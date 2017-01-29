<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 14.01.2017
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="messages" value="${messages}"/>
<div id="messageModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title"><fmt:message key="menu.user.messages"/></h4>
            </div>
            <div class="modal-body">
                <div>
                    <c:forEach var="elem" items="${messages}">
                        <div class="alert alert-info fade in">
                            <button name="closeButton" data-message-id="${elem.messageId}" type="button"
                                    class="close" data-dismiss="alert" aria-hidden="true">×
                            </button>
                            <h4><c:out value="${elem.sender}"/></h4>
                            <p><c:out value="${elem.text}"/></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                        key="modal.button.close"/></button>
            </div>
        </div>
    </div>
</div>

