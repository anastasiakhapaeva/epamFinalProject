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

/**
 * Created by Roman on 07.01.2017.
 */
public class AdminDeleteCommand implements ActionCommand {
    private static final String PARAM_CLAIM_ID = "claimId";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_BOOKED_HOSTELS = "bookedHostels";
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";
    private static final String PARAM_CLAIMS_PAGE = "/service?command=go&page=claims";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        int claimId = Integer.parseInt(request.getParameter(PARAM_CLAIM_ID));
        Claim claim = HostelManager.findClaimById(claimId);
        Hostel current = HostelManager.findHostelById(claim.getHostelId());
        User user = HostelManager.findUserById(claim.getUserId());
        boolean res = AdminAction.deleteClaim(claimId);
        if(res){
            ArrayList<Claim> unconfirmedClaims = (ArrayList<Claim>) session.getAttribute(PARAM_UNCONFIRMED_CLAIMS) ;
            unconfirmedClaims.remove(claim);
            List<HttpSession> userSessions = UserSessions.getUserSessions(user.getUserId());
            for(HttpSession userSession : userSessions){
                ArrayList<Hostel> bookedHostels = (ArrayList<Hostel>)userSession.getAttribute(PARAM_BOOKED_HOSTELS);
                bookedHostels.remove(current);
            }
            Message refusingMessage = new Message(user.getUserId(), "Administration", "You can't book " + current.getName() + " from " + claim.getDateIn() +" to " + claim.getDateOut() + " !");
            boolean isSent = HostelManager.sendMessageToUser(refusingMessage);
            if(isSent) {
                for (HttpSession userSession : userSessions) {
                    ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                    messages.add(refusingMessage);
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
