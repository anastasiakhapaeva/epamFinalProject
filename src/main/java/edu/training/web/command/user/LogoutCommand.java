package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 26.12.2016.
 */
public class LogoutCommand implements ActionCommand {
    private static final String PARAM_MAIN = "/service?command=go&page=main";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        HttpSession session = request.getSession(true);
        if(session != null){
            session.invalidate();
        }
        try{
            response.sendRedirect(request.getContextPath() + PARAM_MAIN);
        }catch (IOException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
