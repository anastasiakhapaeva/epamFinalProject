package edu.training.web.command.admin;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Claim;
import edu.training.web.entity.Message;
import edu.training.web.entity.User;
import edu.training.web.exception.LogicException;
import edu.training.web.listener.UserSessions;
import edu.training.web.logic.AdminAction;
import edu.training.web.logic.HostelManager;
import edu.training.web.logic.Messenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Roman on 10.01.2017.
 */
public class BanUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
            User user = HostelManager.findUserById(userId);
            List<HttpSession> userSessions = UserSessions.getUserSessions(user.getUserId());
            boolean isBanned = AdminAction.banUserById(userId, !user.isBanned());
            if (isBanned) {
                Message banMessage = Messenger.generateBanMessage(userId);
                List<HttpSession> adminSessions = UserSessions.getAdminsSessions();
                for (HttpSession adminSession : adminSessions) {
                    List<Claim> unconfirmedClaims = (ArrayList<Claim>) adminSession.getAttribute(PARAM_UNCONFIRMED_CLAIMS);
                    unconfirmedClaims = unconfirmedClaims.stream().filter(claim -> claim.getUserId() != userId).collect(Collectors.toList());
                    adminSession.setAttribute(PARAM_UNCONFIRMED_CLAIMS, unconfirmedClaims);
                }
                boolean isSent = HostelManager.sendMessageToUser(banMessage);
                for (HttpSession userSession : userSessions) {
                    User currentUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                    currentUser.setBanned(true);
                    if (isSent) {
                        ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                        messages.add(banMessage);
                    }
                }
            } else {
                Message unbanMessage = Messenger.generateUnbanMessage(userId);
                boolean isSent = HostelManager.sendMessageToUser(unbanMessage);
                for (HttpSession userSession : userSessions) {
                    User currentUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                    currentUser.setBanned(false);
                    if (isSent) {
                        ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                        messages.add(unbanMessage);
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
