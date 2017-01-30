package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Roman on 04.01.2017.
 */
public class ForwardCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_PAGE. */
    private static final String PARAM_PAGE = "page";

    /** The Constant PATH_BEG. */
    private static final String PATH_BEG = "/resources/jsp/";

    /** The Constant PATH_END. */
    private static final String PATH_END = ".jsp";

    /** The Constant PARAM_PAGINATION_PAGE. */
    private static final String PARAM_PAGINATION_PAGE = "currentPage";

    /** The Constant PARAM_PAGINATION_PAGES. */
    private static final String PARAM_PAGINATION_PAGES = "noOfPages";

    /** The Constant PARAM_PAGINATION_PERPAGE. */
    private static final String PARAM_PAGINATION_PERPAGE = "perPage";

    /** The Constant PARAM_TYPE. */
    private static final String PARAM_TYPE = "type";

    /** The Constant PARAM_CITY. */
    private static final String PARAM_CITY = "city";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String currentPage = request.getParameter(PARAM_PAGINATION_PAGE);
        String numOfPages = request.getParameter(PARAM_PAGINATION_PAGES);
        String perPage = request.getParameter(PARAM_PAGINATION_PERPAGE);
        String type = request.getParameter(PARAM_TYPE);
        String city = request.getParameter(PARAM_CITY);
        if (currentPage != null && numOfPages != null && perPage != null) {
            request.setAttribute(PARAM_PAGINATION_PAGE, Integer.parseInt(currentPage));
            request.setAttribute(PARAM_PAGINATION_PAGES, Integer.parseInt(numOfPages));
            request.setAttribute(PARAM_PAGINATION_PERPAGE, Integer.parseInt(perPage));
            request.setAttribute(PARAM_TYPE, type);
            request.setAttribute(PARAM_CITY, city);
        }
        return PATH_BEG + request.getParameter(PARAM_PAGE) + PATH_END;
    }
}
