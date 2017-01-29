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
<div id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title"><fmt:message key="regmodal.title"/></h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs" id="login-or-register">
                    <li><a href="#register" data-toggle="tab"><fmt:message key="regmodal.registration"/></a></li>
                    <li class="active"><a href="#profile" data-toggle="tab"><fmt:message key="regmodal.authorization"/></a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane" id="register">
                        <form id="register-form" role="form" class="form-horizontal my-form"
                              action="${pageContext.request.contextPath}/service" method="post" data-parsley-validate>
                            <div class="form-group">
                                <label for="userLogin" class="control-label col-sm-4"><fmt:message key="regmodal.login"/>*</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userLogin" id="userLogin" type="text"
                                           placeholder="<fmt:message key="regmodal.login.holder"/>"
                                           data-parsley-login="/^[a-z](\w){4,}$/i"
                                           data-parsley-required>
                                    <div id="err-login"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userPass" class="control-label col-sm-4"><fmt:message key="regmodal.password"/>*</label>
                                <div class="col-sm-6">
                                    <input id="pass_main" class="form-control" name="userPass" id="userPass"
                                           type="password"
                                           placeholder="<fmt:message key="regmodal.password.holder"/>"
                                           data-parsley-password="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$"
                                           data-parsley-pass="pass_conf"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rePass" class="control-label col-sm-4"><fmt:message key="regmodal.repassword"/>*</label>
                                <div class="col-sm-6">
                                    <input id="pass_conf" class="form-control" name="rePass" id="rePass" type="password"
                                           placeholder="<fmt:message key="regmodal.repassword.holder"/>"
                                           data-parsley-password="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$"
                                           data-parsley-pass="pass_main"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userEmail" class="control-label col-sm-4"><fmt:message key="regmodal.email"/>*</label>
                                <div class="col-sm-6">
                                    <input name="userEmail" id="userEmail" class="form-control" type="text"
                                           placeholder="<fmt:message key="regmodal.email.holder"/>"
                                           data-parsley-type="email"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userFirstName" class="control-label col-sm-4"><fmt:message key="regmodal.name"/>*</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userFirstName" id="userFirstName" type="text"
                                           placeholder="<fmt:message key="regmodal.name.holder"/>"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userLastName" class="control-label col-sm-4"><fmt:message key="regmodal.secondname"/>*</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userLastName" id="userLastName" type="text"
                                           placeholder="<fmt:message key="regmodal.secondname.holder"/>"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userCity" class="control-label col-sm-4"><fmt:message key="regmodal.city"/></label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userCity" id="userCity" type="text"
                                           placeholder="<fmt:message key="regmodal.city.holder"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userPhone" class="control-label col-sm-4"><fmt:message key="regmodal.phone"/></label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userPhone" id="userPhone" type="text"
                                           placeholder="<fmt:message key="regmodal.phone.holder"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 inline-disp">
                                    <input class="form-control btn btn-success" type="submit" id="register_button"
                                           value="<fmt:message key="regmodal.button.signup"/>"/>
                                </div>
                                <div class="inline-disp">
                                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="modal.button.close"/></button>
                                </div>
                            </div>
                            <input type="hidden" name="command" value="register">
                        </form>
                    </div>
                    <div class="tab-pane active" id="profile">
                        <form id="authorization-form" role="form" class="form-horizontal my-form"
                              action="${pageContext.request.contextPath}/service" method="post" data-parsley-validate>
                            <div class="form-group">
                                <label for="user_login" class="control-label col-sm-4"><fmt:message key="regmodal.login"/></label>
                                <div class="col-sm-6">
                                    <input class="form-control" id="user_login" name="login" placeholder="<fmt:message key="regmodal.login.holder"/>"
                                           data-parsley-login="/^[a-z](\w){4,}$/i"
                                           data-parsley-required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="user_password" class="control-label col-sm-4"><fmt:message key="regmodal.password"/></label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" id="user_password" name="password"
                                           placeholder="<fmt:message key="regmodal.password.holder"/>"
                                           data-parsley-password="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$"
                                           data-parsley-required/>
                                </div>
                            </div>
                            <div id="err-autho"></div>
                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 inline-disp">
                                    <input class="form-control btn btn-success" type="submit" id="login_button"
                                           value="<fmt:message key="menu.signin"/>"/>
                                </div>
                                <div class="inline-disp">
                                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="modal.button.close"/></button>
                                </div>
                            </div>
                            <input type="hidden" name="command" value="login">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
