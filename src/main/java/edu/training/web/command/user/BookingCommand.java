package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.*;
import edu.training.web.exception.LogicException;
import edu.training.web.listener.UserSessions;
import edu.training.web.logic.HostelManager;
import edu.training.web.logic.Messenger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        HttpSession session = request.getSession(true);
        try {
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
                   // ArrayList<Hostel> bookedHostels = (ArrayList<Hostel>) session.getAttribute(PARAM_BOOKED_HOSTELS);
                    boolean resReserve = HostelManager.bookHostel(newClaim);
                    if (resReserve) {
                        //Hostel current = HostelManager.findHostelById(hostelId);
                        //bookedHostels.add(current);
                        List<HttpSession> adminSessions = UserSessions.getAdminsSessions();
                        for (HttpSession adminSession : adminSessions) {
                            ArrayList<Claim> unconfirmedClaims = (ArrayList<Claim>) adminSession.getAttribute(PARAM_UNCONFIRMED_CLAIMS);
                            unconfirmedClaims.add(newClaim);
                        }
                    }
                    break;
                case PAYMENT:
                    newClaim.setConfirmed(true);
                    //ArrayList<Hostel> paidHostels = (ArrayList<Hostel>) session.getAttribute(PARAM_PAID_HOSTELS);
                    boolean resPayment = HostelManager.bookHostel(newClaim);
                    if (resPayment) {
                        Hostel current = HostelManager.findHostelById(hostelId);
                       // paidHostels.add(current);
                        boolean isPaid = HostelManager.paymentForHostel(currentUser.getUserId(), current.getPrice() * guests * (1 - currentUser.getDiscount() / 100));
                        if (isPaid) {
                            Message paymentMessage = Messenger.generatePaymentMessage(currentUser.getUserId(), current.getName(), newClaim);
                            boolean isSent = HostelManager.sendMessageToUser(paymentMessage);
                            if(isSent) {
                                List<HttpSession> userSessions = UserSessions.getUserSessions(currentUser.getUserId());
                                for (HttpSession userSession : userSessions) {
                                    ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                                    messages.add(paymentMessage);
                                }
                            }
                            currentUser.setMoney(currentUser.getMoney() - current.getPrice() * guests * (1 - currentUser.getDiscount() / 100));
                        }
                    }
                    break;
            }


            response.sendRedirect(request.getContextPath() + PARAM_HOSTEL_PAGE);
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
