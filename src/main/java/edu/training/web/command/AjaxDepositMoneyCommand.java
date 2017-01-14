package edu.training.web.command;

import edu.training.web.entity.User;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        User current = (User)session.getAttribute(PARAM_CURRENT_USER);
        int userId = current.getUserId();
        double amount = Double.parseDouble(request.getParameter(PARAM_MONEY_AMOUNT));
        try{
            boolean res = HostelManager.depositMoney(userId, amount);
            if(res){
                current.setMoney(current.getMoney() + amount);
            }
            response.getWriter().write(Boolean.toString(res));
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}
