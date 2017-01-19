<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.text"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="page.title"/></title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/menu.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/jquery-ui.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/hosteladvert.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/resources/js/parsley.min.js"/>"></script>
    <script src="<c:url value="/resources/js/ajaxuser.js"/>"></script>
    <script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>
    <script src="<c:url value="/resources/js/notification.js"/>"></script>
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
<c:import url="common/regmodal.jsp"/>
<c:import url="common/depositmodal.jsp"/>
<c:import url="common/usermodal.jsp"/>
<c:import url="common/messagemodal.jsp"/>
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
                <%--<div class="col col-xs-6 text-right">--%>
                    <%--<button type="button" class="btn btn-sm btn-primary btn-create">Create New</button>--%>
                <%--</div>--%>
            </div>
        </div>
        <div class="panel-body">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th class="hidden-xs">ID</th>
                    <th><span class="fa fa-user" aria-hidden="true"></span> <fmt:message key="page.claims.table.user"/></th>
                    <th><span class="fa fa-h-square" aria-hidden="true"></span> <fmt:message key="page.claims.table.hostel"/></th>
                    <th><span class="fa fa-users" aria-hidden="true"></span> <fmt:message key="page.claims.table.places"/></th>
                    <th><span class="fa fa-calendar-plus-o" aria-hidden="true"></span> <fmt:message key="page.form.datein"/></th>
                    <th><span class="fa fa-calendar-minus-o" aria-hidden="true"></span> <fmt:message key="page.form.dateout"/></th>
                    <th><span class="fa fa-cog" aria-hidden="true"></span> <fmt:message key="page.claims.table.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="claim" items="${unconfirmedClaims}">
                    <input name="user_id" type="hidden" value="${claim.userId}">
                    <input name="hostel_id" type="hidden" value="${claim.hostelId}">
                    <tr>
                        <td>${claim.claimId}</td>
                        <td><a  href="#userModal" role="button" data-toggle="modal" data-name="user${claim.userId}" data-user-id="${claim.userId}"></a></td>
                        <td><a data-name="hostel${claim.hostelId}" data-hostel-id="${claim.hostelId}"></a></td>
                        <td>${claim.requiredPlaces}</td>
                        <td>${claim.dateIn}</td>
                        <td>${claim.dateOut}</td>
                        <td class="text-center">
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/service?command=confirm_claim&claimId=${claim.claimId}">
                                <span class="fa fa-check" aria-hidden="true"></span>
                                <span> <fmt:message key="page.claims.table.confirm"/></span>
                            </a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/service?command=delete_claim&claimId=${claim.claimId}">
                                <span class="fa fa-trash" aria-hidden="true"></span>
                                <span> <fmt:message key="page.claims.table.delete"/></span>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
        <%--<div class="panel-footer">--%>
            <%--<div class="row">--%>
                <%--<div class="col col-xs-4">Page 1 of 5--%>
                <%--</div>--%>
                <%--<div class="col col-xs-8">--%>
                    <%--<ul class="pagination hidden-xs pull-right">--%>
                        <%--<li><a href="#">1</a></li>--%>
                        <%--<li><a href="#">2</a></li>--%>
                        <%--<li><a href="#">3</a></li>--%>
                        <%--<li><a href="#">4</a></li>--%>
                        <%--<li><a href="#">5</a></li>--%>
                    <%--</ul>--%>
                    <%--<ul class="pagination visible-xs pull-right">--%>
                        <%--<li><a href="#">«</a></li>--%>
                        <%--<li><a href="#">»</a></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>
    <%--<div class="container">--%>
        <%--<div class="row content-wrapper">--%>
            <%--<table class="table table-striped">--%>
                <%--<thead>--%>
                <%--<a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>
                <%--<tr>--%>
                    <%--<th>ID</th>--%>
                    <%--<th>Title</th>--%>
                    <%--<th>Parent ID</th>--%>
                    <%--<th class="text-center">Action</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tr>--%>
                    <%--<td>1</td>--%>
                    <%--<td>News</td>--%>
                    <%--<td>News Cate</td>--%>
                    <%--<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span class="glyphicon glyphicon-edit"></span> Edit</a> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Del</a></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>2</td>--%>
                    <%--<td>Products</td>--%>
                    <%--<td>Main Products</td>--%>
                    <%--<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span class="glyphicon glyphicon-edit"></span> Edit</a> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Del</a></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>3</td>--%>
                    <%--<td>Blogs</td>--%>
                    <%--<td>Parent Blogs</td>--%>
                    <%--<td class="text-center"><a class='btn btn-info btn-xs' href="#"><span class="glyphicon glyphicon-edit"></span> Edit</a> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Del</a></td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</div>--%>
    <%--</div>--%>
        </div>
    </div>
</div>
<c:import url="common/footer.jsp"/>
</body>
</html>
