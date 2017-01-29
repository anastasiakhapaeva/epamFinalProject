/**
 * Created by Roman on 22.01.2017.
 */
$(function () {
   var menuCtrl = (function () {
       var methods = {
           init: function () {
               this.setLang();
               this.bindEvents();
           },
           setLang: function () {
               var locale = $('#locale').val();
               window.Parsley.setLocale(locale.substring(0, 2));
               setLang(locale);
           },
           bindEvents: function () {
               $('#register-form').submit(function () {
                   var login = $('#userLogin').val();
                   var result = false;
                   MenuBarService.checkLoginExistence(login, function (data) {
                       result = JSON.parse(data);
                   });
                   if (result) {
                       $('#err-login').text($.i18n.prop('msg.registration.error'));
                   } else {
                       $('#err-login').empty();
                   }
                   return !result;
               });

               $('#authorization-form').submit(function () {
                   var username = $('#user_login').val();
                   var password = $('#user_password').val();
                   var result = false;
                   MenuBarService.checkUserExistence(username, password, function (data) {
                       result = JSON.parse(data);
                   });
                   if (result) {
                       $('#err-autho').empty();
                   } else {
                       $('#err-autho').text($.i18n.prop('msg.login.error'));
                   }
                   return result;
               });

               $('#payment-form').submit(function () {
                   var price = parseFloat($('#hostel-price').val());
                   var userMoney = parseFloat($('#us-mon').val());
                   var userDiscount = parseFloat($('#us-discount').val());
                   var places = parseInt($('#guests').val());
                   var bookingType = $('input[type="radio"][name="bookingType"]:checked').val();
                   var totalPrice;
                   var result = false;
                   if(bookingType == 'Payment') {
                       totalPrice = price * places;
                       var discPay = totalPrice * (userDiscount/100);
                       totalPrice = totalPrice - discPay;
                   }else if(bookingType == 'Reservation'){
                       var oneDay = 24*60*60*1000;
                       var date_in = $.datepicker.parseDate('yy-mm-dd',$('#date_in').val());
                       var date_out = $.datepicker.parseDate('yy-mm-dd',$('#date_out').val());
                       var diffDays = Math.abs((date_in.getTime() - date_out.getTime())/(oneDay));
                       totalPrice = price * places * diffDays;
                       var discRes = totalPrice * (userDiscount/100);
                       totalPrice = totalPrice - discRes;
                   }
                   if(totalPrice<=userMoney){
                       result = true;
                   }
                   if (result) {
                       $('#err-booking').empty();
                   } else {
                       $('#err-booking').text($.i18n.prop('msg.booking.money.error'));
                   }
                   return result;
               });

               $('#deposit-form').submit(function (e) {
                   e.preventDefault();
                   var result = false;
                   var amount = $('#amount-mon').val();
                   MenuBarService.depositMoney(amount, function (data) {
                       result = JSON.parse(data);
                   });
                   if(result){
                       location.reload();
                   }else{
                       $('#err-deposit').text($.i18n.prop('msg.deposit.error'));
                   }
               });

               $("#ru, #eng").on("click", function () {
                   var locale = $(this).attr('data-locale');
                   var result = false;
                   MenuBarService.changeLanguage(locale, function (data) {
                       result = JSON.parse(data);
                   });
                   if(result){
                       location.reload();
                   }
               });

               $('[name="closeButton"]').click(function () {
                   var messageId = $(this).attr("data-message-id");
                   var result = false;
                   MenuBarService.deleteMessage(messageId, function (data) {
                       result = JSON.parse(data);
                   });
                   return result;
               });
           }
       };

       function setLang(lang) {
           if(lang == ""){
               lang = "ru_RU";
           }
           $.i18n.properties({
               name: 'msg',
               path: 'resources/i18n/',
               mode: 'map',
               checkAvailableLanguages: true,
               language: lang,
               callback: function() {}
           });
       }

       return methods;
   }());

   menuCtrl.init();
});