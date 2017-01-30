package by.belhostel.hostels.command.user;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.*;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.exception.NoSuchTypeException;
import by.belhostel.hostels.listener.UserSessions;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.logic.Messenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_HOSTEL_ID. */
    private static final String PARAM_HOSTEL_ID = "hostel_id";

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /** The Constant PARAM_DATE_IN. */
    private static final String PARAM_DATE_IN = "date_in";

    /** The Constant PARAM_DATE_OUT. */
    private static final String PARAM_DATE_OUT = "date_out";

    /** The Constant PARAM_GUESTS. */
    private static final String PARAM_GUESTS = "guests";

    /** The Constant PARAM_BOOKING_TYPE. */
    private static final String PARAM_BOOKING_TYPE = "bookingType";

    /** The Constant PARAM_UNCONFIRMED_CLAIMS. */
    private static final String PARAM_UNCONFIRMED_CLAIMS = "unconfirmedClaims";

    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /** The Constant PARAM_HOSTEL_PAGE. */
    private static final String PARAM_HOSTEL_PAGE = "/service?command=go&page=hostel";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PARAM_MESSAGES. */
    private static final String PARAM_MESSAGES = "messages";

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
        if(currentUser != null) {
            try {
                int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
                LocalDate dateIn = LocalDate.parse(request.getParameter(PARAM_DATE_IN), DateTimeFormatter.ofPattern(DATE_FORMAT));
                LocalDate dateOut = LocalDate.parse(request.getParameter(PARAM_DATE_OUT), DateTimeFormatter.ofPattern(DATE_FORMAT));
                int guests = Integer.parseInt(request.getParameter(PARAM_GUESTS));

                ClaimType bookingType = ClaimType.valueOf(request.getParameter(PARAM_BOOKING_TYPE).toUpperCase());
                Claim newClaim = new Claim(hostelId, currentUser.getUserId(), bookingType, guests, dateIn, dateOut);
                switch (bookingType) {
                    case RESERVATION:
                        newClaim.setConfirmed(false);
                        boolean resReserve = HostelManager.bookHostelByClaim(newClaim);
                        if (resReserve) {
                            List<HttpSession> adminSessions = UserSessions.getAdminsSessions();
                            for (HttpSession adminSession : adminSessions) {
                                ArrayList<Claim> unconfirmedClaims = (ArrayList<Claim>) adminSession.getAttribute(PARAM_UNCONFIRMED_CLAIMS);
                                unconfirmedClaims.add(newClaim);
                            }
                        }
                        break;
                    case PAYMENT:
                        newClaim.setConfirmed(true);
                        boolean resPayment = HostelManager.bookHostelByClaim(newClaim);
                        if (resPayment) {
                            Hostel current = HostelManager.findHostelById(hostelId);
                            boolean isPaid = HostelManager.payForHostel(currentUser.getUserId(), current.getPrice() * guests * (1 - currentUser.getDiscount() / 100));
                            if (isPaid) {
                                Message paymentMessage = Messenger.generatePaymentMessage(currentUser.getUserId(), current.getName(), newClaim);
                                boolean isSent = HostelManager.sendMessageToUser(paymentMessage);
                                if (isSent) {
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
                    default:
                        throw new NoSuchTypeException("No such constant in ClaimType enum");
                }


                response.sendRedirect(request.getContextPath() + PARAM_HOSTEL_PAGE);
            } catch (IOException | NoSuchTypeException | NumberFormatException | LogicException e) {
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
