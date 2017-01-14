<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Messages</h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                <div class="tab-content">
                    <div class="tab-pane active" id="inbox">

                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <%--<ul class="mail-list">--%>
                                        <c:forEach var="elem" items="${messages}">
                                            <div class="alert alert-info fade in">
                                                <button name="closeButton" data-message-id="${elem.messageId}" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                                <h4>${elem.sender}</h4>
                                                <p>${elem.text}</p>
                                            </div>
                                            </c:forEach>
                                        <%--<li>--%>
                                            <%--<div>--%>
                                                <%--<span class="mail-sender">You Tube</span>--%>
                                                <%--<span class="mail-subject">New subscribers!</span>--%>
                                                <%--<span class="mail-message-preview">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nihil eveniet ipsum nisi? Eaque odio quae debitis saepe explicabo alias sit tenetur animi...</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<div>--%>
                                                <%--<span class="mail-sender">You Tube</span>--%>
                                                <%--<span class="mail-subject">New subscribers!</span>--%>
                                                <%--<span class="mail-message-preview">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nihil eveniet ipsum nisi? Eaque odio quae debitis saepe explicabo alias sit tenetur animi...</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<div>--%>
                                                <%--<span class="mail-sender">You Tube</span>--%>
                                                <%--<span class="mail-subject">New subscribers!</span>--%>
                                                <%--<span class="mail-message-preview">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nihil eveniet ipsum nisi? Eaque odio quae debitis saepe explicabo alias sit tenetur animi...</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<div>--%>
                                                <%--<span class="mail-sender">You Tube</span>--%>
                                                <%--<span class="mail-subject">New subscribers!</span>--%>
                                                <%--<span class="mail-message-preview">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nihil eveniet ipsum nisi? Eaque odio quae debitis saepe explicabo alias sit tenetur animi...</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

