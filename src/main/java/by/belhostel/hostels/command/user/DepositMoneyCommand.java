package by.belhostel.hostels.command.user;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 02.01.2017.
 */
public class DepositMoneyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_MONEY_AMOUNT = "amountMon";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        HttpSession session = request.getSession(true);
        User current = (User) session.getAttribute(PARAM_CURRENT_USER);
        try {
            int userId = current.getUserId();
            double amount = Double.parseDouble(request.getParameter(PARAM_MONEY_AMOUNT));
            boolean res = HostelManager.depositMoneyForUser(userId, amount);
            if (res) {
                current.setMoney(current.getMoney() + amount);
            }
            response.getWriter().write(Boolean.toString(res));
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
