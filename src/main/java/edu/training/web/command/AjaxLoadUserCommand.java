package edu.training.web.command;

import com.google.gson.Gson;
import edu.training.web.entity.Entity;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import edu.training.web.logic.Authorization;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 09.01.2017.
 */
public class AjaxLoadUserCommand implements ActionCommand {
    private static final String PARAM_USER_ID = "userId";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
        User user = HostelManager.findUserById(userId);
        UserProfile userProfile = Authorization.getUserProfile(userId);
        ArrayList<Entity> userInfo = new ArrayList<Entity>();
        userInfo.add(user);
        userInfo.add(userProfile);
        try{
            String s  = new Gson().toJson(userInfo);
            response.getWriter().write(s);
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}
