package edu.training.web.command;

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
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            LocalDate dateIn = LocalDate.parse(request.getParameter(PARAM_DATE_IN), DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateOut = LocalDate.parse(request.getParameter(PARAM_DATE_OUT), DateTimeFormatter.ofPattern(DATE_FORMAT));
            response.getWriter().write(HostelManager.countFreePlaces(hostelId, dateIn, dateOut).toString());
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}
