<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="page.title"/></title>
    <link href="<c:url value="/resources/css/lib/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/lib/jquery-ui.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/lib/font-awesome.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/resources/js/lib/jquery.1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/jquery.i18n.properties-min-1.0.9.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/parsley.min.js"/>"></script>

    <script src="<c:url value="/resources/js/app/service/MenuBarService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/MenuBarController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/DateController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/pageupdate.js"/>"></script>
    <script src="<c:url value="/resources/js/parsley/validator.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/en.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/ru.js"/>"></script>
    <script>
        $( document ).ready(function() {
            window.Parsley.setLocale($("#locale").val().substring(0, 2));
        });
    </script>
    <%--<script src="<c:url value="/resources/js/app/hostel.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/app/validator.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/app/ajaxrequests.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/app/pageupdate.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/app/notification.js"/>"></script>--%>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="body-style">
<c:import url="resources/jsp/common/navbar.jsp"/>
<c:import url="resources/jsp/modal/regmodal.jsp"/>
<c:import url="resources/jsp/modal/depositmodal.jsp"/>
<c:import url="resources/jsp/modal/messagemodal.jsp"/>
<c:import url="resources/jsp/modal/aboutmodal.jsp"/>
<div class="main-content">
    <div class="panel-content">
        <div class="container table-row">
            <div class="row title-panel">
                <h2><fmt:message key="page.main.title"/></h2>
                <div class="under-title"><fmt:message key="page.main.undertitle"/></div>
            </div>
        </div>
        <div class="container table-row">
            <div class="row content">
                <div class="container text-center booking-panel center-block">
                    <div class="row vert-center">
                        <c:import url="resources/jsp/common/findform.jsp"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="cities">
                <div class="city-content">
                    <div class="title-panel">
                        <h2><fmt:message key="page.main.cities.title"/></h2>
                        <div class="under-title"><fmt:message key="page.main.cities.undertitle"/></div>
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
                                    <span><fmt:message key="page.form.minsk"/></span></a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Gomel">
                                    <img src="<c:url value="/resources/img/gomel.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span><fmt:message key="page.form.gomel"/></span>
                                </a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Grodno">
                                    <img src="<c:url value="/resources/img/grodno.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span><fmt:message key="page.form.grodno"/></span>
                                </a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Mogilev">
                                    <img src="<c:url value="/resources/img/mogilev.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span><fmt:message key="page.form.mogilev"/></span></a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Brest">
                                    <img src="<c:url value="/resources/img/brest.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span><fmt:message key="page.form.brest"/></span></a>
                            </div>
                            <div class="col-lg-2">
                                <a class="under-img"
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=Vitebsk">
                                    <img src="<c:url value="/resources/img/vitebsk.jpg"/>" class="img-circle img-city"
                                         width="158" height="158">
                                    <span><fmt:message key="page.form.vitebsk"/></span></a>
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