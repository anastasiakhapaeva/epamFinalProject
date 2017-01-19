<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.text"/>
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

    <script src="<c:url value="/resources/js/jquery.1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-ui.min.js"/>"></script>
    <script src="<c:url value="/resources/js/parsley.min.js"/>"></script>
    <script src="<c:url value="/resources/js/ajaxloadimages.js"/>"></script>
    <script src="<c:url value="/resources/js/hostel.js"/>"></script>
    <script src="<c:url value="/resources/js/validator.js"/>"></script>
    <script src="<c:url value="/resources/js/pageupdate.js"/>"></script>
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
<c:import url="common/navbar.jsp"/>
<c:import url="common/regmodal.jsp"/>
<c:import url="common/depositmodal.jsp"/>
<c:import url="common/newhostelmodal.jsp"/>
<c:import url="common/messagemodal.jsp"/>
<div id="catalog" class="main-content padd-top">
    <input type="hidden" id="main-path" value="${pageContext.request.contextPath}">
    <input type="hidden" id="loadType" value="main">
    <div class="container">
        <div class="row content">
            <div class="container text-center booking-panel center-block">
                <div class="row vert-center">
                    <form role="form" class="form-inline form" method="post"
                          action="${pageContext.request.contextPath}/service">
                        <div class="form-group">
                            <span class="forinputlabel"><fmt:message key="page.form.location"/></span>
                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="fa fa-map-marker"></span>
                            </span>
                                <select id="city" class="form-control" name="city" required="true">
                                    <option value="" disabled selected hidden>
                                        <fmt:message key="page.form.choose"/>
                                    </option>
                                    <option value="Minsk"><fmt:message key="page.form.minsk"/></option>
                                    <option value="Grodno"><fmt:message key="page.form.grodno"/></option>
                                    <option value="Gomel"><fmt:message key="page.form.gomel"/></option>
                                    <option value="Mogilev"><fmt:message key="page.form.mogilev"/></option>
                                    <option value="Vitebsk"><fmt:message key="page.form.vitebsk"/></option>
                                    <option value="Brest"><fmt:message key="page.form.brest"/></option>
                                </select>

                            </div>
                        </div>
                        <div class="form-group">
                            <span class="forinputlabel"><fmt:message key="page.form.datein"/></span>
                            <div class="input-group">
                                <span class="input-group-addon">
                                    <span class="fa fa-calendar-plus-o" aria-hidden="true"></span>
                                </span>
                                <input id="date_in" type="text" class="form-control" name="date_in" required="true"
                                       placeholder="<fmt:message key="page.form.datein"/>">
                            </div>
                        </div>
                        <div class="form-group">
                            <span class="forinputlabel"><fmt:message key="page.form.dateout"/></span>
                            <div class="input-group">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar-minus-o" aria-hidden="true"></span>
                            </span>
                                <input id="date_out" type="text" class="form-control" name="date_out" required="true"
                                       placeholder="<fmt:message key="page.form.dateout"/>">
                            </div>
                        </div>
                        <div class="form-group">
                            <span class="forinputlabel"><fmt:message key="page.form.places"/></span>
                            <input id="guests" placeholder="<fmt:message key="page.form.places.holder"/>" type="text" class="form-control"
                                   name="guests" required="true">
                        </div>
                        <div class="form-group">
                            <span class="forinputlabel label-butt">Отправить</span>
                            <button type="submit" class="btn btn-success"><fmt:message key="page.form.button.find"/></button>
                        </div>
                        <input type="hidden" name="command" value="find_hostels">
                        <input type="hidden" name="type" value="claim">
                    </form>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${currentUser.admin}">
        <div class="container">
            <div class="row">
                <div class="pull-right">
                    <a href="#addHostelModal" type="button" class="btn btn-primary"  data-toggle="modal"><fmt:message key="page.catalog.addhostel"/></a>
                </div>
            </div>
        </div>
    </c:if>
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
                                    <h5><fmt:message key="page.catalog.price"/> <span class="itemPrice">$${elem.price}</span></h5>
                                </div>
                                <div class="col-md-6 text-right">
                                    <a class="btn btn-success"
                                       href="${pageContext.request.contextPath}/service?command=show_hostel&index=${hostels.indexOf(elem)}">
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
</div>
<c:import url="common/footer.jsp"/>

</body>
</html>
