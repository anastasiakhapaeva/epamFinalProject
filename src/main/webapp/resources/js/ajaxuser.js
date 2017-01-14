/**
 * Created by Roman on 07.01.2017.
 */
$(document).ready(function () {
    var user_ids = $("input[name='user_id']");
    var hostel_ids = $("input[name='hostel_id']");
    var i;
    var beg = $('#main-path').val();
    for (i = 0; i < user_ids.length; i++) {
        $.ajax({
            type: 'GET',
            url: beg + '/service',
            data: {
                userId: user_ids[i].value,
                hostelId: hostel_ids[i].value,
                command: 'ajax_load_names',
            },
            async: false,
            success: function (data) {
                var jsonData = JSON.parse(data);
                var us_prefix = "user";
                var hs_prefix = "hostel";
                var us_id = user_ids[i].value;
                var hs_id = hostel_ids[i].value;
                $("[data-name="+us_prefix+us_id+"]").text(jsonData[0]);
                $("[data-name="+hs_prefix+hs_id+"]").text(jsonData[1]);

            },
            error: function (data) {
                alert('error');
            }
        });
    }
});
$(document).ready(function () {
    $('#userModal').on("show.bs.modal", function (e) {
        var user_id = $(e.relatedTarget).attr("data-user-id");
        $.ajax({
            type: 'GET',
            url: '/web/service',
            data: {
                userId: user_id,
                command: 'ajax_load_user'
            },
            async: false,
            success: function (data) {
                var jsonData = JSON.parse(data);
                $('#user-id-profile').val(jsonData[0].userId);
                $('#user-name').text(jsonData[1].lastName + ' ' + jsonData[1].firstName);
                if(jsonData[0].isAdmin){
                    $('#user-role').text("Administrator");
                }else{
                    $('#user-role').text("User");
                }
                $('#user-city').text(jsonData[1].city);
                $('#user-phone').text(jsonData[1].phone);
                $('#user-email').text(jsonData[1].email);
                $('#user-money-profile').text(jsonData[0].money);
                $('#user-discount').text(jsonData[0].discount);
                if(jsonData[0].isBanned){
                    $('#user-ban').text("Banned");
                }else{
                    $('#user-ban').text("Not banned");
                }

            },
            error: function (data) {
                alert('error');
            }
        });
    });
});

$(document).ready(function () {
    $('#ban-user').on("click", function (e) {
        var user_id = $('#user-id-profile').val();
        var result = false;
        $.ajax({
            type: 'GET',
            url: '/web/service',
            data: {
                userId: user_id,
                command: 'ban_user'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);

            },
            error: function (data) {
                alert('error');
            }
        });
        if(result){
            $('#user-ban').text("Banned");
        }else{
            $('#user-ban').text("Not banned");
        }
    });
});

$(document).ready(function () {



    $('#set-discount-user').on("click", function (e) {
        $('#set-user-discount').attr('type', 'text').focus();
        $('#ok-addon').attr('class', 'input-group-addon');
        $('#user-discount').attr('class', 'hidden');
    });

    $('#ok-addon').on("click", function () {
        var instance = $('#set-user-discount').parsley('validate');
        if(instance.isValid()) {
            $('#user-discount-err').text("");
            var user_id = $('#user-id-profile').val();
            var discount = $('#set-user-discount').val();
            var result = false;
            $.ajax({
                type: 'GET',
                url: '/web/service',
                data: {
                    userId: user_id,
                    discount: discount,
                    command: 'set_discount_user'
                },
                async: false,
                success: function (data) {
                    result = JSON.parse(data);

                },
                error: function (data) {
                    alert('error');
                }
            });
            if (result) {
                $('#user-discount').attr('class', "").text(discount);
                $('#set-user-discount').attr('type', 'hidden');
                $('#ok-addon').attr('class', 'hidden');
            } else {
                $('#user-discount').attr('class', "").text("Cannot set discount");
                $('#set-user-discount').attr('type', 'hidden');
                $('#ok-addon').attr('class', 'hidden');
            }
        }else{
            $('#user-discount-err').text("Discount must be form 0.00 to 99.99 %");
        }
    });
});