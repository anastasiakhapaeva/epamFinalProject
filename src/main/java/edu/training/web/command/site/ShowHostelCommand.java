package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Hostel;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Roman on 01.01.2017.
 */
public class ShowHostelCommand implements ActionCommand {
    private static final String PARAM_HOSTEL = "hostel";
    private static final String PARAM_HOSTEL_ID = "id";
    private static final String PARAM_HOSTEL_PAGE = "/resources/jsp/hostel.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PARAM_HOSTEL_PAGE;
        HttpSession session = request.getSession(true);
        try {
            int id = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            Hostel current = HostelManager.findHostelById(id);
            session.setAttribute(PARAM_HOSTEL, current);
        }catch (NumberFormatException | LogicException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
