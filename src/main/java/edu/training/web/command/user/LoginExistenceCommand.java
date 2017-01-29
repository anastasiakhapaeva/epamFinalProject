package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.Registration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 25.12.2016.
 */
public class LoginExistenceCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_NAME_LOGIN = "userLogin";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            String login = request.getParameter(PARAM_NAME_LOGIN);
            response.getWriter().write(Boolean.toString(Registration.checkLoginExistence(login)));
        } catch (IOException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
