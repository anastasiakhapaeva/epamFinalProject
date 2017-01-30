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

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_CLAIM_ID. */
    private static final String PARAM_CLAIM_ID = "claimId";

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /** The Constant PARAM_UNCONFIRMED_CLAIMS. */
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";

    /** The Constant PARAM_MESSAGES. */
    private static final String PARAM_MESSAGES = "messages";

    /** The Constant PARAM_CLAIMS_PAGE. */
    private static final String PARAM_CLAIMS_PAGE = "/service?command=go&page=claims";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PARAM_ERROR. */
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    /** The Constant PARAM_MAIN. */
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

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
