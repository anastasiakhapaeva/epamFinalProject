package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Roman on 05.01.2017.
 */
public class AjaxFreePlacesCommand implements ActionCommand {
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_DATE_IN = "dateIn";
    private static final String PARAM_DATE_OUT = "dateOut";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            LocalDate dateIn = LocalDate.parse(request.getParameter(PARAM_DATE_IN), DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateOut = LocalDate.parse(request.getParameter(PARAM_DATE_OUT), DateTimeFormatter.ofPattern(DATE_FORMAT));
            response.getWriter().write(HostelManager.countFreePlaces(hostelId, dateIn, dateOut).toString());
        }catch (IOException | NumberFormatException | LogicException e){
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
