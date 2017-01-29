package by.belhostel.hostels.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Roman on 28.12.2016.
 */
public class RequestChecker {
    private static final String PARAM_HEADER = "X-Requested-With";
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    public static boolean isAjaxRequest(HttpServletRequest request){
        return XML_HTTP_REQUEST.equals(request.getHeader(PARAM_HEADER));
    }
}
