<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.msg"/>
<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 09.01.2017
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="userModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title"><fmt:message key="page.profile.title"/></h4>
            </div>
            <div class="modal-body">
                <div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 id="user-name" class="panel-title"></h3>
                        </div>
                        <div class="panel-body">

                            <input id="user-id-profile" type="hidden" value="">
                            <div class="col-md-3 col-lg-3 " align="center"><img alt="User Pic"
                                                                                src="http://image.flaticon.com/icons/svg/149/149071.svg"
                                                                                class="img-circle img-responsive">
                            </div>
                            <div class=" col-md-9 col-lg-9 ">
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <td><fmt:message key="usermodal.role"/></td>
                                        <td id="user-role"></td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="page.profile.city"/></td>
                                        <td id="user-city"></td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="page.profile.phone"/></td>
                                        <td id="user-phone"></td>
                                    </tr>
                                    <tr>
                                        <td>Email:</td>
                                        <td id="user-email"></td>
                                    </tr>

                                    <tr>
                                        <td><fmt:message key="usermodal.money"/></td>
                                        <td id="user-money-profile"></td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="usermodal.discount"/></td>
                                        <td>
                                            <span id="user-discount"></span>
                                            <div class="input-group">
                                                <input id="set-user-discount" class="form-control button" type="hidden"
                                                       value="" aria-describedby="ok-addon"
                                                       data-parsley-pattern="^[0-9]{1,2}\.[0-9]{2}$">
                                                <span id="ok-addon" class="hidden">
                                                                <span class="fa fa-check" aria-hidden="true"></span>
                                                            </span>
                                            </div>
                                            <div id="user-discount-err" class="text-danger"></div>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td><fmt:message key="page.profile.ban"/>:</td>
                                        <td id="user-ban"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <div class="container">
                                <div>
                                    <a id="set-discount-user" href="" type="button"
                                       class="btn btn-sm btn-warning"><span
                                            class="glyphicon glyphicon-edit"></span><fmt:message
                                            key="usermodal.button.discount"/></a>
                                    <a id="ban-user" href="" type="button" class="btn btn-sm btn-danger"><span
                                            class="glyphicon glyphicon-remove"></span><fmt:message
                                            key="usermodal.button.ban"/></a>
                                    <div class="inline-disp pull-right">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">
                                            <fmt:message key="modal.button.close"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

