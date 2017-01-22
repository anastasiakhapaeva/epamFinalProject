// /**
//  * Created by Roman on 30.12.2016.
//  */
window.onload = function () {

    var city = sessionStorage.getItem('city');
    if (city !== null) $('#city').val(city);

    var date_in = sessionStorage.getItem('date_in');
    if (date_in !== null) $('#date_in').val(date_in);

    var date_out = sessionStorage.getItem('date_out');
    if (date_out !== null) $('#date_out').val(date_out);

    var places = sessionStorage.getItem('places');
    if (places !== null) $('#guests').val(places);
}

window.onbeforeunload = function () {
    if ($('#city').val() !== null && $('#city').val() !== undefined) {
        sessionStorage.setItem("city", $('#city').val());
    }
    if ($('#date_in').val() !== null && $('#date_in').val() !== undefined) {
        sessionStorage.setItem("date_in", $('#date_in').val());
    }
    if ($('#date_out').val() !== null && $('#date_out').val() !== undefined) {
        sessionStorage.setItem("date_out", $('#date_out').val());
    }
    if ($('#guests').val() !== null && $('#guests').val() !== undefined) {
        sessionStorage.setItem("places", $('#guests').val());
    }
}