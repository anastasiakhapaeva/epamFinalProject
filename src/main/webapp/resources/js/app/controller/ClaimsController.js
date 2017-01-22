/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
    var claimsCtrl = (function () {
        var methods = {
            init: function () {
                this.setLang();
                this.loadNames();
                this.bindEvents();
            },
            setLang: function () {
                var locale = $('#locale').val();
                setLang(locale);
            },
            loadNames: function () {
                var userIds = $("input[name='user_id']");
                var hostelIds = $("input[name='hostel_id']");
                var i;
                for (i = 0; i < userIds.length; i++) {
                    LoadService.loadNames(userIds[i].value, hostelIds[i].value, function (data) {
                        var names = JSON.parse(data);
                        var us_prefix = "user";
                        var hs_prefix = "hostel";
                        var us_id = userIds[i].value;
                        var hs_id = hostelIds[i].value;
                        $("[data-name="+us_prefix+us_id+"]").text(names[0]);
                        $("[data-name="+hs_prefix+hs_id+"]").text(names[1]);
                    });
                }
            },
            bindEvents: function () {
                $('#userModal').on("show.bs.modal", function (e) {
                    var userId = $(e.relatedTarget).attr("data-user-id");
                   AdminService.showUserInfo(userId, function (data) {
                       var jsonData = JSON.parse(data);
                       $('#user-id-profile').val(jsonData[0].userId);
                       $('#user-name').text(jsonData[1].lastName + ' ' + jsonData[1].firstName);
                       if(jsonData[0].isAdmin){
                           $('#user-role').text($.i18n.prop('msg.administrator'));
                       }else{
                           $('#user-role').text($.i18n.prop('msg.user'));
                       }
                       $('#user-city').text(jsonData[1].city);
                       $('#user-phone').text(jsonData[1].phone);
                       $('#user-email').text(jsonData[1].email);
                       $('#user-money-profile').text(jsonData[0].money);
                       $('#user-discount').text(jsonData[0].discount);
                       if(jsonData[0].isBanned){
                           $('#user-ban').text($.i18n.prop('msg.banned'));
                       }else{
                           $('#user-ban').text($.i18n.prop('msg.notbanned'));
                       }
                   });
                });

                $('#ban-user').on("click", function () {
                    var userId = $('#user-id-profile').val();
                    var result = false;
                    AdminService.banUser(userId, function (data) {
                        result = JSON.parse(data);
                    });
                    if(result){
                        $('#user-ban').text($.i18n.prop('msg.banned'));
                    }else{
                        $('#user-ban').text($.i18n.prop('msg.notbanned'));
                    }
                });

                $('#set-discount-user').on("click", function () {
                    $('#set-user-discount').attr('type', 'text').focus();
                    $('#ok-addon').attr('class', 'input-group-addon');
                    $('#user-discount').attr('class', 'hidden');
                });

                $('#ok-addon').on("click", function () {
                    var instance = $('#set-user-discount').parsley('validate');
                    if(instance.isValid()) {
                        $('#user-discount-err').text("");
                        var userId = $('#user-id-profile').val();
                        var discount = $('#set-user-discount').val();
                        var result = false;
                        AdminService.setDiscount(userId, discount, function (data) {
                            result = JSON.parse(data);
                        });
                        if (result) {
                            $('#user-discount').attr('class', "").text(discount);
                            $('#set-user-discount').attr('type', 'hidden');
                            $('#ok-addon').attr('class', 'hidden');
                        } else {
                            $('#user-discount').attr('class', "").text($.i18n.prop('msg.setdiscount.error'));
                            $('#set-user-discount').attr('type', 'hidden');
                            $('#ok-addon').attr('class', 'hidden');
                        }
                    }else{
                        $('#user-discount-err').text($.i18n.prop('msg.discount.value'));
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
                callback: function () {
                }
            });
        }


        return methods;
    }());

    claimsCtrl.init();
});