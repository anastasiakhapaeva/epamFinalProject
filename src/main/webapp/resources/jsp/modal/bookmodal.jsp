<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 27.12.2016
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="bookModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title"><fmt:message key="bookmodal.title"/></h4>
            </div>
            <div class="modal-body">
                <div id="book-info" class="text-info marg-bott text-center"><fmt:message key="bookmodal.payment.info"/>
                </div>
                <div id="book-payment" class="text-danger marg-bott text-center"><fmt:message key="bookmodal.payment.underinfo"/></div>
                <form id="payment-form" role="form" class="form-horizontal my-form" method="post"
                      action="${pageContext.request.contextPath}/service" data-parsley-validate>
                    <div class="form-group">
                        <label class="control-label col-sm-4"><fmt:message key="bookmodal.bookingtype"/></label>
                        <div class="col-sm-6">
                        <label class="radio-inline"><input checked type="radio" id="payment-radio" name="bookingType" value="Payment"><fmt:message key="bookmodal.payment"/></label>
                        <label class="radio-inline"><input type="radio" id="reservation-radio" name="bookingType" value="Reservation"><fmt:message key="bookmodal.reservation"/></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="date_in" class="control-label col-sm-4"><fmt:message key="page.form.datein"/></label>
                        <div class="input-group col-sm-6">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar-plus-o" aria-hidden="true"></span>
                                </span>
                            <input id="date_in" type="text" class="form-control datepicker" name="date_in"
                                   required="true" placeholder="<fmt:message key="page.form.datein"/>"
                                   data-parsley-errors-container="#err-datein">
                        </div>
                        <div id="err-datein" class="col-sm-offset-4"></div>
                    </div>
                    <div class="form-group">
                        <label for="date_out" class="control-label col-sm-4"><fmt:message key="page.form.dateout"/></label>
                        <div class="input-group col-sm-6">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar-minus-o" aria-hidden="true"></span>
                            </span>
                            <input id="date_out" type="text" class="form-control datepicker" name="date_out"
                                   required="true" placeholder="<fmt:message key="page.form.dateout"/>"
                                   data-parsley-errors-container="#err-dateout">
                        </div>
                        <div id="err-dateout" class="col-sm-offset-4"></div>
                    </div>
                    <div id="place-amount" class="col-sm-offset-4"></div>
                    <div class="form-group">
                        <label for="guests" class="control-label col-sm-4"><fmt:message key="page.form.places"/></label>
                        <div class="input-group col-sm-6">
                            <span class="input-group-addon">
                                <span class="fa fa-users" aria-hidden="true"></span>
                            </span>
                            <input id="guests" placeholder="<fmt:message key="page.form.places.holder"/>" type="text" class="form-control"
                                   name="guests" required="true"
                                   data-parsley-type="digits"
                                   data-parsley-max="${hostel.freePlaces}"
                                   data-parsley-max-message="<fmt:message key="bookmodal.error.maxplaces"/>"
                                   data-parsley-errors-container="#err-place">
                        </div>
                        <div id="err-place" class="col-sm-offset-4"></div>
                    </div>
                    <div id="total-price" class="text-center"></div>
                    <div id="err-booking" class="text-danger marg-bott text-center"></div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-4 inline-disp">
                            <input class="form-control btn btn-success" type="submit" id="booking_button"
                                   value="<fmt:message key="bookmodal.button.book"/>"/>
                        </div>
                        <div class="inline-disp">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="modal.button.close"/></button>
                        </div>
                    </div>
                    <input type="hidden" name="command" value="book_hostel">
                    <input type="hidden" name="hostel_id" value="${hostel.hostelId}">
                    <input type="hidden" name="user_id" value="${currentUser.userId}">
                </form>
            </div>
        </div>
    </div>
</div>
