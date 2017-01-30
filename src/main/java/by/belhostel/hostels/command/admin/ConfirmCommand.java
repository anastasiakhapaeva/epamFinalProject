package by.belhostel.hostels.command.admin;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.logic.AdminAction;
import by.belhostel.hostels.entity.Claim;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.entity.Message;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.listener.UserSessions;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.logic.Messenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Roman on 07.01.2017.
 */
public class ConfirmCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_CLAIM_ID = "claimId";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_CLAIMS_PAGE = "/service?command=go&page=claims";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if (currentUser != null && currentUser.isAdmin()) {
            try {
                int claimId = Integer.parseInt(request.getParameter(PARAM_CLAIM_ID));
                Claim claim = HostelManager.findClaimById(claimId);
                User user = HostelManager.findUserById(claim.getUserId());
                Hostel current = HostelManager.findHostelById(claim.getHostelId());

                boolean res = AdminAction.confirmClaimById(claimId);
                if (res) {
                    ArrayList<Claim> unconfirmedClaims = (ArrayList<Claim>) session.getAttribute(PARAM_UNCONFIRMED_CLAIMS);
                    unconfirmedClaims.remove(claim);
                    List<HttpSession> userSessions = UserSessions.getUserSessions(user.getUserId());
                    long differenceInDays = DAYS.between(claim.getDateIn(), claim.getDateOut());
                    boolean isPaid = HostelManager.payForHostel(user.getUserId(), current.getPrice() * claim.getRequiredPlaces() * differenceInDays * (1 - user.getDiscount() / 100));
                    if (isPaid) {
                        Message approvalMessage = Messenger.generateApprovalMessage(user.getUserId(), current.getName(), claim);
                        boolean isSent = HostelManager.sendMessageToUser(approvalMessage);
                        for (HttpSession userSession : userSessions) {
                            User sessionUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                            sessionUser.setMoney(sessionUser.getMoney() - current.getPrice() * claim.getRequiredPlaces() * differenceInDays * (1 - sessionUser.getDiscount() / 100));
                            if (isSent) {
                                ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                                messages.add(approvalMessage);
                            }
                        }
                    }
                }
                response.sendRedirect(request.getContextPath() + PARAM_CLAIMS_PAGE);
            } catch (IOException | NumberFormatException | LogicException e) {
                LOG.error(e);
                request.setAttribute(PARAM_ERROR_MESSAGE, e);
                page = PARAM_ERROR;
            }
        } else {
            page = PARAM_MAIN;
        }
        return page;
    }
}
