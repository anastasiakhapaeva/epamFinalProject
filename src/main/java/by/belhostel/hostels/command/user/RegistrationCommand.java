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
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_NAME_LOGIN = "userLogin";
    private static final String PARAM_NAME_PASSWORD = "userPass";
    private static final String PARAM_NAME_EMAIL = "userEmail";
    private static final String PARAM_NAME_FIRSTNAME = "userFirstName";
    private static final String PARAM_NAME_LASTNAME = "userLastName";
    private static final String PARAM_NAME_CITY = "userCity";
    private static final String PARAM_NAME_PHONE = "userPhone";
    private static final String PARAM_INDEX = "/index.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

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
