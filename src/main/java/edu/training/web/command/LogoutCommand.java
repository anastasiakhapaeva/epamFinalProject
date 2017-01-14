package edu.training.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Roman on 26.12.2016.
 */
public class LogoutCommand implements ActionCommand {
    private static final String PARAM_INDEX = "/index.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        if(session != null){
            session.invalidate();
        }

        return PARAM_INDEX;
    }
}
