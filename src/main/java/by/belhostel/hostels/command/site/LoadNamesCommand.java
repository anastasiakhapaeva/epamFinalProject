package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import com.google.gson.Gson;
import by.belhostel.hostels.entity.UserProfile;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.logic.Authorization;
import by.belhostel.hostels.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 07.01.2017.
 */
public class LoadNamesCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
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
            UserProfile userProfile = Authorization.findUserProfileById(userId);
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
