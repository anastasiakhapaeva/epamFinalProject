/**
 * Created by Roman on 25.12.2016.
 */
$(function () {
    var frm = $('#register-form');
    frm.submit(function (ev) {
        var login = $('#userLogin').val();
        var result = false;
        $.ajax({
            type: 'POST',
            url: frm.attr('action'),
            dataType: 'json',
            data: {
                userLogin: login,
                command: 'ajax_registr'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);
            }
        });
        if (result) {
            $('#err-login').text("A user with such login already exists!");
        } else {
            $('#err-login').empty();
        }
        return !result;
    });
});

$(function () {
    var frm = $('#authorization-form');
    frm.submit(function (ev) {
        var username = $('#user_login').val();
        var password = $('#user_password').val();
        var result = false;
        $.ajax({
            type: 'POST',
            url: frm.attr('action'),
            dataType: 'json',
            data: {
                login: username,
                pass: password,
                command: 'ajax_autho'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);
            }
        });
        if (result) {
            $('#err-autho').empty();
        } else {
            $('#err-autho').text("The username or password you entered is incorrect!");
        }
        return result;
    });
});

$(function () {
    var frm = $('#payment-form');
    // var date_in = $('#bookModal').find("div[class='tab-pane active']").find("input[name='date_in']").val();
    // var date_out = $('#bookModal').find("div[class='tab-pane active']").find("input[name='date_out']").val();
    // var place_amount = $('#bookModal').find("div[class='tab-pane active']").find("input[name='place-amount']");
    // var guests = $('#bookModal').find("div[class='tab-pane active']").find("input[name='guests']");
    frm.submit(function (ev) {
        var price = parseFloat($('#hostel-price').val());
        var userMoney = parseFloat($('#us-mon').val());
        var userDiscount = parseFloat($('#us-discount').val());
        var places = parseInt($('#guests').val());
        var bookingType = $('input[type="radio"][name="bookingType"]:checked').val();
        var totalPrice;
        var result = false;
        if(bookingType == 'Payment') {
            totalPrice = price * places;
            var discPay = totalPrice * (userDiscount/100);
            totalPrice = totalPrice - discPay;
        }else if(bookingType == 'Reservation'){
            var oneDay = 24*60*60*1000;
            var date_in = $.datepicker.parseDate('yy-mm-dd',$('#date_in').val());
            var date_out = $.datepicker.parseDate('yy-mm-dd',$('#date_out').val());
            var diffDays = Math.abs((date_in.getTime() - date_out.getTime())/(oneDay));
            totalPrice = price * places * diffDays;
            var discRes = totalPrice * (userDiscount/100);
            totalPrice = totalPrice - discRes;
        }
        if(totalPrice<=userMoney){
            result = true;
        }
        if (result) {
            $('#err-booking').empty();
        } else {
            $('#err-booking').text("You haven't got enough money!");
        }
        return result;
    });
});

$(function () {
    $('#deposit-form').submit(function (e) {
        var result = false;
        var amount = $('#amount-mon').val();
        $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            data: {
                amountMon: amount,
                command: 'deposit_money'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);
            }
        });
        if(result){
            location.reload();
        }else{
            $('#err-deposit').text("Something wrong!");
        }
        return false;
    });
});

$(function () {
    $('#ru').on("click", function () {
        var result = false;
        $.ajax({
            type: 'GET',
            url: '/web/service',
            data: {
                locale: 'ru_RU',
                command: 'ajax_change_lang'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);
            },
            error: function (data) {
                alert('error');
            }
        });
        if(result){
            location.reload();
        }
    });

    $('#eng').on("click", function () {
        var result = false;
        $.ajax({
            type: 'GET',
            url: '/web/service',
            data: {
                locale: 'en_US',
                command: 'ajax_change_lang'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);
            },
            error: function (data) {
                alert('error');
            }
        });
        if(result){
            location.reload();
        }
    });
});