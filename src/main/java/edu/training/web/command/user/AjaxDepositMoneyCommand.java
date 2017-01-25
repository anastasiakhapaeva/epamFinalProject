package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.User;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 02.01.2017.
 */
public class AjaxDepositMoneyCommand implements ActionCommand {
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
            boolean res = HostelManager.depositMoney(userId, amount);
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
