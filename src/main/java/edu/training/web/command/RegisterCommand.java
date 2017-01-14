package edu.training.web.command;

import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
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
    private static final String PARAM_PAGE = "page";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
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

        Registration.register(newUser, newUserProfile);
        Registration.createUserProfile(newUserProfile);
//        if (Registration.register(newUser, newUserProfile) && Registration.createUserProfile(newUserProfile)) {
//            page = (String) session.getAttribute(PARAM_PAGE);
//        }
//        if(page == null){
//            page = PARAM_INDEX;
//        }
        return PARAM_INDEX;
    }
}
