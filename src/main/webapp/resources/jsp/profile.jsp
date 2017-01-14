<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<fmt:setLocale value="${locale}"/>--%>
<%--<fmt:setBundle basename="i18n.text"/>--%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Hostels in Belarus</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/menu.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="body-style">
<c:import url="common/navbar.jsp"/>
<c:import url="common/depositmodal.jsp"/>
<div class="main-content">
    <div class="container padd-top">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>Профиль пользователя</h4></div>
                    <div class="panel-body">
                        <div class="box box-info">
                            <div class="box-body">
                                <div class="col-sm-6">
                                    <div align="center"><img alt="User Pic"
                                                             src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg"
                                                             id="profile-image1" class="img-circle img-responsive">
                                        <input id="profile-image-upload" class="hidden" type="file">
                                        <div style="color:#999;">click here to change profile image</div>
                                        <!--Upload Image Js And Css-->

                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <h4 class="profile-username">${userProfile.lastName} ${userProfile.firstName}</h4></span>
                                    <c:choose>
                                        <c:when test="${currentUser.admin}">
                                            <span><p>Админ</p></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span><p>Пользователь</p></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="clearfix"></div>
                                <hr class="devider">


                                <div class="col-sm-5 col-xs-6 tital">Имя:</div>
                                <div class="col-sm-7 col-xs-6 ">${userProfile.firstName}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Фамилия:</div>
                                <div class="col-sm-7">${userProfile.lastName}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Email:</div>
                                <div class="col-sm-7">${userProfile.email}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Номер телефона:</div>
                                <div class="col-sm-7">${userProfile.phone}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Город проживания:</div>
                                <div class="col-sm-7">${userProfile.city}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Деньги:</div>
                                <div class="col-sm-7">${currentUser.money}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Скидка:</div>
                                <div class="col-sm-7">${currentUser.discount}</div>

                                <div class="clearfix"></div>
                                <div class="bot-border"></div>

                                <div class="col-sm-5 col-xs-6 tital">Бан:</div>
                                <div class="col-sm-7">${currentUser.banned}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="common/footer.jsp"/>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->

<script src="<c:url value="/resources/js/jquery.1.10.2.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/parsley.min.js"/>"></script>
<script src="<c:url value="/resources/js/validator.js"/>"></script>
<script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>
</body>
</html>