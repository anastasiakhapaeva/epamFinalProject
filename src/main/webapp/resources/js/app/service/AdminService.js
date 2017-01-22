/**
 * Created by Roman on 22.01.2017.
 */
var AdminService = (function () {
    var methods = {
        showUserInfo: function (userId, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    userId: userId,
                    command: 'ajax_load_user'
                },
                async: false,
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot load user info");
                }
            });
        },
        banUser: function (userId, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    userId: userId,
                    command: 'ban_user'
                },
                async: false,
                success: function (data) {
                    callback(data);

                },
                error: function (data) {
                    console.error("ajax error: cannot ban user");
                }
            });
        },
        setDiscount: function (userId, discount, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    userId: userId,
                    discount: discount,
                    command: 'set_discount_user'
                },
                async: false,
                success: function (data) {
                    callback(data);

                },
                error: function (data) {
                    console.error("ajax error: cannot set discount");
                }
            });
        }
    };

    return methods;
}());