/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
    var dateCtrl = (function () {
        var methods = {
            init: function () {
                this.bindEvents();
            },
            bindEvents: function () {
                $("#date_in, #date_out").keydown(function (e) {
                    e.preventDefault();
                });

                var dates = $("#date_in, #date_out").datepicker({
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
                        dates.not(this).datepicker("option", option, date);
                        if ($('#bookModal').find('#date_in').val() && $('#bookModal').find('#date_out').val()) {
                            var dateIn = $('#date_in').val();
                            var dateOut = $('#date_out').val();
                            var hostelId = $('#hostel_id').val();
                            BookingService.countAvailablePlaces(hostelId, dateIn, dateOut, function (data) {
                                var result = JSON.parse(data);
                                $('#place-amount').text($.i18n.prop('msg.available.places') + result);
                                $('#guests').attr('data-parsley-max', result);
                            });
                            if ($('#bookModal').find('#guests').val()) {
                                $('#bookModal').find('#guests').trigger('input');
                            }
                        }
                    }
                });
            }
        };
       return methods;
    }());

    dateCtrl.init();
});