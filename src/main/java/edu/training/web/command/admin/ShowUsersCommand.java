package edu.training.web.command.admin;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.User;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.AdminAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 29.01.2017.
 */
public class ShowUsersCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_USERS = "users";
    private static final String PARAM_USERS_PAGE = "/resources/jsp/users.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = PARAM_USERS_PAGE;
        try {
            ArrayList<User> users = AdminAction.findAllUsers();
            request.setAttribute(PARAM_USERS, users);
        }catch (LogicException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
