package by.belhostel.hostels.command.user;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.ClaimType;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.exception.NoSuchTypeException;
import by.belhostel.hostels.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 27.01.2017.
 */
public class ShowMyHostelsCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_BOOK_TYPE = "bookType";
    private static final String PARAM_MY_HOSTELS = "myHostels";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    private static final String MY_HOSTELS_PAGE = "/resources/jsp/bookedhostels.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = MY_HOSTELS_PAGE;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        String bookType = request.getParameter(PARAM_BOOK_TYPE);
        ArrayList<Hostel> myHostels;
        try {
            ClaimType type = ClaimType.valueOf(bookType.toUpperCase());
            switch (type) {
                case RESERVATION:
                    myHostels = HostelManager.findBookedHostelsForUser(currentUser.getUserId());
                    break;
                case PAYMENT:
                    myHostels = HostelManager.findPaidHostelsForUser(currentUser.getUserId());
                    break;
                default:
                    throw new NoSuchTypeException("No such constant in ClaimType enum");
            }
            request.setAttribute(PARAM_MY_HOSTELS, myHostels);
            request.setAttribute(PARAM_BOOK_TYPE, bookType);
        } catch (NoSuchTypeException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
