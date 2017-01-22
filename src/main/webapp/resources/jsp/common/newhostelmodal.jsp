<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 11.01.2017
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addHostelModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title"><fmt:message key="hostelmodal.title"/></h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                <%--<div id="book-info" class="text-info marg-bott text-center">Вы оплачивает первые сутки проживания сразу на сайте, а--%>
                    <%--оставшуюся сумму доплачиваете непосредственно при заезде.--%>
                <%--</div>--%>
                <%--<div id="book-payment" class="text-danger marg-bott text-center">Отменить оплату нельзя!</div>--%>
                    <form id="add-hostel-form" role="form" class="form-horizontal my-form" enctype="multipart/form-data"
                          action="${pageContext.request.contextPath}/service" method="post" data-parsley-validate>
                        <div class="form-group">
                            <label for="newHostelName" class="control-label col-sm-4"><fmt:message key="page.form.hostelname"/>*</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelName" id="newHostelName" type="text"
                                       placeholder="<fmt:message key="page.form.hostelname"/>"
                                       data-parsley-required>
                                <%--<div id="err-login"></div>--%>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelCity" class="control-label col-sm-4"><fmt:message key="page.form.hostelcity"/>*</label>
                            <div class="col-sm-6">
                                <select id="newHostelCity" class="form-control" name="newHostelCity" required="true">
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
                            <label for="newHostelAddress" class="control-label col-sm-4"><fmt:message key="page.form.hosteladdress"/>*</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelAddress" id="newHostelAddress"
                                       type="text"
                                       placeholder="<fmt:message key="page.form.hostelcity"/>"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelPhone" class="control-label col-sm-4"><fmt:message key="page.form.hostelphone"/>*</label>
                            <div class="col-sm-6">
                                <input id="newHostelPhone" class="form-control" name="newHostelPhone" type="name"
                                       placeholder="<fmt:message key="page.form.hostelphone"/>"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelPrice" class="control-label col-sm-4"><fmt:message key="page.form.hostelprice"/></label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelPrice" id="newHostelPrice" type="text"
                                       placeholder="<fmt:message key="page.form.hostelprice"/>"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelPlaces" class="control-label col-sm-4"><fmt:message key="page.form.hostelplaces"/>*</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelPlaces" id="newHostelPlaces" type="text"
                                       placeholder="<fmt:message key="page.form.places"/>"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelDesc" class="control-label col-sm-4"><fmt:message key="page.form.hosteldesc"/>*</label>
                            <div class="col-sm-6">
                                <textarea placeholder="<fmt:message key="page.form.hosteldesc"/>" rows="7" class="form-control" name="newHostelDesc" id="newHostelDesc" data-parsley-required>${current.description}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-4"><fmt:message key="hostelmodal.photos"/>*</label>
                            <div class="col-sm-6">
                                <input type="file" class="form-control" name="newHostelPhoto1" id="newHostelPhoto1" data-parsley-required accept="image/jpeg">
                                <input type="file" class="form-control" name="newHostelPhoto2" id="newHostelPhoto2" data-parsley-required accept="image/jpeg">
                                <input type="file" class="form-control" name="newHostelPhoto3" id="newHostelPhoto3" data-parsley-required accept="image/jpeg">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-5 inline-disp">
                                <input class="form-control btn btn-success" type="submit"
                                       value="<fmt:message key="hostelmodal.button.add"/>"/>
                            </div>
                            <div class="inline-disp">
                                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="modal.button.close"/></button>
                            </div>
                        </div>
                        <input type="hidden" name="command" value="add_hostel">
                    </form>
            </div>
        </div>
    </div>
</div>


