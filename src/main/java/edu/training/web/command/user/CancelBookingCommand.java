package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.User;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Roman on 02.01.2017.
 */
public class CancelBookingCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_HOSTEL_PAGE = "/resources/jsp/hostel.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PARAM_HOSTEL_PAGE;
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        try {
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            HostelManager.cancelBookingByIds(currentUser.getUserId(), hostelId);
        }catch (NumberFormatException | LogicException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
