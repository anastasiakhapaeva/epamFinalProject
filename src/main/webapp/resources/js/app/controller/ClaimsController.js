/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
    var claimsCtrl = (function () {
        var methods = {
            init: function () {
                this.setLang();
                this.loadNames();
            },
            setLang: function () {
                var locale = $('#locale').val();
                setLang(locale);
            },
            loadNames: function () {
                var userIds = $("input[name='user_id']");
                var hostelIds = $("input[name='hostel_id']");
                var inputsUser = userIds.map(function() {
                    return $(this).val();
                }).get();
                var inputsHostel = hostelIds.map(function() {
                    return $(this).val();
                }).get();
                LoadService.loadNames(inputsUser, inputsHostel, function (data) {
                    var us_prefix = "user";
                    var hs_prefix = "hostel";
                    var i;
                    for(i=0;i<data.length;i++){
                        var us_id = userIds[i].value;
                        var hs_id = hostelIds[i].value;
                        $("[data-name="+us_prefix+us_id+"]").text(data[i][0]);
                        $("[data-name="+hs_prefix+hs_id+"]").text(data[i][1]);
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