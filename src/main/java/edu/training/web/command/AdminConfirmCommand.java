package edu.training.web.command;

import edu.training.web.entity.Claim;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.Message;
import edu.training.web.entity.User;
import edu.training.web.listener.UserSessions;
import edu.training.web.logic.AdminAction;
import edu.training.web.logic.HostelManager;

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
public class AdminConfirmCommand implements ActionCommand {
    private static final String PARAM_CLAIM_ID = "claimId";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_BOOKED_HOSTELS = "bookedHostels";
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_CLAIMS_PAGE = "/service?command=go&page=claims";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int claimId = Integer.parseInt(request.getParameter(PARAM_CLAIM_ID));
        Claim claim = HostelManager.findClaimById(claimId);
        User user = HostelManager.findUserById(claim.getUserId());
        Hostel current = HostelManager.findHostelById(claim.getHostelId());
        boolean res = AdminAction.confirmClaim(claimId);
        if(res){
            ArrayList<Claim> unconfirmedClaims = (ArrayList<Claim>) session.getAttribute(PARAM_UNCONFIRMED_CLAIMS) ;
            unconfirmedClaims.remove(claim);
            List<HttpSession> userSessions = UserSessions.getUserSessions(user.getUserId());
            for(HttpSession userSession : userSessions){
                ArrayList<Hostel> bookedHostels = (ArrayList<Hostel>)userSession.getAttribute(PARAM_BOOKED_HOSTELS);
                bookedHostels.remove(current);
            }
            long differenceInDays = DAYS.between(claim.getDateIn(), claim.getDateOut());
            boolean isPaid = HostelManager.paymentForHostel(user.getUserId(), current.getPrice() * claim.getRequiredPlaces() * differenceInDays * (1 - user.getDiscount()/100));
            if (isPaid) {
                Message approvalMessage = new Message(user.getUserId(), current.getName(), "You are welcome to our hostel! Waiting for you from " + claim.getDateIn() +" to " + claim.getDateOut() + " !");
                boolean isSent = HostelManager.sendMessageToUser(approvalMessage);
                for(HttpSession userSession : userSessions){
                    User currentUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                    if(isSent) {
                        ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                        messages.add(approvalMessage);
                    }
                    currentUser.setMoney(currentUser.getMoney() - current.getPrice() * claim.getRequiredPlaces() * differenceInDays * (1 - currentUser.getDiscount()/100));
                }

            }
        }
        try{
            response.sendRedirect(request.getContextPath() + PARAM_CLAIMS_PAGE);
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}