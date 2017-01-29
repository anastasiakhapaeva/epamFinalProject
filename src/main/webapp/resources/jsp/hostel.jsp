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
    <script src="<c:url value="/resources/js/app/service/BookingService.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/DateController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/HostelController.js"/>"></script>
    <script src="<c:url value="/resources/js/app/controller/MenuBarController.js"/>"></script>
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
<c:set var="current" value="${hostel}"/>
<c:set var="bookedHostels" value="${bookedHostels}"/>
<c:set var="paidHostels" value="${paidHostels}"/>
<c:import url="common/navbar.jsp"/>
<c:import url="modal/bookmodal.jsp"/>
<c:import url="modal/regmodal.jsp"/>
<c:import url="modal/depositmodal.jsp"/>
<c:import url="modal/messagemodal.jsp"/>
<c:import url="modal/aboutmodal.jsp"/>
<div class="main-content padd-top">
    <input type="hidden" id="main-path" value="${pageContext.request.contextPath}">
    <input type="hidden" id="loadType" value="all">
    <input id="hostel_id" name="id" type="hidden" value="${hostel.hostelId}">
    <input id="user_id" type="hidden" value="${currentUser.userId}">
    <input type="hidden" id="hostel-price" value="${hostel.price}">
    <input type="hidden" id="us-mon" value="${currentUser.money}">
    <input type="hidden" id="us-discount" value="${currentUser.discount}">
    <div class="container">
        <div class="row content-wrapper">
            <h2 class="hostel-title">
                <span><c:out value="${hostel.name}"/></span>
            </h2>
            <hr class="devider"/>
            <div class="col-md-12 padd-top-20">
                <div class="hostel col-md-6 service-image-left">
                    <img id="main-img" class="img-responsive"
                         src=""
                         alt=""/>
                </div>

                <div class="container service1-items col-sm-2 col-md-2 pull-left">
                    <a id="item-1" class="service1-item">
                        <img id="img0" class="img-responsive" src=""/>
                    </a>
                    <a id="item-2" class="service1-item">
                        <img id="img1" class="img-responsive" src=""
                             alt=""/>
                    </a>
                    <a id="item-3" class="service1-item">
                        <img id="img2" class="img-responsive" src=""
                             alt=""/>
                    </a>
                </div>


                <div class="col-md-4">
                    <div class="info-title">
                        <fmt:message key="page.hostel.contact"/>
                    </div>
                    <div>
                        <span class="fa fa-map-marker" aria-hidden="true"></span>
                        <span> <c:out value="${hostel.city}"/>, </span>
                        <span><c:out value="${hostel.address}"/> </span>
                    </div>
                    <div>
                        <span class="fa fa-phone" aria-hidden="true"></span>
                        <span> <c:out value="${hostel.phone}"/></span>
                    </div>
                    <hr>
                    <div class="info-title">
                        <span><fmt:message key="page.hostel.places"/> <c:out value="${hostel.freePlaces}"/></span>
                    </div>
                    <hr>
                    <div class="info-title"><fmt:message key="page.hostel.price"/></div>
                    <div class="hostel-price">$<c:out value="${hostel.price}"/></div>
                    <hr>
                    <c:choose>
                        <c:when test="${not empty currentUser and not currentUser.banned}">
                            <c:choose>
                                <c:when test="${currentUser.money < current.price}">
                                    <div><fmt:message key="page.hostel.status.money"/></div>
                                </c:when>

                                <c:otherwise>
                                    <div id="book-status-booked" class="hidden">
                                        <div><fmt:message key="page.hostel.status.booked"/></div>
                                        <a href="${pageContext.request.contextPath}/service?command=book_cancel&hostelId=${hostel.hostelId}&userId=${currentUser.userId}"
                                           class="btn btn-danger" role="button">
                                            <fmt:message key="page.hostel.button.cancel"/>
                                        </a>
                                    </div>
                                    <div id="book-status-free" class="hidden">
                                        <a href="#bookModal" role="button" data-toggle="modal" class="btn btn-success">
                                            <fmt:message key="page.hostel.button.book"/>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${not empty currentUser and currentUser.banned}">
                            <div><fmt:message key="page.hostel.status.banned"/></div>
                        </c:when>
                        <c:otherwise>
                            <div><fmt:message key="page.hostel.status.login"/></div>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-12 hostel-info">
                        <ul id="myTab" class="nav nav-tabs nav_tabs">

                            <li class="active"><a href="#service-one" data-toggle="tab"><fmt:message
                                    key="page.hostel.description"/></a></li>
                            <c:if test="${currentUser.admin}">
                                <li><a href="#edit-hostel" data-toggle="tab"><fmt:message key="page.hostel.edit"/></a>
                                </li>
                            </c:if>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                            <div class="tab-pane fade in active" id="service-one">

                                <div class="container hostel-info">
                                    <c:out value="${hostel.description}"/>
                                </div>

                            </div>
                            <c:if test="${currentUser.admin}">
                                <div class="tab-pane fade" id="edit-hostel">
                                    <form id="edit-hostel-form" role="form" class="form-horizontal my-form"
                                          action="${pageContext.request.contextPath}/service" method="post"
                                          data-parsley-validate>
                                        <div class="form-group">
                                            <label for="hostelName" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hostelname"/></label>
                                            <div class="col-sm-6">
                                                <input class="form-control" name="hostelName" id="hostelName"
                                                       type="text"
                                                       value="${current.name}"
                                                       data-parsley-required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hostelCity" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hostelcity"/></label>
                                            <div class="col-sm-6">
                                                <select id="hostelCity" class="form-control" name="hostelCity"
                                                        required="true">
                                                    <option value="Minsk"><fmt:message key="page.form.minsk"/></option>
                                                    <option value="Grodno"><fmt:message
                                                            key="page.form.grodno"/></option>
                                                    <option value="Gomel"><fmt:message key="page.form.gomel"/></option>
                                                    <option value="Mogilev"><fmt:message
                                                            key="page.form.mogilev"/></option>
                                                    <option value="Vitebsk"><fmt:message
                                                            key="page.form.vitebsk"/></option>
                                                    <option value="Brest"><fmt:message key="page.form.brest"/></option>
                                                    <option value="${current.city}" hidden
                                                            selected>${current.city}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hostelAddress" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hosteladdress"/></label>
                                            <div class="col-sm-6">
                                                <input class="form-control" name="hostelAddress" id="hostelAddress"
                                                       type="text"
                                                       value="${current.address}"
                                                       data-parsley-required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hostelPhone" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hostelphone"/></label>
                                            <div class="col-sm-6">
                                                <input id="hostelPhone" class="form-control" name="hostelPhone"
                                                       type="name"
                                                       value="${current.phone}"
                                                       data-parsley-required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hostelPrice" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hostelprice"/></label>
                                            <div class="col-sm-6">
                                                <input class="form-control" name="hostelPrice" id="hostelPrice"
                                                       type="text"
                                                       value="${current.price}"
                                                       data-parsley-required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hostelPlaces" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hostelplaces"/></label>
                                            <div class="col-sm-6">
                                                <input class="form-control" name="hostelPlaces" id="hostelPlaces"
                                                       type="text"
                                                       value="${current.freePlaces}"
                                                       data-parsley-required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="hostelDesc" class="control-label col-sm-4"><fmt:message
                                                    key="page.form.hosteldesc"/></label>
                                            <div class="col-sm-6">
                                                <textarea rows="7" class="form-control" name="hostelDesc"
                                                          id="hostelDesc"
                                                          data-parsley-required>${current.description}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-offset-6 inline-disp">
                                                <input class="form-control btn btn-success" type="submit"
                                                       id="register_button"
                                                       value="<fmt:message key="page.form.savechanges"/>"/>
                                            </div>
                                        </div>
                                        <input type="hidden" name="hostelId" value="${current.hostelId}">
                                        <input type="hidden" name="command" value="edit_hostel">
                                    </form>
                                </div>
                            </c:if>
                        </div>
                        <hr>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="common/footer.jsp"/>

</body>
</html>
