package by.belhostel.hostels.command.admin;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.logic.AdminAction;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.listener.UserSessions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Roman on 11.01.2017.
 */
public class EditHostelCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /** The Constant PARAM_HOSTEL_ID. */
    private static final String PARAM_HOSTEL_ID = "hostelId";

    /** The Constant PARAM_HOSTEL_NAME. */
    private static final String PARAM_HOSTEL_NAME = "hostelName";

    /** The Constant PARAM_HOSTEL_CITY. */
    private static final String PARAM_HOSTEL_CITY = "hostelCity";

    /** The Constant PARAM_HOSTEL_ADDRESS. */
    private static final String PARAM_HOSTEL_ADDRESS = "hostelAddress";

    /** The Constant PARAM_HOSTEL_PHONE. */
    private static final String PARAM_HOSTEL_PHONE = "hostelPhone";

    /** The Constant PARAM_HOSTEL_PRICE. */
    private static final String PARAM_HOSTEL_PRICE = "hostelPrice";

    /** The Constant PARAM_HOSTEL_PLACES. */
    private static final String PARAM_HOSTEL_PLACES = "hostelPlaces";

    /** The Constant PARAM_HOSTEL_DESC. */
    private static final String PARAM_HOSTEL_DESC = "hostelDesc";

    /** The Constant PARAM_HOSTELS. */
    private static final String PARAM_HOSTELS = "hostels";

    /** The Constant PARAM_HOSTEL. */
    private static final String PARAM_HOSTEL = "hostel";

    /** The Constant PARAM_HOSTEL_PAGE. */
    private static final String PARAM_HOSTEL_PAGE = "/service?command=go&page=hostel";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PARAM_ERROR. */
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    /** The Constant PARAM_MAIN. */
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if (currentUser != null && currentUser.isAdmin()) {
            Hostel currentHostel = (Hostel) session.getAttribute(PARAM_HOSTEL);
            String hostelName = request.getParameter(PARAM_HOSTEL_NAME);
            String hostelCity = request.getParameter(PARAM_HOSTEL_CITY);
            String hostelAddress = request.getParameter(PARAM_HOSTEL_ADDRESS);
            String hostelPhone = request.getParameter(PARAM_HOSTEL_PHONE);
            String hostelDesc = request.getParameter(PARAM_HOSTEL_DESC);
            try {
                double hostelPrice = Double.parseDouble(request.getParameter(PARAM_HOSTEL_PRICE));
                int hostelPlaces = Integer.parseInt(request.getParameter(PARAM_HOSTEL_PLACES));
                int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
                Hostel editedHostel = new Hostel(hostelId, hostelName, hostelPlaces, hostelPrice, hostelPhone, hostelCity, hostelDesc, hostelAddress);
                boolean res = AdminAction.editHostel(editedHostel);
                if (res) {
                    for (HttpSession userSession : UserSessions.getAllSessions()) {
                        ArrayList<Hostel> hostels = ((ArrayList<Hostel>) userSession.getAttribute(PARAM_HOSTELS));
                        if (hostels != null && hostels.contains(currentHostel)) {
                            Collections.replaceAll(hostels, currentHostel, editedHostel);
                        }
                        Hostel currentUserHostel = (Hostel) userSession.getAttribute(PARAM_HOSTEL);
                        if (currentUserHostel != null && currentHostel.equals(currentUserHostel)) {
                            userSession.setAttribute(PARAM_HOSTEL, editedHostel);
                        }
                    }
                }

                response.sendRedirect(request.getContextPath() + PARAM_HOSTEL_PAGE);
            } catch (IOException | NumberFormatException | LogicException e) {
                LOG.error(e);
                request.setAttribute(PARAM_ERROR_MESSAGE, e);
                page = PARAM_ERROR;
            }
        } else {
            page = PARAM_MAIN;
        }
        return page;
    }
}
