/**
 * Created by Roman on 22.01.2017.
 */
var MenuBarService = (function () {
    var methods = {
        checkLoginExistence: function (login, callback) {
            $.ajax({
                type: 'POST',
                url: '/web/service',
                dataType: 'json',
                data: {
                    userLogin: login,
                    command: 'ajax_registr'
                },
                async: false,
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot check login");
                }
            });
        },
        checkUserExistence: function (username, password, callback) {
            $.ajax({
                type: 'POST',
                url: '/web/service',
                dataType: 'json',
                data: {
                    login: username,
                    pass: password,
                    command: 'ajax_autho'
                },
                async: false,
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot check user");
                }
            });
        },
        depositMoney: function (amount, callback) {
            $.ajax({
                type: 'POST',
                url: '/web/service',
                data: {
                    amountMon: amount,
                    command: 'deposit_money'
                },
                async: false,
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot deposit money");
                }
            });
        },
        changeLanguage: function (locale, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    locale: locale,
                    command: 'ajax_change_lang'
                },
                async: false,
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot change language");
                }
            });
        },
        deleteMessage: function (messageId, callback) {
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    messageId: messageId,
                    command: 'ajax_delete_message'
                },
                async: false,
                success: function (data) {
                    callback(data);
                },
                error: function (data) {
                    console.error("ajax error: cannot delete message");
                }
            });
        }
    };

    return methods;
}());