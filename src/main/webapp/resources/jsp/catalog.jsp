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
    <script src="<c:url value="/resources/js/i18n/en.js"/>"></script>
    <script src="<c:url value="/resources/js/i18n/ru.js"/>"></script>
    <script>
        $( document ).ready(function() {
            window.Parsley.setLocale($("#locale").val());
        });
    </script>
    <script src="<c:url value="/resources/js/parsley/validator.js"/>"></script>
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
    <div class="container">
        <div class="row content">
            <div class="container text-center booking-panel center-block">
                <div class="row vert-center">
                    <c:import url="common/findform.jsp"/>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${currentUser.admin}">
        <div class="container">
            <div class="row">
                <div class="pull-right">
                    <a href="#addHostelModal" type="button" class="btn btn-primary" data-toggle="modal"><fmt:message
                            key="page.catalog.addhostel"/></a>
                </div>
            </div>
        </div>
    </c:if>
    <c:choose>
    <c:when test="${not empty hostels}">
    <div class="text-center">
        <h1 class="myHostelsTitle"><fmt:message key="page.bookedhostels.found"/></h1>
    </div>
    <c:forEach var="elem" items="${hostels}">
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
                                        <h2 class="hostel-name"><c:out value="${elem.name}"/></h2>
                                        <div>
                                            <span class="fa fa-map-marker" aria-hidden="true"></span>
                                            <span> <c:out value="${elem.city}"/>, </span>
                                            <span><c:out value="${elem.address}"/> </span>
                                        </div>
                                        <div>
                                            <span class="fa fa-phone" aria-hidden="true"></span>
                                            <span> <c:out value="${elem.phone}"/></span>
                                        </div>
                                        <div>
                                            <p class="hostel-descript"><c:out value="${elem.description}"/></p>
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
                                            class="itemPrice">$<c:out value="${elem.price}"/></span></h5>
                                </div>
                                <div class="col-md-6 text-right">
                                    <a class="btn btn-success"
                                       href="${pageContext.request.contextPath}/service?command=show_hostel&id=${elem.hostelId}">
                                        <span class="fa fa-info" aria-hidden="true"></span> <fmt:message key="page.catalog.more"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="container">
        <div class="text-center">

            <ul class="pagination pagination-large">
                <input id="currPage" type="hidden" value="${currentPage}">
                <input id="hostelsPerPage" type="hidden" value="${perPage}">;
                <li>
                    <select id="selectPerPage" class="form-control perpage">
                        <option value="3">3</option>
                        <option value="5">5</option>
                        <option value="10">10</option>
                    </select>
                    <label style="color: white; font-weight: normal;"><fmt:message key="page.catalog.perpage"/></label>
                </li>
                <c:if test="${currentPage != 1}">
                    <c:choose>
                        <c:when test="${type == 'city'}">
                            <li>
                                <a data-page
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=${city}&perPage=${perPage}&pageNum=${currentPage - 1}">«</a>
                            </li>
                        </c:when>
                        <c:when test="${type == 'claim'}">
                            <li><a data-page href="" data-page-num="${currentPage - 1}">«</a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a data-page
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=all&perPage=${perPage}&pageNum=${currentPage - 1}">«</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <%--<c:when test="${currentPage eq i}">--%>
                        <c:when test="${false}">
                            <li id="lipage${i}"><span>${i}</span></li>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${type == 'city'}">
                                    <li id="lipage${i}">
                                        <a id="page${i}" data-page
                                           href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=${city}&perPage=${perPage}&pageNum=${i}">${i}</a>
                                    </li>
                                </c:when>
                                <c:when test="${type == 'claim'}">
                                    <li id="lipage${i}"><a id="page${i}" data-page href="" data-page-num="${i}">${i}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li id="lipage${i}">
                                        <a id="page${i}" data-page
                                           href="${pageContext.request.contextPath}/service?command=find_hostels&type=all&perPage=${perPage}&pageNum=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <c:choose>


                        <c:when test="${type == 'city'}">
                            <li>
                                <a data-page
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=city&city=${city}&perPage=${perPage}&pageNum=${currentPage + 1}">»</a>
                            </li>
                        </c:when>
                        <c:when test="${type == 'claim'}">
                            <li><a data-page href="" data-page-num="${currentPage + 1}">»</a></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a data-page
                                   href="${pageContext.request.contextPath}/service?command=find_hostels&type=all&perPage=${perPage}&pageNum=${currentPage + 1}">»</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </ul>
        </div>
    </div>
</c:when>
<c:otherwise>
    <div class="text-center">
        <h1 class="myHostelsTitle"><fmt:message key="page.catalog.nohostels"/></h1>
    </div>
</c:otherwise>
</c:choose>
</div>
<c:import url="common/footer.jsp"/>
</body>
</html>
