package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Roman on 01.01.2017.
 */
public class ShowHostelCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_HOSTEL. */
    private static final String PARAM_HOSTEL = "hostel";

    /** The Constant PARAM_HOSTEL_ID. */
    private static final String PARAM_HOSTEL_ID = "id";

    /** The Constant PARAM_HOSTEL_PAGE. */
    private static final String PARAM_HOSTEL_PAGE = "/resources/jsp/hostel.jsp";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PARAM_ERROR. */
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PARAM_HOSTEL_PAGE;
        HttpSession session = request.getSession(true);
        try {
            int id = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            Hostel current = HostelManager.findHostelById(id);
            session.setAttribute(PARAM_HOSTEL, current);
        } catch (NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
