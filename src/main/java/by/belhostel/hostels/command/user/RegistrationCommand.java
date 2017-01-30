package by.belhostel.hostels.command.user;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.entity.UserProfile;
import by.belhostel.hostels.logic.Registration;
import by.belhostel.hostels.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Roman on 08.12.2016.
 */
public class RegistrationCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_NAME_LOGIN. */
    private static final String PARAM_NAME_LOGIN = "userLogin";

    /** The Constant PARAM_NAME_PASSWORD. */
    private static final String PARAM_NAME_PASSWORD = "userPass";

    /** The Constant PARAM_NAME_EMAIL. */
    private static final String PARAM_NAME_EMAIL = "userEmail";

    /** The Constant PARAM_NAME_FIRSTNAME. */
    private static final String PARAM_NAME_FIRSTNAME = "userFirstName";

    /** The Constant PARAM_NAME_LASTNAME. */
    private static final String PARAM_NAME_LASTNAME = "userLastName";

    /** The Constant PARAM_NAME_CITY. */
    private static final String PARAM_NAME_CITY = "userCity";

    /** The Constant PARAM_NAME_PHONE. */
    private static final String PARAM_NAME_PHONE = "userPhone";

    /** The Constant PARAM_INDEX. */
    private static final String PARAM_INDEX = "/index.jsp";

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
        String page = PARAM_INDEX;

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String firstName = request.getParameter(PARAM_NAME_FIRSTNAME);
        String lastName = request.getParameter(PARAM_NAME_LASTNAME);
        String city = request.getParameter(PARAM_NAME_CITY);
        String phone = request.getParameter(PARAM_NAME_PHONE);

        User newUser = new User(login, pass);
        UserProfile newUserProfile = new UserProfile(firstName, lastName, email, phone, city);
        newUser.setProfile(newUserProfile);
        try {
            Registration.register(newUser);
        } catch (LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
