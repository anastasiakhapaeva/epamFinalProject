/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
    var userCtrl = (function () {
        var methods = {
            init: function () {
                this.bindEvents();
            },
            bindEvents: function () {
                $('#userModal').on("show.bs.modal", function (e) {
                    var userId = $(e.relatedTarget).attr("data-user-id");
                    AdminService.showUserInfo(userId, function (data) {
                        var jsonData = JSON.parse(data);
                        $('#user-id-profile').val(jsonData.userId);
                        $('#user-name').text(jsonData.profile.lastName + ' ' + jsonData.profile.firstName);
                        if(jsonData.isAdmin){
                            $('#user-role').text($.i18n.prop('msg.administrator'));
                            $('#ban-user').attr('class', 'hidden');
                        }else{
                            $('#user-role').text($.i18n.prop('msg.user'));
                            $('#ban-user').attr('class', 'btn btn-sm btn-danger');
                        }
                        $('#user-city').text(jsonData.profile.city);
                        $('#user-phone').text(jsonData.profile.phone);
                        $('#user-email').text(jsonData.profile.email);
                        $('#user-money-profile').text(jsonData.money);
                        $('#user-discount').text(jsonData.discount);
                        if(jsonData.isBanned){
                            $('#user-ban').text($.i18n.prop('msg.banned'));
                        }else{
                            $('#user-ban').text($.i18n.prop('msg.notbanned'));
                        }
                    });
                });

                $('#ban-user').on("click", function (e) {
                    e.preventDefault();
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
                    location.reload();
                });

                $('#set-discount-user').on("click", function (e) {
                    e.preventDefault();
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

        return methods;
    }());

    userCtrl.init();
});