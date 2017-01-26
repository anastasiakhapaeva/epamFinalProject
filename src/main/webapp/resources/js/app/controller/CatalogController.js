/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
    var catalogCtrl = (function () {
        var methods = {
            init: function () {
                this.setLang();
                this.loadMainImages();
                this.setCurrentPage();
                this.bindEvents();
            },
            setLang: function () {
                var locale = $('#locale').val();
                setLang(locale);
            },
            loadMainImages: function () {
                var inputs = $("input[name='id']");
                var loadType = 'main';
                var i;
                for(i=0; i< inputs.length;i++) {
                    LoadService.loadImages(inputs[i].value, loadType, function (data) {
                        $("#" + inputs[i].value).attr('src', data);
                    });
                }
            },
            setCurrentPage: function () {
                var currPage = $('#currPage').val();
                $('#page' + currPage).attr('class', 'active');
            },
            bindEvents: function () {
                $("a[data-page-num]").on('click', function (e) {
                    e.preventDefault();
                    var page = $(this).attr('data-page-num');
                    $('#pageNum').val(page);
                    $('#findForm').submit();
                });
            }
        };

        function setLang(lang) {
            $.i18n.properties({
                name: 'msg',
                path: 'resources/i18n/',
                mode: 'map',
                language: lang,
                callback: function() {}
            });
        }

        return methods;
    }());

    catalogCtrl.init();
});