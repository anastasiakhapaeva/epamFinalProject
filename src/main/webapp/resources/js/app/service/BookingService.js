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
        },
        checkBookingState: function(userId, hostelId, callback){
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    userId: userId,
                    hostelId: hostelId,
                    command: 'ajax_check_state',
                },
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot check booking state");
                }
            });
        }
    };

    return methods;
}());
