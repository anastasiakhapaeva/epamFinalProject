package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Claim;
import edu.training.web.entity.Message;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.Authorization;
import edu.training.web.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Roman on 26.12.2016.
 */
public class LoginCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";
    private static final String PARAM_INDEX = "/index.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PARAM_INDEX;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            User authorizedUser = Authorization.findCurrentUser(login, pass);
            UserProfile profile = Authorization.findUserProfileById(authorizedUser.getUserId());
            authorizedUser.setProfile(profile);
            ArrayList<Message> messages = Authorization.findMessagesForUser(authorizedUser.getUserId());

            HttpSession session = request.getSession(true);
            session.setAttribute(PARAM_CURRENT_USER, authorizedUser);
            session.setAttribute(PARAM_MESSAGES, messages);
            if (authorizedUser.isAdmin()) {
                ArrayList<Claim> unconfirmedClaims = HostelManager.findUnconfirmedClaims();
                session.setAttribute(PARAM_UNCONFIRMED_CLAIMS, unconfirmedClaims);
            }
        } catch (LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
