package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.Registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Roman on 08.12.2016.
 */
public class RegisterCommand implements ActionCommand {
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
        HttpSession session = request.getSession(true);

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String firstName = request.getParameter(PARAM_NAME_FIRSTNAME);
        String lastName = request.getParameter(PARAM_NAME_LASTNAME);
        String city = request.getParameter(PARAM_NAME_CITY);
        String phone = request.getParameter(PARAM_NAME_PHONE);

        User newUser = new User(login, pass);
        UserProfile newUserProfile = new UserProfile(firstName, lastName, email, phone, city);
        try {
            Registration.register(newUser, newUserProfile);
            Registration.createUserProfile(newUserProfile);
        }catch (LogicException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
