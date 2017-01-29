package edu.training.web.command.site;

import com.google.gson.Gson;
import edu.training.web.command.ActionCommand;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.Authorization;
import edu.training.web.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 09.01.2017.
 */
public class LoadUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
            User user = HostelManager.findUserById(userId);
            UserProfile userProfile = Authorization.findUserProfileById(userId);
            user.setProfile(userProfile);
            response.getWriter().write(new Gson().toJson(user));
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
