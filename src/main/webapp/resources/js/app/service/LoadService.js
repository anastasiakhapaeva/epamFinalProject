/**
 * Created by Roman on 22.01.2017.
 */
var LoadService = (function () {
    var methods = {
        loadImages: function (hostelId, type, callback) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: {
                    hostelId: JSON.stringify(hostelId),
                    command: 'ajax_load_img',
                    loadType: type
                },
                url: '/web/service',
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot load images");
                }
            });
        },
        loadNames: function (userId, hostelId, callback) {
            $.ajax({
                type: 'POST',
                url: '/web/service',
                dataType: 'json',
                data: {
                    userId: JSON.stringify(userId),
                    hostelId: JSON.stringify(hostelId),
                    command: 'ajax_load_names'
                },
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot load names");
                }
            });
        }
    };

    return methods;
}());
