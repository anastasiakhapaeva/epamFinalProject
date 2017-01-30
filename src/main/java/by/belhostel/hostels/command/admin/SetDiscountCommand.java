package by.belhostel.hostels.command.admin;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.logic.AdminAction;
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

/**
 * Created by Roman on 10.01.2017.
 */
public class SetDiscountCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_USER_ID. */
    private static final String PARAM_USER_ID = "userId";

    /** The Constant PARAM_MESSAGES. */
    private static final String PARAM_MESSAGES = "messages";

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /** The Constant PARAM_DISCOUNT. */
    private static final String PARAM_DISCOUNT = "discount";

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
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if (currentUser != null && currentUser.isAdmin()) {
            try {
                int userId = Integer.parseInt(request.getParameter(PARAM_USER_ID));
                double discount = Double.parseDouble(request.getParameter(PARAM_DISCOUNT));
                User user = HostelManager.findUserById(userId);
                List<HttpSession> userSessions = UserSessions.getUserSessions(user.getUserId());
                boolean isDiscountSet = AdminAction.setDiscountForUser(userId, discount);
                if (isDiscountSet) {
                    Message discountMessage = Messenger.generateDiscountMessage(userId);
                    boolean isSent = HostelManager.sendMessageToUser(discountMessage);
                    for (HttpSession userSession : userSessions) {
                        User sessionUser = (User) userSession.getAttribute(PARAM_CURRENT_USER);
                        sessionUser.setDiscount(discount);
                        if (isSent) {
                            ArrayList<Message> messages = (ArrayList<Message>) userSession.getAttribute(PARAM_MESSAGES);
                            messages.add(discountMessage);
                        }
                    }
                }
                response.getWriter().write(Boolean.toString(isDiscountSet));
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
