package edu.training.web.command.site;

import com.google.gson.Gson;
import edu.training.web.command.ActionCommand;
import edu.training.web.entity.UserProfile;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.Authorization;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 07.01.2017.
 */
public class AjaxLoadNamesCommand implements ActionCommand {
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            String[] names = new String[2];
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
            UserProfile userProfile = Authorization.getUserProfile(userId);
            names[0] = userProfile.getLastName() + " " + userProfile.getFirstName();
            names[1] = HostelManager.findHostelById(hostelId).getName();
            String json = new Gson().toJson(names);
            response.getWriter().write(json);
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
