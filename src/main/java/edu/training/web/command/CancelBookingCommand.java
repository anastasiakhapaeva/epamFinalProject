package edu.training.web.command;

import edu.training.web.entity.Claim;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.User;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Roman on 02.01.2017.
 */
public class CancelBookingCommand implements ActionCommand {
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_BOOKED_HOSTELS = "bookedHostels";
    private static final String PARAM_HOSTEL_PAGE = "/resources/jsp/hostel.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        ArrayList<Hostel> bookedHostels = (ArrayList<Hostel>) session.getAttribute(PARAM_BOOKED_HOSTELS);
        int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
        Hostel current = HostelManager.findHostelById(hostelId);

        boolean res = HostelManager.cancelBooking(currentUser.getUserId(), hostelId);
        if(res){
            bookedHostels.remove(current);
        }
        return PARAM_HOSTEL_PAGE;
    }
}
