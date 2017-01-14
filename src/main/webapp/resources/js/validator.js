/**
 * Created by Roman on 24.12.2016.
 */
window.Parsley.addValidator('login', {
    validateString: function (value, regexp) {
        return regexp.test(value);
    },
    requirementType: 'regexp',
    messages: {
        en: 'Invalid login. Login must have more than 3 symbols, starts with latin letter,' +
        'may contains _',
        ru: "Недопустимый логин. Логин должен иметь больше 3 символов, " +
        "начинаться с латинской буквы, может содеражть _"
    }
});
window.Parsley.addValidator('password', {
    validateString: function (value, regexp) {
        return regexp.test(value);
    },
    requirementType: 'regexp',
    messages: {
        en: 'Invalid password. Password must have more than 5 symbols, contains at least 1 letter in each case ' +
        'and at least one digit.',
        ru: "Недопустимый пароль. Пароль должен иметь более 5 символов, сожержать хотя бы одну букву в каждом регистре " +
        " и хотя бы одну цифру."
    }
});
window.Parsley.addValidator('pass', {
    validateString: function (value, id) {
        return value == document.getElementById(id).value;
    },
    requirementType: 'string',
    messages: {
        en: 'Password\'s doesn\'t match.',
        ru: "Пароли не совпадают."
    }
});

$(function () {


    var dates = $("#date_in, #date_out").datepicker({
        // minDate: f(this),
        dateFormat: 'yy-mm-dd',
        beforeShow: function () {
            var min_date = new Date();
            if ($('#date_in').val()) {
                min_date = $.datepicker.parseDate('yy-mm-dd', $('#date_in').val());
            }
            if (this.id == "date_in") {
                $('#' + this.id).datepicker("option", "minDate", new Date(min_date.getTime()));
                if ($('#date_out').val()) {
                    var max_date = $.datepicker.parseDate('yy-mm-dd', $('#date_out').val());
                    $('#' + this.id).datepicker("option", "maxDate", new Date(max_date.getTime() - 24 * 60 * 60 * 1000));
                }
            } else {
                $('#' + this.id).datepicker("option", "minDate", new Date(min_date.getTime() + 24 * 60 * 60 * 1000));
            }
        },
        onSelect: function (selectedDate) {
            var option = this.id == "date_in" ? "minDate" : "maxDate",
                instance = $(this).data("datepicker"),
                date = $.datepicker.parseDate(
                    instance.settings.dateFormat ||
                    $.datepicker._defaults.dateFormat,
                    selectedDate, instance.settings);
            // if($("#date_in").val()){
            //     var date_in = $.datepicker.parseDate('yy-mm-dd',$("#date_in").val());
            //     date.setDate(date_in.getDate() + 1);
            // }
            dates.not(this).datepicker("option", option, date);
            if ($('#bookModal').find('#date_in').val() && $('#bookModal').find('#date_out').val()) {
                var beg = $('#main-path').val();
                var hostel_id = $('#hostel_id').val();
                $.ajax({
                    type: 'GET',
                    url: beg + '/service',
                    data: {
                        hostelId: hostel_id,
                        dateIn: $('#date_in').val(),
                        dateOut: $('#date_out').val(),
                        command: 'ajax_free_places',
                    },
                    // async: false,
                    success: function (data) {
                        var result = JSON.parse(data);
                        $('#place-amount').text("Available places on this period: " + result);
                        $('#guests').attr('data-parsley-max', result);
                    }
                });
                if ($('#bookModal').find('#guests').val()) {
                    $('#bookModal').find('#guests').trigger('input');
                }
            }
        }
    });
});
$(document).ready(function () {
    $('#bookModal').find('#guests').on('input', function () {
        var price = parseFloat($('#hostel-price').val());
        var places = parseInt($('#guests').val());
        var userDiscount = parseFloat($('#us-discount').val());
        var bookingType = $('input[type="radio"][name="bookingType"]:checked').val();
        var totalPrice;
        if (bookingType == 'Payment') {
            totalPrice = price * places;
            var discPay = totalPrice * (userDiscount / 100);
            totalPrice = totalPrice - discPay;
            $('#total-price').text("Total price per 1 night: " + totalPrice);
        } else if (bookingType == 'Reservation') {

            var oneDay = 24 * 60 * 60 * 1000;
            var date_in = $.datepicker.parseDate('yy-mm-dd', $('#date_in').val());
            var date_out = $.datepicker.parseDate('yy-mm-dd', $('#date_out').val());

            var diffDays = Math.abs((date_in.getTime() - date_out.getTime()) / (oneDay));
            totalPrice = price * places * diffDays;
            var discRes = totalPrice * (userDiscount / 100);
            totalPrice = totalPrice - discRes;
            $('#total-price').text("Total price of the full living: " + totalPrice);
        }
    });
});


$(document).ready(function () {
    $('input[type="radio"][name="bookingType"]').change(function () {
        if (this.value == 'Payment') {
            // $('#booking-type').val('pay_hostel');
            $('#book-info').text("Вы оплачивает первые сутки проживания сразу на сайте, а оставшуюся сумму доплачиваете непосредственно при заезде.");
            $('#book-payment').text("Отменить такое бронирование нельзя!");

        }
        else if (this.value == 'Reservation') {
            // $('#booking-type').val('reserve_hostel');
            $('#book-info').text("Вы ожидаете подтверждения заявки админом. После подтверждения, оплачивается вся сумма бронирования хостела.");
            $('#book-payment').text("Отменить заявку можно до подтверждения ее администратором!");
        }
        if ($('#bookModal').find('#guests').val()) {
            $('#bookModal').find('#guests').trigger('input');
        }
    });
});

$(document).ready(function () {
    $('#bookModal').on('shown.bs.modal', function (e) {
        if ($('#bookModal').find('#guests').val()) {
            $('#bookModal').find('#guests').trigger('input');
        }
        if ($('#date_in').val() && $('#date_out').val()) {
            var beg = $('#main-path').val();
            var hostel_id = $('#hostel_id').val();
            $.ajax({
                type: 'GET',
                url: beg + '/service',
                data: {
                    hostelId: hostel_id,
                    dateIn: $('#date_in').val(),
                    dateOut: $('#date_out').val(),
                    command: 'ajax_free_places',
                },
                // async: false,
                success: function (data) {
                    var result = JSON.parse(data);
                    $('#place-amount').text("Available places on this period: " + result);
                    $('#guests').attr('data-parsley-max', result);
                }
            });
        }
    });
});