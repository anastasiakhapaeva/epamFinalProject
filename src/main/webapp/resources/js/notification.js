/**
 * Created by Roman on 14.01.2017.
 */
$(document).ready(function () {
    $('[name="closeButton"]').click(function (e) {
        var messageId = $(this).attr("data-message-id");
        var result = false;
        $.ajax({
            type: 'GET',
            url: '/web/service',
            data: {
                messageId: messageId,
                command: 'ajax_delete_message'
            },
            async: false,
            success: function (data) {
                result = JSON.parse(data);
            },
            error: function (data) {
                alert('error');
            }
        });
        return result;
    })
})