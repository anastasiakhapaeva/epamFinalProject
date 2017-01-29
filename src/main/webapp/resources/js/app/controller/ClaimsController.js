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