package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Hostel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Roman on 01.01.2017.
 */
public class ShowHostelCommand implements ActionCommand {
    private static final String PARAM_HOSTEL = "hostel";
    private static final String PARAM_HOSTELS = "hostels";
    private static final String PARAM_HOSTEL_INDEX = "index";
    private static final String PARAM_HOSTEL_PAGE = "/resources/jsp/hostel.jsp";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PARAM_HOSTEL_PAGE;
        HttpSession session = request.getSession(true);
        try {
            int index = Integer.parseInt(request.getParameter(PARAM_HOSTEL_INDEX));
            Hostel current = ((ArrayList<Hostel>) session.getAttribute(PARAM_HOSTELS)).get(index);
            session.setAttribute(PARAM_HOSTEL, current);
        }catch (NumberFormatException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
