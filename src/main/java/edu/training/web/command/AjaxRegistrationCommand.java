package edu.training.web.command;

import edu.training.web.logic.Registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 25.12.2016.
 */
public class AjaxRegistrationCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "userLogin";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String login = request.getParameter(PARAM_NAME_LOGIN);
            response.getWriter().write(Boolean.toString(Registration.checkLoginExistance(login)));
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}
