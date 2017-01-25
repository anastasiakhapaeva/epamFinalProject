package edu.training.web.command.admin;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Message;
import edu.training.web.entity.User;
import edu.training.web.exception.LogicException;
import edu.training.web.listener.UserSessions;
import edu.training.web.logic.AdminAction;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 10.01.2017.
 */
public class AdminBanUserCommand implements ActionCommand {
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
            User user = HostelManager.findUserById(userId);
            List<HttpSession> userSessions = UserSessions.getUserSessions(user.getUserId());
            boolean isBanned = AdminAction.banUser(userId, !user.isBanned());
            if (isBanned) {
                Message banMessage = new Message(user.getUserId(), "Administration", "You are banned for violating the rules of the system!");
                boolean isSent = HostelManager.sendMessageToUser(banMessage);
                for (HttpSession userSession : userSessions) {
                    if (isSent) {
                        User currentUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                        ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                        messages.add(banMessage);
                        currentUser.setBanned(isBanned);
                    }
                }
            } else {
                Message unbanMessage = new Message(user.getUserId(), "Administration", "You was unbanned!");
                boolean isSent = HostelManager.sendMessageToUser(unbanMessage);
                for (HttpSession userSession : userSessions) {
                    if (isSent) {
                        User currentUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                        ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                        messages.add(unbanMessage);
                        currentUser.setBanned(isBanned);
                    }
                }
            }
            response.getWriter().write(Boolean.toString(isBanned));
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
