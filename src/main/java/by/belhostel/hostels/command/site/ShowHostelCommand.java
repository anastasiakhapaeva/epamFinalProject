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
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_HOSTEL = "hostel";
    private static final String PARAM_HOSTEL_ID = "id";
    private static final String PARAM_HOSTEL_PAGE = "/resources/jsp/hostel.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

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
