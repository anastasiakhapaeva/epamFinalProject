package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Roman on 05.01.2017.
 */
public class FreePlacesCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_HOSTEL_ID. */
    private static final String PARAM_HOSTEL_ID = "hostelId";

    /** The Constant PARAM_DATE_IN. */
    private static final String PARAM_DATE_IN = "dateIn";

    /** The Constant PARAM_DATE_OUT. */
    private static final String PARAM_DATE_OUT = "dateOut";

    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PARAM_ERROR. */
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            LocalDate dateIn = LocalDate.parse(request.getParameter(PARAM_DATE_IN), DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateOut = LocalDate.parse(request.getParameter(PARAM_DATE_OUT), DateTimeFormatter.ofPattern(DATE_FORMAT));
            response.getWriter().write(HostelManager.countFreePlaces(hostelId, dateIn, dateOut).toString());
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
