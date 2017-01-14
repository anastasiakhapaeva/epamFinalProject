<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 11.01.2017
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addHostelModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Addition of new hostel</h4>
            </div>
            <!-- Основное содержимое модального окна -->
            <div class="modal-body">
                <%--<div id="book-info" class="text-info marg-bott text-center">Вы оплачивает первые сутки проживания сразу на сайте, а--%>
                    <%--оставшуюся сумму доплачиваете непосредственно при заезде.--%>
                <%--</div>--%>
                <%--<div id="book-payment" class="text-danger marg-bott text-center">Отменить оплату нельзя!</div>--%>
                    <form id="add-hostel-form" role="form" class="form-horizontal my-form" enctype="multipart/form-data"
                          action="${pageContext.request.contextPath}/service" method="post" data-parsley-validate>
                        <div class="form-group">
                            <label for="newHostelName" class="control-label col-sm-4">Hostel name*</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelName" id="newHostelName" type="text"
                                       placeholder="hostels name"
                                       data-parsley-required>
                                <%--<div id="err-login"></div>--%>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelCity" class="control-label col-sm-4">Hostel city</label>
                            <div class="col-sm-6">
                                <select id="newHostelCity" class="form-control" name="newHostelCity" required="true">
                                    <option value="" disabled selected hidden>
                                        Выберите город
                                    </option>
                                    <option value="Minsk">Минск</option>
                                    <option value="Grodno">Гродно</option>
                                    <option value="Gomel">Гомель</option>
                                    <option value="Mogilev">Могилев</option>
                                    <option value="Vitebsk">Витебск</option>
                                    <option value="Brest">Брест</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelAddress" class="control-label col-sm-4">Hostel address*</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelAddress" id="newHostelAddress"
                                       type="text"
                                       placeholder="hostel address"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelPhone" class="control-label col-sm-4">Hostel phone*</label>
                            <div class="col-sm-6">
                                <input id="newHostelPhone" class="form-control" name="newHostelPhone" type="name"
                                       placeholder="hostel phone"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelPrice" class="control-label col-sm-4">Price per night*, $</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelPrice" id="newHostelPrice" type="text"
                                       placeholder="price per night"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelPlaces" class="control-label col-sm-4">Max amount of places*</label>
                            <div class="col-sm-6">
                                <input class="form-control" name="newHostelPlaces" id="newHostelPlaces" type="text"
                                       placeholder="places"
                                       data-parsley-required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newHostelDesc" class="control-label col-sm-4">Description*</label>
                            <div class="col-sm-6">
                                <textarea placeholder="description" rows="7" class="form-control" name="newHostelDesc" id="newHostelDesc" data-parsley-required>${current.description}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-4">Photos*</label>
                            <div class="col-sm-6">
                                <input type="file" class="form-control" name="newHostelPhoto1" id="newHostelPhoto1" data-parsley-required accept="image/jpeg">
                                <input type="file" class="form-control" name="newHostelPhoto2" id="newHostelPhoto2" data-parsley-required accept="image/jpeg">
                                <input type="file" class="form-control" name="newHostelPhoto3" id="newHostelPhoto3" data-parsley-required accept="image/jpeg">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-5 inline-disp">
                                <input class="form-control btn btn-success" type="submit"
                                       value="Add hostel"/>
                            </div>
                            <div class="inline-disp">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                        <input type="hidden" name="command" value="add_hostel">
                    </form>
            </div>
        </div>
    </div>
</div>


