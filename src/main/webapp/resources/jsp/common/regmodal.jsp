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
    <link href="<c:url value="/resources/css/hostel.css"/>" rel="stylesheet">
</head>
<body>
<div id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Добро пожаловать на сайт!</h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                <ul class="nav nav-tabs" id="login-or-register">
                    <li><a href="#register" data-toggle="tab">Регистрация</a></li>
                    <li class="active"><a href="#profile" data-toggle="tab">Авторизация</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane" id="register">
                        <form id="register-form" role="form" class="form-horizontal my-form"
                              action="${pageContext.request.contextPath}/service" method="post" data-parsley-validate>
                            <div class="form-group">
                                <label for="userLogin" class="control-label col-sm-4">Ваш Логин*</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userLogin" id="userLogin" type="text"
                                           placeholder="введите логин"
                                           data-parsley-login="/^[a-z](\w){4,}$/i"
                                           data-parsley-required>
                                    <div id="err-login"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userPass" class="control-label col-sm-4">Ваш Пароль*</label>
                                <div class="col-sm-6">
                                    <input id="pass_main " class="form-control" name="userPass" id="userPass"
                                           type="password"
                                           placeholder="введите пароль"
                                           data-parsley-password="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$"
                                           data-parsley-pass="pass_conf"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rePass" class="control-label col-sm-4">Повторите Пароль*</label>
                                <div class="col-sm-6">
                                    <input id="pass_conf" class="form-control" name="rePass" id="rePass" type="password"
                                           placeholder="повторите пароль"
                                           data-parsley-password="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$"
                                           data-parsley-pass="pass_main"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userEmail" class="control-label col-sm-4">Ваш Email*</label>
                                <div class="col-sm-6">
                                    <input name="userEmail" id="userEmail" class="form-control" type="text"
                                           placeholder="введите ваш email"
                                           data-parsley-type="email"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userFirstName" class="control-label col-sm-4">Ваше Имя*</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userFirstName" id="userFirstName" type="text"
                                           placeholder="ваше имя"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userLastName" class="control-label col-sm-4">Ваша Фамилия*</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userLastName" id="userLastName" type="text"
                                           placeholder="ваша фамилия"
                                           data-parsley-required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userCity" class="control-label col-sm-4">Город проживания</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userCity" id="userCity" type="text"
                                           placeholder="город">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userPhone" class="control-label col-sm-4">Номер телефона</label>
                                <div class="col-sm-6">
                                    <input class="form-control" name="userPhone" id="userPhone" type="text"
                                           placeholder="номер телефона">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 inline-disp">
                                    <input class="form-control btn btn-success" type="submit" id="register_button"
                                           value="Зарегистрироваться"/>
                                </div>
                                <div class="inline-disp">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                                </div>
                            </div>
                            <input type="hidden" name="command" value="register">
                        </form>
                    </div>
                    <div class="tab-pane active" id="profile">
                        <form id="authorization-form" role="form" class="form-horizontal my-form"
                              action="${pageContext.request.contextPath}/service" method="post" data-parsley-validate>
                            <div class="form-group">
                                <label for="user_login" class="control-label col-sm-4">Ваш Логин</label>
                                <div class="col-sm-6">
                                    <input class="form-control" id="user_login" name="login" placeholder="Логин"
                                           data-parsley-login="/^[a-z](\w){4,}$/i"
                                           data-parsley-required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="user_password" class="control-label col-sm-4">Ваш Пароль</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" id="user_password" name="password"
                                           placeholder="Пароль"
                                           data-parsley-password="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$"
                                           data-parsley-required/>
                                </div>
                            </div>
                            <div id="err-autho"></div>
                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 inline-disp">
                                    <input class="form-control btn btn-success" type="submit" id="login_button"
                                           value="Войти"/>
                                </div>
                                <div class="inline-disp">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
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
<%--<script src="<c:url value="/resources/js/parsley.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/validator.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>--%>
</body>
</html>
