/**
 * Created by Roman on 01.01.2017.
 */
$('#img0,#img1,#img2').click(function () {
    $('#main-img').attr('src', $(this).attr('src'));
});

// $(document).ready(function () {
//     var result = false;
//     var beg = $('#main-path').val();
//     var hostel_id = $('#hostel_id').val();
//     var user_id = $('#user_id').val();
//     if(user_id == "") return;
//     $.ajax({
//         type: 'GET',
//         url: beg+'/service',
//         data: {
//             hostelId: hostel_id,
//             userId: user_id,
//             command: 'ajax_is_booked',
//         },
//         async: false,
//         success: function (data) {
//             result = JSON.parse(data);
//             if(result){
//                 $('#book-status-booked').removeAttr('class');
//                 $('#book-status-free').attr('class',"hidden");
//             }else{
//                 $('#book-status-free').removeAttr('class');
//                 $('#book-status-booked').attr('class',"hidden");
//             }
//
//         },
//         error: function (data) {
//             alert('error');
//         }
//     });
// });
// $(function () {
//     $('#book-form').submit(function (e) {
//         var result;
//         var hostel_id = $('#hostel_id').val();
//         var user_id = $('#user_id').val();
//         var date_in = $('#date_in').val();
//         var date_out = $('#date_out').val();
//         var guests = $('#guests').val();
//         $.ajax({
//             type: 'POST',
//             url: $(this).attr('action'),
//             data: {
//                 hostel_id: hostel_id,
//                 user_id: user_id,
//                 date_in: date_in,
//                 date_out: date_out,
//                 guests: guests,
//                 command: 'book_hostel'
//             },
//             async: false,
//             success: function (data) {
//                 result = JSON.parse(data);
//                 if(result){
//                     $('#book-status').empty();
//                     $('#book-status').text("You have booked this hostel! Wait for administrator approval.");
//                     $('#book-status').removeAttr('class');
//                     $('#cancel-btn').attr('class',"btn btn-danger");
//                     $('#bookModal').modal('hide');
//                 }
//             }
//         });
//         return false;
//     });
// });

// $('#cancel-btn').click(function () {
//     var beg = $('#main-path').val();
//     var result = false;
//     var hostel_id = $('#hostel_id').val();
//     var user_id = $('#user_id').val();
//     $.ajax({
//         type: 'GET',
//         url: beg + '/service',
//         data: {
//             hostelId: hostel_id,
//             userId: user_id,
//             command: 'book_cancel'
//         },
//         async: false,
//         success: function (data) {
//             result = JSON.parse(data);
//         }
//     });
//     if(result){
//         $('#book-status').removeAttr('class');
//     }else{
//         $('#book-status').empty();
//         $('#book-status').text("You have booked this hostel! Wait for administrator approval.");
//         $('#book-status').removeAttr('class');
//         $('#cancel-btn').attr('class',"btn btn-danger");
//     }
//     location.reload();
// });