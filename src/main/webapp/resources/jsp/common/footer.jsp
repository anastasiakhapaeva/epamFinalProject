<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.text"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 26.12.2016
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="footer navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">BELHOSTEL</a>
        </div>
        <div class="collapse navbar-collapse" id="menu1">
            <span class="copyrights"><fmt:message key="footer.copyrights"/></span>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">
                        <span class="fa fa-google-plus-official" aria-hidden="true"></span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="fa fa-vk" aria-hidden="true"></span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="fa fa-facebook-official" aria-hidden="true"></span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</footer>
