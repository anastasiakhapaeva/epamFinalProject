package edu.training.web.command;

import edu.training.web.entity.Claim;
import edu.training.web.entity.ClaimType;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.User;
import edu.training.web.listener.UserSessions;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Roman on 01.01.2017.
 */
public class BookingCommand implements ActionCommand {
    private static final String PARAM_HOSTEL_ID = "hostel_id";
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_DATE_IN = "date_in";
    private static final String PARAM_DATE_OUT = "date_out";
    private static final String PARAM_GUESTS = "guests";
    private static final String PARAM_BOOKING_TYPE = "bookingType";
    private static final String PARAM_PAID_HOSTELS = "paidHostels";
    private static final String PARAM_BOOKED_HOSTELS = "bookedHostels";
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String PARAM_HOSTEL_PAGE = "/service?command=go&page=hostel";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
        LocalDate dateIn = LocalDate.parse(request.getParameter(PARAM_DATE_IN), DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDate dateOut = LocalDate.parse(request.getParameter(PARAM_DATE_OUT), DateTimeFormatter.ofPattern(DATE_FORMAT));
        int guests = Integer.parseInt(request.getParameter(PARAM_GUESTS));

        ClaimType bookingType = ClaimType.valueOf(request.getParameter(PARAM_BOOKING_TYPE).toUpperCase());
        Claim newClaim = new Claim(hostelId, currentUser.getUserId(), bookingType, guests, dateIn, dateOut);
        switch (bookingType) {
            case RESERVATION:
                newClaim.setConfirmed(false);
                ArrayList<Hostel> bookedHostels = (ArrayList<Hostel>) session.getAttribute(PARAM_BOOKED_HOSTELS);
                boolean resReserve = HostelManager.bookHostel(newClaim);
                if (resReserve) {
                    Hostel current = HostelManager.findHostelById(hostelId);
                    bookedHostels.add(current);
                    List<HttpSession> adminSessions = UserSessions.getAdminsSessions();
                    for(HttpSession adminSession : adminSessions){
                        ArrayList<Claim> unconfirmedClaims = (ArrayList<Claim>) adminSession.getAttribute(PARAM_UNCONFIRMED_CLAIMS);
                        unconfirmedClaims.add(newClaim);
                    }
                }
                break;
            case PAYMENT:
                newClaim.setConfirmed(true);
                ArrayList<Hostel> paidHostels = (ArrayList<Hostel>) session.getAttribute(PARAM_PAID_HOSTELS);
                boolean resPayment = HostelManager.bookHostel(newClaim);
                if (resPayment) {
                    Hostel current = HostelManager.findHostelById(hostelId);
                    paidHostels.add(current);
                    boolean isPaid = HostelManager.paymentForHostel(currentUser.getUserId(), current.getPrice() * guests * (1 - currentUser.getDiscount()/100));
                    if (isPaid) {
                        currentUser.setMoney(currentUser.getMoney() - current.getPrice() * guests * (1 - currentUser.getDiscount()/100));
                    }
                }
                break;
        }


        try {
            response.sendRedirect(request.getContextPath() + PARAM_HOSTEL_PAGE);
        } catch (IOException e) {
            LOG.error(e);
        }
        return "";
    }
}
