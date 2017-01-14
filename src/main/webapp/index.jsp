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
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/menu.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/parsley.min.js"/>"></script>
    <script src="<c:url value="/resources/js/hostel.js"/>"></script>
    <script src="<c:url value="/resources/js/validator.js"/>"></script>
    <script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>
    <script src="<c:url value="/resources/js/pageupdate.js"/>"></script>
    <script src="<c:url value="/resources/js/notification.js"/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="body-style">
<c:import url="resources/jsp/common/navbar.jsp"/>
<c:import url="resources/jsp/common/regmodal.jsp"/>
<c:import url="resources/jsp/common/depositmodal.jsp"/>
<c:import url="resources/jsp/common/messagemodal.jsp"/>
<div class="main-content">
    <div class="panel-content">
        <div class="container table-row">
            <div class="row title-panel">
                <h2>Бронирование хостелов с belhostel.</h2>
                <div class="under-title">Создайте профиль на нашем сайте и просматривайте, бронируйте, оплачивайте
                    хостелы в
                    любое время!
                </div>
            </div>
        </div>
        <div class="container table-row">
            <div class="row content">
                <div class="container text-center booking-panel center-block">
                    <div class="row vert-center">
                        <form role="form" class="form-inline form" method="post" action="${pageContext.request.contextPath}/service">
                            <div class="form-group">
                                <span class="forinputlabel">Расположение</span>
                                <div class="input-group">
                            <span class="input-group-addon">
                                <span class="fa fa-map-marker"></span>
                            </span>
                                    <select id="city" class="form-control" name="city" required="true">
                                        <option value="" disabled selected hidden>
                                            Выберите город
                                        </option>
                                        <option value="Minsk">Минск</option>
                                        <option value="Grodno">Гродно</option>
                                        <option value="Gomel">Гомель</option>
                                        <option value="Mogilev">Могилев</option>
                                        <option value="Vitebsk">Витебск</option>
                                        <option value="Brest">Брест</option>
                                    </select>

                                </div>
                            </div>
                            <div class="form-group">
                                <span class="forinputlabel">Дата въезда</span>
                                <div class="input-group">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar-plus-o" aria-hidden="true"></span>
                                </span>
                                    <input id="date_in" type="text" class="form-control" name="date_in" required="true" placeholder="дата въезда">
                                </div>
                            </div>
                            <div class="form-group">
                                <span class="forinputlabel">Дата выезда</span>
                                <div class="input-group">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar-minus-o" aria-hidden="true"></span>
                            </span>
                                    <input id="date_out" type="text"  class="form-control" name="date_out" required="true" placeholder="дата выезда">
                                </div>
                            </div>
                            <div class="form-group">
                                <span class="forinputlabel">Места</span>
                                <input id="guests" placeholder="Количество мест" type="text" class="form-control" name="guests" required="true">
                            </div>
                            <div class="form-group">
                                <span class="forinputlabel label-butt">Отправить</span>
                                <button type="submit" class="btn btn-success">Подобрать</button>
                            </div>
                            <input type="hidden" name="command" value="find_hostels">
                            <input type="hidden" name="type" value="claim">
                        </form>
                    </div>
                </div>
                <!--</div>-->
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="cities">
                <div class="city-content">
                    <div class="title-panel">
                        <h2>Лучшие предложения на хостелы в одном из этих городов!</h2>
                        <div class="under-title">Забронируйте номер и убедитесь сами.</div>
                    </div>
                </div>
                <div class="table-row">
                    <div class="container marg-bott">
                        <div class="row">
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Minsk">
                                    <img src="<c:url value="/resources/img/minsk.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span>Минск</span></a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Gomel">
                                    <img src="<c:url value="/resources/img/gomel.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span>Гомель</span>
                                </a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Grodno">
                                    <img src="<c:url value="/resources/img/grodno.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span>Гродно</span>
                                </a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Mogilev">
                                    <img src="<c:url value="/resources/img/mogilev.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span>Могилев</span></a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Brest">
                                    <img src="<c:url value="/resources/img/brest.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span>Брест</span></a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Vitebsk">
                                    <img src="<c:url value="/resources/img/vitebsk.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span>Витебск</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="resources/jsp/common/footer.jsp"/>
<%--<hr>--%>
<%--<div class="row">--%>
<%--<div class="col-xs-8">--%>
<%--<ul class="list-unstyled list-inline pull-left">--%>
<%--<li><a href="#">Terms of Service</a></li>--%>
<%--<li><a href="#">Contact Us</a></li>--%>
<%--<li><a href="#">Privacy</a></li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--<div class="col-xs-4">--%>
<%--<p class="text-muted pull-right">© 2015 Company Name. All rights reserved</p>--%>
<%--</div>--%>
<%--</div>--%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>--%>
<%--<!-- Include all compiled plugins (below), or include individual files as needed -->--%>
<%--<script src="<c:url value="/resources/js/jquery.1.10.2.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/parsley.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/validator.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/pageupdate.js"/>"></script>--%>
</body>
</html>