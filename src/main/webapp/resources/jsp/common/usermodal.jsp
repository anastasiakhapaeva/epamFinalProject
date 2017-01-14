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
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Профиль пользователя.</h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                <div class="container">
                    <div class="row">
                        <div class="container">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h3 id="user-name" class="panel-title"></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <input id="user-id-profile" type="hidden" value="">
                                        <div class="col-md-3 col-lg-3 " align="center"><img alt="User Pic"
                                                                                            src="http://image.flaticon.com/icons/svg/149/149071.svg"
                                                                                            class="img-circle img-responsive">
                                        </div>
                                        <div class=" col-md-9 col-lg-9 ">
                                            <table class="table">
                                                <tbody>
                                                <tr>
                                                    <td>Role:</td>
                                                    <td id="user-role"></td>
                                                </tr>
                                                <tr>
                                                    <td>City:</td>
                                                    <td id="user-city"></td>
                                                </tr>
                                                <tr>
                                                    <td>Phone number:</td>
                                                    <td id="user-phone"></td>
                                                </tr>
                                                <tr>
                                                    <td>Email:</td>
                                                    <td id="user-email"></td>
                                                </tr>

                                                <tr>
                                                    <td>Money, $:</td>
                                                    <td id="user-money-profile"></td>
                                                </tr>
                                                <tr>
                                                    <td>Discount, %:</td>
                                                    <td>
                                                        <span id="user-discount"></span>
                                                        <div class="input-group">
                                                            <input id="set-user-discount"  class="form-control button" type="hidden" value="" aria-describedby="ok-addon"
                                                                   data-parsley-pattern="^[0-9]{1,2}\.[0-9]{2}$">
                                                            <span id="ok-addon" class="hidden">
                                                                <span class="fa fa-check" aria-hidden="true"></span>
                                                            </span>
                                                        </div>
                                                        <div id="user-discount-err" class="text-danger"></div>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Ban:</td>
                                                    <td id="user-ban"></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-footer">
                                    <div class="container">
                                        <div>
                                            <a id="set-discount-user" href="#" type="button"
                                               class="btn btn-sm btn-warning"><span
                                                    class="glyphicon glyphicon-edit"></span>Set discount</a>
                                            <a id="ban-user" href="#" type="button" class="btn btn-sm btn-danger"><span
                                                    class="glyphicon glyphicon-remove"></span>Ban/Unban</a>
                                            <div class="inline-disp pull-right">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                                    Close
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
</div>
</div>

