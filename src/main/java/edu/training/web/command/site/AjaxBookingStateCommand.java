package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 27.01.2017.
 */
public class AjaxBookingStateCommand implements ActionCommand {
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "";
        try{
            int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            boolean isBooked = HostelManager.isBookedHostelByUser(userId, hostelId);
            response.getWriter().write(Boolean.toString(isBooked));
        }catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
