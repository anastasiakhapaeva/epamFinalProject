<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 22.01.2017
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="findForm" role="form" class="form-inline form" method="post" action="${pageContext.request.contextPath}/service">
    <div class="form-group">
        <span class="forinputlabel"><fmt:message key="page.form.location"/></span>
        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="fa fa-map-marker"></span>
                            </span>
            <select id="city" class="form-control" name="city" required="true">
                <option value="" disabled selected hidden>
                    <fmt:message key="page.form.choose"/>
                </option>
                <option value="Minsk"><fmt:message key="page.form.minsk"/></option>
                <option value="Grodno"><fmt:message key="page.form.grodno"/></option>
                <option value="Gomel"><fmt:message key="page.form.gomel"/></option>
                <option value="Mogilev"><fmt:message key="page.form.mogilev"/></option>
                <option value="Vitebsk"><fmt:message key="page.form.vitebsk"/></option>
                <option value="Brest"><fmt:message key="page.form.brest"/></option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <span class="forinputlabel"><fmt:message key="page.form.datein"/></span>
        <div class="input-group">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar-plus-o" aria-hidden="true"></span>
                                </span>
            <input id="date_in" type="text" class="form-control" name="date_in" required="true"
                   placeholder="<fmt:message key="page.form.datein"/>">
        </div>
    </div>
    <div class="form-group">
        <span class="forinputlabel"><fmt:message key="page.form.dateout"/></span>
        <div class="input-group">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar-minus-o" aria-hidden="true"></span>
                            </span>
            <input id="date_out" type="text" class="form-control" name="date_out" required
                   placeholder="<fmt:message key="page.form.dateout"/>">
        </div>
    </div>
    <div class="form-group">
        <span class="forinputlabel"><fmt:message key="page.form.places"/></span>
        <input id="guests" placeholder="<fmt:message key="page.form.places.holder"/>" type="number" min="1"
               class="form-control" name="guests" required="">
    </div>
    <div class="form-group">
        <span class="forinputlabel label-butt">Send</span>
        <button id="sendForm" type="submit" class="btn btn-success"><fmt:message key="page.form.button.find"/></button>
    </div>
    <input type="hidden" name="command" value="find_hostels">
    <input type="hidden" name="type" value="claim">
    <input id="pageNum" type="hidden" name="pageNum" value="1">
</form>
<script>
    $("#date_in, #date_out").keydown(function (e) {
        e.preventDefault();
    });

    $('#guests').on('change invalid', function () {
        if (this.validity.valueMissing) {
            this.setCustomValidity($.i18n.prop('msg.required'));
        } else if (this.validity.rangeUnderflow) {
            this.setCustomValidity($.i18n.prop('msg.lessmin'));
        } else {
            this.setCustomValidity('');
        }
    });

</script>
