/**
 * Created by Roman on 22.01.2017.
 */
var LoadService = (function () {
    var methods = {
        loadImages: function (hostelId, type, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    hostelId: hostelId,
                    command: 'ajax_load_img',
                    loadType: type
                },
                async: false,
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
                type: 'GET',
                url: '/web/service',
                data: {
                    userId: userId,
                    hostelId: hostelId,
                    command: 'ajax_load_names',
                },
                async: false,
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
