package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 27.01.2017.
 */
public class BookingStateCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "";
        try {
            int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            boolean isBooked = HostelManager.isBookedHostelByUser(userId, hostelId);
            response.getWriter().write(Boolean.toString(isBooked));
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
