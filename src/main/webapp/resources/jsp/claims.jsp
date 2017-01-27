<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
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
    <link href="<c:url value="/resources/css/lib/jquery-ui.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/hosteladvert.css"/>" rel="stylesheet">
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
    <script src="<c:url value="/resources/js/app/controller/ClaimsController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/MenuBarController.js"/>"></script>
    <script src="<c:url value="/resources/js/parsley/validator.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/en.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/ru.js"/>"></script>
    <script>
        $(document).ready(function () {
            window.Parsley.setLocale($("#locale").val().substring(0, 2));
        });
    </script>
    <%--<script src="<c:url value="/resources/js/app/ajaxuser.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/app/ajaxrequests.js"/>"></script>--%>
    <%--<script src="<c:url value="/resources/js/app/notification.js"/>"></script>--%>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="body-style">
<c:set var="unconfirmedClaims" value="${unconfirmedClaims}"/>
<c:import url="common/navbar.jsp"/>
<c:import url="modal/regmodal.jsp"/>
<c:import url="modal/depositmodal.jsp"/>
<c:import url="modal/usermodal.jsp"/>
<c:import url="modal/messagemodal.jsp"/>
<c:import url="modal/aboutmodal.jsp"/>
<div id="claims" class="main-content padd-top">
    <input type="hidden" id="main-path" value="${pageContext.request.contextPath}">
    <div class="container">
        <div class="row">
            <div class="panel panel-default panel-table">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col col-xs-6">
                            <h3 class="panel-title">
                                <span class="fa fa-list" aria-hidden="true"></span>
                                <span><fmt:message key="page.claims.title"/></span>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th class="hidden-xs">ID</th>
                            <th><span class="fa fa-user" aria-hidden="true"></span> <fmt:message
                                    key="page.claims.table.user"/></th>
                            <th><span class="fa fa-h-square" aria-hidden="true"></span> <fmt:message
                                    key="page.claims.table.hostel"/></th>
                            <th><span class="fa fa-users" aria-hidden="true"></span> <fmt:message
                                    key="page.claims.table.places"/></th>
                            <th><span class="fa fa-calendar-plus-o" aria-hidden="true"></span> <fmt:message
                                    key="page.form.datein"/></th>
                            <th><span class="fa fa-calendar-minus-o" aria-hidden="true"></span> <fmt:message
                                    key="page.form.dateout"/></th>
                            <th><span class="fa fa-cog" aria-hidden="true"></span> <fmt:message
                                    key="page.claims.table.action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="claim" items="${unconfirmedClaims}">
                            <input name="user_id" type="hidden" value="${claim.userId}">
                            <input name="hostel_id" type="hidden" value="${claim.hostelId}">
                            <tr>
                                <td>${claim.claimId}</td>
                                <td><a href="#userModal" role="button" data-toggle="modal"
                                       data-name="user${claim.userId}" data-user-id="${claim.userId}"></a></td>
                                <td><span data-name="hostel${claim.hostelId}" data-hostel-id="${claim.hostelId}"></span>
                                </td>
                                <td>${claim.requiredPlaces}</td>
                                <td>${claim.dateIn}</td>
                                <td>${claim.dateOut}</td>
                                <td class="text-center">
                                    <a class="btn btn-success"
                                       href="${pageContext.request.contextPath}/service?command=confirm_claim&claimId=${claim.claimId}">
                                        <span class="fa fa-check" aria-hidden="true"></span>
                                        <span> <fmt:message key="page.claims.table.confirm"/></span>
                                    </a>
                                    <a class="btn btn-danger"
                                       href="${pageContext.request.contextPath}/service?command=delete_claim&claimId=${claim.claimId}">
                                        <span class="fa fa-trash" aria-hidden="true"></span>
                                        <span> <fmt:message key="page.claims.table.delete"/></span>
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
