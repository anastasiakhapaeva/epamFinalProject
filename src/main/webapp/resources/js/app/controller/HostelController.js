/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
    var hostelCtrl = (function () {
        var methods = {
            init: function () {
                this.setLang();
                this.loadAllImages();
                this.checkBookingState();
                this.bindEvents();
            },
            setLang: function () {
                var locale = $('#locale').val();
                setLang(locale);
            },
            loadAllImages: function () {
                var hostelId = $('#hostel_id').val();
                var loadType = 'all';
                LoadService.loadImages(hostelId, loadType, function (data) {
                    $('#main-img').attr('src', data[0]);
                    var j;
                    for(j=0;j<data.length;j++){
                        $("#img" + j).attr('src', data[j]);
                    }
                });
            },
            checkBookingState: function () {
                var hostelId = $('#hostel_id').val();
                var userId = $('#user_id').val();
                if(userId) {
                    BookingService.checkBookingState(userId, hostelId, function (data) {
                        var result = JSON.parse(data);
                        if (result) {
                            $('#book-status-booked').removeAttr('class');
                            $('#book-status-free').attr('class', 'hidden');
                        } else {
                            $('#book-status-booked').attr('class', 'hidden');
                            $('#book-status-free').removeAttr('class');
                        }
                    });
                }
            },
            bindEvents: function () {
                $('#img0,#img1,#img2').click(function () {
                    $('#main-img').attr('src', $(this).attr('src'));
                });

                $('#bookModal').on('shown.bs.modal', function () {
                    var dateIn = $('#date_in').val();
                    var dateOut = $('#date_out').val();
                    if ($(this).find('#guests').val()) {
                        $(this).find('#guests').trigger('input');
                    }
                    if (dateIn && dateOut) {
                        var hostelId = $('#hostel_id').val();
                        BookingService.countAvailablePlaces(hostelId, dateIn, dateOut, function (data) {
                            var result = JSON.parse(data);
                            $('#place-amount').text($.i18n.prop('msg.available.places') + result);
                            $('#guests').attr('data-parsley-max', result);
                        });
                    }
                });

                $('#bookModal').find('#guests').on('input', function () {
                    var price = parseFloat($('#hostel-price').val());
                    var places = parseInt($('#guests').val());
                    var userDiscount = parseFloat($('#us-discount').val());
                    var bookingType = $('input[type="radio"][name="bookingType"]:checked').val();
                    countTotalPrice(bookingType, price, places, userDiscount, function (msg) {
                        $('#total-price').text(msg);
                    });
                });

                $('input[type="radio"][name="bookingType"]').change(function () {
                    if (this.value == 'Payment') {
                        $('#book-info').text($.i18n.prop('msg.payment.info'));
                        $('#book-payment').text($.i18n.prop('msg.payment.underinfo'));

                    }
                    else if (this.value == 'Reservation') {
                        $('#book-info').text($.i18n.prop('msg.reservation.info'));
                        $('#book-payment').text($.i18n.prop('msg.reservation.underinfo'));
                    }
                    if ($('#bookModal').find('#guests').val()) {
                        $('#bookModal').find('#guests').trigger('input');
                    }
                });
            }
        };

        function setLang(lang) {
            $.i18n.properties({
                name: 'msg',
                path: 'resources/i18n/',
                mode: 'map',
                language: lang,
                callback: function() {}
            });
        }

        function countTotalPrice(bookingType, price, places, userDiscount, callback) {
            var totalPrice;
            if (bookingType == 'Payment') {
                totalPrice = price * places;
                var discPay = totalPrice * (userDiscount / 100);
                totalPrice = totalPrice - discPay;
                callback($.i18n.prop('msg.payment.totalprice') + totalPrice);
            } else if (bookingType == 'Reservation') {
                var oneDay = 24 * 60 * 60 * 1000;
                var date_in = $.datepicker.parseDate('yy-mm-dd', $('#date_in').val());
                var date_out = $.datepicker.parseDate('yy-mm-dd', $('#date_out').val());

                var diffDays = Math.abs((date_in.getTime() - date_out.getTime()) / (oneDay));
                totalPrice = price * places * diffDays;
                var discRes = totalPrice * (userDiscount / 100);
                totalPrice = totalPrice - discRes;
                callback($.i18n.prop('msg.reservation.totalprice') + totalPrice);

            }
        }

        return methods;
    }());

    hostelCtrl.init();
});