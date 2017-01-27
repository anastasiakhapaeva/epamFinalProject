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
<div id="aboutModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title"><fmt:message key="aboutmodal.title"/></h4>
            </div>
            <div class="modal-body">
                <div>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla tincidunt vel purus vel ultricies.
                    Mauris rutrum, mauris nec accumsan luctus, nunc libero porta enim, et sodales quam ligula nec mi.
                    Sed eget purus tristique, semper lorem ut, ultrices tellus. Vivamus ornare fermentum urna a
                    faucibus. Curabitur porta dignissim placerat. Donec imperdiet velit eget convallis laoreet. Nulla
                    consectetur felis vitae sem vulputate porttitor. Proin vitae est lacus.

                    Cras ut lobortis augue. Mauris faucibus quam risus. Integer nec tellus at massa tincidunt blandit.
                    Nulla tempus dui a feugiat feugiat. Morbi lobortis sapien at massa vulputate, id laoreet risus
                    laoreet. Vestibulum vestibulum ullamcorper ipsum vitae ornare. Donec ex tellus, iaculis vel nunc
                    nec, dapibus eleifend magna. Donec quam lectus, placerat sit amet placerat ac, interdum venenatis
                    velit. Curabitur neque lectus, finibus vitae tempor in, ultrices sed libero.

                    Proin convallis eu ligula vel condimentum. Etiam elementum sapien quam. Mauris lacus purus, maximus
                    sit amet pellentesque ac, consequat vel velit. Aliquam rutrum tempor purus sed ullamcorper.
                    Curabitur in vestibulum velit. Morbi et odio mauris. Vivamus pretium faucibus odio in accumsan.
                    Nulla at quam ac tortor tempor hendrerit. In sit amet lacus id turpis bibendum tincidunt vitae vitae
                    diam. Quisque rutrum, dui nec mollis fringilla, lectus tortor volutpat libero, a accumsan mauris
                    ante vitae sapien. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac
                    turpis egestas. Sed bibendum quam pulvinar interdum tempus. Nunc elementum ullamcorper mattis. Cum
                    sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque
                    viverra sed ipsum non venenatis. Aenean ac lobortis tortor.

                    Integer placerat sit amet neque eu pellentesque. Praesent malesuada mauris non ligula semper
                    rhoncus. Suspendisse potenti. Aliquam euismod sollicitudin augue. Nam id aliquet est. Fusce at
                    luctus urna. Aenean lacinia velit mi, eu dictum augue tristique vel. Curabitur mauris risus,
                    vestibulum vitae magna feugiat, suscipit semper sem. Sed consequat ex in facilisis bibendum. Etiam
                    aliquet molestie lorem at varius.
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                        key="modal.button.close"/></button>
            </div>
        </div>
    </div>
</div>

