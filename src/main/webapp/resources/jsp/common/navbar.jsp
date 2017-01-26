<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 26.12.2016
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<c:set var="user" value="${currentUser}"/>
<c:set var="profile" value="${userProfile}"/>
<c:set var="hostels" value="${hostels}" scope="request"/>
<c:set var="bookedHostels" value="${bookedHostels}"/>
<c:set var="paidHostels" value="${paidHostels}"/>
<c:set var="messages" value="${messages}"/>
<nav id="nav-bar" class="navbar navbar-inverse navbar-fixed-top menu" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#menu">
                <span class="sr-only">Открыть навигацию</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand active" href="${pageContext.request.contextPath}/index.jsp">BELHOSTEL</a>
        </div>
        <div class="collapse navbar-collapse" id="menu">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/service?command=go&page=main">
                        <span class="fa fa-home" aria-hidden="true"></span>
                        <span><fmt:message key="menu.main"/></span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/service?command=find_hostels&type=all">
                        <span class="fa fa-bed" aria-hidden="true"></span>
                        <span><fmt:message key="menu.hostels"/></span>
                    </a>
                    <%--<c:choose>--%>
                        <%--<c:when test="${not empty hostels}">--%>
                            <%--<a href="${pageContext.request.contextPath}/service?command=go&page=catalog">--%>
                                <%--<span class="fa fa-bed" aria-hidden="true"></span>--%>
                                <%--<span><fmt:message key="menu.hostels"/></span>--%>
                            <%--</a>--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                            <%--<a href="${pageContext.request.contextPath}/service?command=find_hostels&type=all">--%>
                                <%--<span class="fa fa-bed" aria-hidden="true"></span>--%>
                                <%--<span><fmt:message key="menu.hostels"/></span>--%>
                            <%--</a>--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                </li>
                <c:if test="${user.admin}">
                    <c:set var="unconfirmedClaims" value="${unconfirmedClaims}" scope="session"/>
                    <li>
                        <a href="${pageContext.request.contextPath}/service?command=go&page=claims">
                            <span class="fa fa-envelope-open" aria-hidden="true"></span>
                            <span><fmt:message key="menu.claims"/> <span class="badge">${fn:length(unconfirmedClaims)}</span></span>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="#userModal" role="button" data-toggle="modal">
                        <span class="fa fa-info" aria-hidden="true"></span>
                        <span><fmt:message key="menu.aboutus"/></span>
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a role="button" id="ru" data-locale="ru_RU">
                        <span id="flag_ru"></span>
                        <span>Rus</span>
                    </a>
                </li>
                <li>
                    <a role="button" id="eng" data-locale="en_US">
                        <span id="flag_en"></span>
                        <span>Eng</span>
                    </a>
                </li>
                </li>
                <input id="locale" type="hidden" value="${locale}">
                <li class="li-height">
                    <c:choose>
                    <c:when test="${not empty user}">
                <li>
                    <a>
                        <span class="fa fa-usd" aria-hidden="true"></span>
                        <input id="curr-money" type="hidden" value="${user.money}">
                        <span id="user-money">${user.money}</span>
                    </a>
                </li>
                <li class="dropdown">
                    <a id="drop1" role="button" class="dropdown-toggle" data-toggle="dropdown">
                        <c:choose>
                            <c:when test="${user.banned}">
                            <span class="fa fa-ban" aria-hidden="true"></span>
                        </c:when>
                            <c:otherwise>
                                <span class="fa fa-user" aria-hidden="true"></span>
                            </c:otherwise>
                        </c:choose>
                        <span>${profile.firstName}
                            <c:if test="${user.banned}">
                                <span><fmt:message key="menu.user.banned"/></span>
                            </c:if>
                        </span>
                        <c:if test="${not empty messages}">
                            <span class="badge">${fn:length(messages)}</span>
                        </c:if>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
                        <li><a tabindex="-1"
                               href="${pageContext.request.contextPath}/service?command=go&page=profile"><fmt:message key="menu.user.profile"/></a>
                        </li>
                        <c:if test="${not empty messages}">
                            <li><a tabindex="-1" href="#messageModal" role="button" data-toggle="modal"><fmt:message key="menu.user.messages"/> <span class="badge">${fn:length(messages)}</span></a>
                            </li>

                        </c:if>
                        <li><a tabindex="-1" href="#depositModal" role="button" data-toggle="modal"><fmt:message key="menu.user.balance"/></a>
                        </li>
                        <c:if test="${not empty paidHostels}">
                        <li><a tabindex="-1"
                               href="${pageContext.request.contextPath}/service?command=go&page=profile"><fmt:message key="menu.user.paid"/></a></li>
                            </c:if>
                            <c:if test="${not empty bookedHostels}">
                        <li><a tabindex="-1"
                               href="${pageContext.request.contextPath}/service?command=go&page=profile"><fmt:message key="menu.user.booked"/></a></li>
                        <li>
                            </c:if>
                        <li class="divider"></li>
                        <li><a tabindex="-1" href="${pageContext.request.contextPath}/service?command=logout"><fmt:message key="menu.user.logout"/></a>
                        </li>
                    </ul>
                </li>
                </c:when>
                <c:otherwise>
                    <a href="#myModal" role="button" data-toggle="modal" class="login-btn">
                        <span><fmt:message key="menu.signin"/></span>
                        <span class="glyphicon glyphicon-log-in no-padd"></span>
                    </a>
                </c:otherwise>
                </c:choose>
                </li>
            </ul>
        </div>
    </div>
</nav>
