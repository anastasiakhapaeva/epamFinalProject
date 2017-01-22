/**
 * Created by Roman on 22.01.2017.
 */
var BookingService = (function () {
    var methods = {
        countAvailablePlaces: function (hostelId, dateIn, dateOut, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    hostelId: hostelId,
                    dateIn: dateIn,
                    dateOut: dateOut,
                    command: 'ajax_free_places',
                },
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot count available places");
                }
            });
        }
    };

    return methods;
}());
