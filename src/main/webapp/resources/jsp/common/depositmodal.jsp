<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 27.12.2016
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="depositModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Пополнение баланса.</h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                    <form id="deposit-form" role="form" class="form-horizontal form" method="post"
                          action="${pageContext.request.contextPath}/service"
                          data-parsley-validate>
                        <div class="form-group">
                            <label for="card-num" class="control-label col-sm-4">Номер карты</label>
                            <div class="input-group col-sm-6">
                                <span class="input-group-addon">
                                    <span class="fa fa-credit-card" aria-hidden="true"></span>
                                </span>
                                <input id="card-num" type="text" class="form-control" name="card-num" required="true"
                                       placeholder="номер кредитной карты"
                                       data-parsley-errors-container="#err-card"
                                       data-parsley-required>
                            </div>
                            <div id="err-card" class="col-sm-offset-4"></div>
                        </div>
                        <div class="form-group">
                            <label for="amount-mon" class="control-label col-sm-4">Cумма</label>
                            <div class="input-group col-sm-6">
                            <span class="input-group-addon">
                                <span class="fa fa-usd" aria-hidden="true"></span>
                            </span>
                                <input id="amount-mon" type="text" class="form-control" name="amount-mon"
                                       required="true" placeholder="сумма в долларах"
                                       data-parsley-errors-container="#err-money"
                                       data-parsley-pattern="^[0-9]{1,3}\.[0-9]{2}$"
                                       data-parsley-pattern-message="Insert correct money amount(max 999.99$)"
                                       data-parsley-required>
                            </div>
                            <div id="err-money" class="col-sm-offset-4"></div>
                        </div>
                        <div id="err-deposit"></div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4 inline-disp">
                                <input class="form-control btn btn-success" type="submit" id="pay_button"
                                       value="Пополнить"/>
                            </div>
                            <div class="inline-disp">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<script src="<c:url value="/resources/js/parsley.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/pageupdate.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/validator.js"/>"></script>--%>
<%--<script src="<c:url value="/resources/js/ajaxrequests.js"/>"></script>--%>
</body>
</html>
