<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 01.01.2017
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="page.title"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/lib/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/hostel.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/lib/jquery-ui.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/lib/font-awesome.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/lib/jquery.1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/jquery.i18n.properties-min-1.0.9.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/parsley.min.js"/>"></script>

    <script src="<c:url value="/resources/js/app/service/MenuBarService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/service/LoadService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/service/AdminService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/service/BookingService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/DateController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/UserController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/MenuBarController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/pageupdate.js"/>"></script>
    <script src="<c:url value="/resources/js/parsley/validator.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/en.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/ru.js"/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="body-style">
<c:set var="users" value="${users}"/>
<c:import url="common/navbar.jsp"/>
<c:import url="modal/regmodal.jsp"/>
<c:import url="modal/depositmodal.jsp"/>
<c:import url="modal/usermodal.jsp"/>
<c:import url="modal/messagemodal.jsp"/>
<c:import url="modal/aboutmodal.jsp"/>
<div class="main-content padd-top">
    <div class="container">
        <div class="row">
            <div class="panel panel-default panel-table">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col col-xs-6">
                            <h3 class="panel-title">
                                <span class="fa fa-list" aria-hidden="true"></span>
                                <span><fmt:message key="users.users"/></span>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th><span><fmt:message
                                    key="page.claims.table.user"/></span></th>
                            <th><span><fmt:message key="usermodal.role"/></span></th>
                            <th class="text-center"><span><fmt:message key="page.profile.ban"/></span></th>
                            <th><span>Email</span></th>
                            <th><fmt:message
                                    key="page.claims.table.action"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td class="media-middle">
                                    <img alt="User Pic"
                                         src="http://image.flaticon.com/icons/svg/149/149071.svg"
                                         class="img-circle img-responsive inline-disp" width="50">
                                    <a href="#userModal" data-toggle="modal" data-name="user${user.userId}"
                                       data-user-id="${user.userId}"><c:out value="${user.profile.lastName}"/> <c:out
                                            value="${user.profile.firstName}"/></a>
                                </td>
                                <td class="text-center media-middle">
                                    <c:choose>
                                        <c:when test="${user.admin}">
                                            <span><fmt:message key="page.profile.admin"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span><fmt:message key="page.profile.user"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center media-middle">
                                    <c:choose>
                                        <c:when test="${user.banned}">
                                            <span><fmt:message key="page.profile.bany"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span><fmt:message key="page.profile.bann"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center media-middle">
                                    <c:out value="${user.profile.email}"/>
                                </td>
                                <td class="text-center media-middle">
                                    <a class="btn btn-success" href="#userModal" data-toggle="modal"
                                       data-name="user${user.userId}" data-user-id="${user.userId}">
                                        <span class="fa fa-pencil" aria-hidden="true"></span>
                                        <span> <fmt:message
                                                key="users.edit"/></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="common/footer.jsp"/>
</body>
</html>
