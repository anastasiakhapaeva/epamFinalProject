<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/lib/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/lib/jquery-ui.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/hosteladvert.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/lib/font-awesome.css"/>" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">

    <script src="<c:url value="/resources/js/lib/jquery.1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/jquery.i18n.properties-min-1.0.9.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/lib/parsley.min.js"/>"></script>

    <script src="<c:url value="/resources/js/app/service/MenuBarService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/service/LoadService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/MenuBarController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/DateController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/CatalogController.js"/>"></script>
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
<c:import url="common/navbar.jsp"/>
<c:import url="modal/regmodal.jsp"/>
<c:import url="modal/depositmodal.jsp"/>
<c:import url="modal/newhostelmodal.jsp"/>
<c:import url="modal/messagemodal.jsp"/>
<c:import url="modal/aboutmodal.jsp"/>
<div id="catalog" class="main-content padd-top">
    <input type="hidden" id="main-path" value="${pageContext.request.contextPath}">
    <input type="hidden" id="loadType" value="main">
    <div>

        <c:choose>
            <c:when test="${not empty myHostels}">
                <div class="text-center">
                    <c:choose>
                        <c:when test="${bookType == 'payment'}">
                            <h1 class="myHostelsTitle"><fmt:message key="page.bookedhostels.payment"/></h1>
                        </c:when>
                        <c:otherwise>
                            <h1 class="myHostelsTitle"><fmt:message key="page.bookedhostels.reservation"/></h1>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:forEach var="elem" items="${myHostels}">
                    <div class="container">
                        <div class="row">
                            <div>
                                <div class="panel panel-default  panel--styled">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-12 panelTop">
                                                <input name="id" type="hidden" value="${elem.hostelId}">
                                                <div class="col-md-4 text-center">
                                                    <img id="${elem.hostelId}" class="img-responsive inline-disp"
                                                         src="http://placehold.it/150x150" alt=""/>
                                                </div>
                                                <div class="col-md-8">
                                                    <h2 class="hostel-name">${elem.name}</h2>
                                                    <div>
                                                        <span class="fa fa-map-marker" aria-hidden="true"></span>
                                                        <span> ${elem.city}, </span>
                                                        <span>${elem.address} </span>
                                                    </div>
                                                    <div>
                                                        <span class="fa fa-phone" aria-hidden="true"></span>
                                                        <span> ${elem.phone}</span>
                                                    </div>
                                                    <div>
                                                        <p class="hostel-descript">${elem.description}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <hr class="panel-devider"/>
                                        </div>
                                        <div class="row panelBottom">
                                            <div class="col-md-6 text-left">
                                                <h5><fmt:message key="page.catalog.price"/> <span
                                                        class="itemPrice">$${elem.price}</span></h5>
                                            </div>
                                            <div class="col-md-6 text-right">
                                                <a class="btn btn-success"
                                                   href="${pageContext.request.contextPath}/service?command=show_hostel&id=${elem.hostelId}">
                                                    <span class="fa fa-info" aria-hidden="true"></span> <fmt:message
                                                        key="page.catalog.more"/>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="text-center">
                    <h1 class="myHostelsTitle"><fmt:message key="page.bookedhostels.nohostels"/></h1>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<c:import url="common/footer.jsp"/>
</body>
</html>
