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

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_BOOK_TYPE. */
    private static final String PARAM_BOOK_TYPE = "bookType";

    /** The Constant PARAM_MY_HOSTELS. */
    private static final String PARAM_MY_HOSTELS = "myHostels";

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PARAM_ERROR. */
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    /** The Constant MY_HOSTELS_PAGE. */
    private static final String MY_HOSTELS_PAGE = "/resources/jsp/bookedhostels.jsp";

    /** The Constant PARAM_MAIN. */
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = MY_HOSTELS_PAGE;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if(currentUser != null) {
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
        }else{
            page = PARAM_MAIN;
        }
        return page;
    }
}
