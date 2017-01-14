/**
 * Created by Roman on 27.12.2016.
 */
$(document).ready(function(){
    var inputs = $("input[name='id']");
    var loadType = $('#loadType').val();
    var i;
    var beg = $('#main-path').val();
    for(i=0; i< inputs.length;i++) {
            $.ajax({
                type: 'GET',
                url: beg+'/service',
                data: {
                    hostelId: inputs[i].value,
                    command: 'ajax_load_img',
                    loadType: loadType
                },
                async: false,
                success: function (data) {
                    if(loadType == 'main') {
                        $("#" + inputs[i].value).attr('src', data);
                    }else{
                        var jsonData = JSON.parse(data);
                        $('#main-img').attr('src', jsonData[0]);
                        var j;
                        for(j=0;j<jsonData.length;j++){
                            $("#img" + j).attr('src', jsonData[j]);
                        }
                    }
                },
                error: function (data) {
                    alert('error');
                }
            });
    }
});
$(document).ready(function () {
    $('#img0,#img1,#img2').click(function () {
        $('#main-img').attr('src', $(this).attr('src'));
    });
});
