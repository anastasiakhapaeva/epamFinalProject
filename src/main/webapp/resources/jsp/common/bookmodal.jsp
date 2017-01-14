<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 27.12.2016
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="bookModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Уточните данные для бронирования</h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                <div id="book-info" class="text-info marg-bott text-center">Вы оплачивает первые сутки проживания сразу на сайте, а
                    оставшуюся сумму доплачиваете непосредственно при заезде.
                </div>
                <div id="book-payment" class="text-danger marg-bott text-center">Отменить оплату нельзя!</div>
                <form id="payment-form" role="form" class="form-horizontal my-form" method="post"
                      action="${pageContext.request.contextPath}/service" data-parsley-validate>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Тип бронирования</label>
                        <div class="col-sm-6">
                        <label class="radio-inline"><input checked type="radio" id="payment-radio" name="bookingType" value="Payment">Payment</label>
                        <label class="radio-inline"><input type="radio" id="reservation-radio" name="bookingType" value="Reservation">Reservation</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="date_in" class="control-label col-sm-4">Дата въезда</label>
                        <div class="input-group col-sm-6">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar-plus-o" aria-hidden="true"></span>
                                </span>
                            <input id="date_in" type="text" class="form-control datepicker" name="date_in"
                                   required="true" placeholder="дата въезда"
                                   data-parsley-errors-container="#err-datein">
                        </div>
                        <div id="err-datein" class="col-sm-offset-4"></div>
                    </div>
                    <div class="form-group">
                        <label for="date_out" class="control-label col-sm-4">Дата выезда</label>
                        <div class="input-group col-sm-6">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar-minus-o" aria-hidden="true"></span>
                            </span>
                            <input id="date_out" type="text" class="form-control datepicker" name="date_out"
                                   required="true" placeholder="дата выезда"
                                   data-parsley-errors-container="#err-dateout">
                        </div>
                        <div id="err-dateout" class="col-sm-offset-4"></div>
                    </div>
                    <div id="place-amount" class="col-sm-offset-4"></div>
                    <div class="form-group">
                        <label for="guests" class="control-label col-sm-4">Места</label>
                        <div class="input-group col-sm-6">
                            <span class="input-group-addon">
                                <span class="fa fa-users" aria-hidden="true"></span>
                            </span>
                            <input id="guests" placeholder="Количество мест" type="text" class="form-control"
                                   name="guests" required="true"
                                   data-parsley-type="digits"
                                   data-parsley-max="${hostel.freePlaces}"
                                   data-parsley-max-message="There is no such amount of free places in the hostel"
                                   data-parsley-errors-container="#err-place">
                        </div>
                        <div id="err-place" class="col-sm-offset-4"></div>
                    </div>
                    <div id="total-price" class="text-center"></div>
                    <div id="err-booking" class="text-danger marg-bott text-center"></div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-4 inline-disp">
                            <input class="form-control btn btn-success" type="submit" id="booking_button"
                                   value="Забронировать"/>
                        </div>
                        <div class="inline-disp">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                    <input type="hidden" name="command" value="book_hostel">
                    <%--<input id="booking-type" type="hidden" name="bookingType" value="Payment">--%>
                    <input type="hidden" name="hostel_id" value="${hostel.hostelId}">
                    <input type="hidden" name="user_id" value="${currentUser.userId}">
                </form>
            </div>
        </div>
    </div>
</div>
<%--<script src="<c:url value="/resources/js/parsley.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/pageupdate.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/validator.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>--%>
</body>
</html>
