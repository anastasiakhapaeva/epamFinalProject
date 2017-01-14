package edu.training.web.command;

import edu.training.web.logic.Authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 25.12.2016.
 */
public class AjaxAuthorizationCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "pass";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String login = request.getParameter(PARAM_NAME_LOGIN);
            String password = request.getParameter(PARAM_NAME_PASSWORD);
            response.getWriter().write(Boolean.toString(Authorization.login(login, password)));
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}
