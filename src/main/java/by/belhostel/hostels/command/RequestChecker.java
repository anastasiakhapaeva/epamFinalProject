package by.belhostel.hostels.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Roman on 28.12.2016.
 */
public class RequestChecker {

    /** The Constant PARAM_HEADER. */
    private static final String PARAM_HEADER = "X-Requested-With";

    /** The Constant XML_HTTP_REQUEST. */
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

    /**
     * Checks if is ajax request.
     *
     * @param request the request
     * @return true, if is ajax request
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        return XML_HTTP_REQUEST.equals(request.getHeader(PARAM_HEADER));
    }
}
