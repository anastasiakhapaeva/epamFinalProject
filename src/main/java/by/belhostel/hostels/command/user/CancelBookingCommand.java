package by.belhostel.hostels.command.user;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.exception.LogicException;
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
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PARAM_MAIN;
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if(currentUser != null) {
            try {
                int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
                boolean res = HostelManager.cancelBookingByIds(currentUser.getUserId(), hostelId);
                if(res){
                    page = PARAM_HOSTEL_PAGE;
                }
            } catch (NumberFormatException | LogicException e) {
                LOG.error(e);
                request.setAttribute(PARAM_ERROR_MESSAGE, e);
                page = PARAM_ERROR;
            }
        }
        return page;
    }
}
