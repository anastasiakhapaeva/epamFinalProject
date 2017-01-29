package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;
import edu.training.web.command.FindCommandEnum;
import edu.training.web.command.PaginationControl;
import edu.training.web.entity.Hostel;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 27.12.2016.
 */
public class FindHostelsCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_CATALOG = "/service?command=go&page=catalog";
    private static final String PARAM_CITY = "city";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_HOSTELS = "hostels";
    private static final String PARAM_DATE_IN = "date_in";
    private static final String PARAM_DATE_OUT = "date_out";
    private static final String PARAM_GUESTS = "guests";
    private static final String PARAM_PAGINATION_PAGE = "pageNum";
    private static final String PARAM_PAGINATION_PERPAGE = "perPage";
    private static final String PARAM_REQUEST_CITY = "&city=";
    private static final String PARAM_REQUEST_PAGE = "&currentPage=";
    private static final String PARAM_REQUEST_PAGES = "&noOfPages=";
    private static final String PARAM_REQUEST_PERPAGE = "&perPage=";
    private static final String PARAM_REQUEST_TYPE = "&type=";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        HttpSession session = request.getSession(true);

        PaginationControl paginationControl = new PaginationControl();
        String currPaginationPage = request.getParameter(PARAM_PAGINATION_PAGE);
        if (currPaginationPage != null) {
            paginationControl.setCurrentPage(Integer.parseInt(currPaginationPage));
        }

        String paginationPerPage = request.getParameter(PARAM_PAGINATION_PERPAGE);
        if (paginationPerPage != null) {
            paginationControl.setRecordsPerPage(Integer.parseInt(paginationPerPage));
        }

        String city = request.getParameter(PARAM_CITY);
        String requestString = "";
        String type = request.getParameter(PARAM_TYPE);
        FindCommandEnum commandType = FindCommandEnum.valueOf(type.toUpperCase());
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        try {
            switch (commandType) {
                case ALL:
                    hostels = HostelManager.findAllHostels(paginationControl);
                    break;
                case CITY:
                    hostels = HostelManager.findHostelsByCity(city, paginationControl);
                    requestString = PARAM_REQUEST_CITY + city;
                    break;
                case CLAIM:
                    String dateIn = request.getParameter(PARAM_DATE_IN);
                    String dateOut = request.getParameter(PARAM_DATE_OUT);
                    int guests = Integer.parseInt(request.getParameter(PARAM_GUESTS));
                    hostels = HostelManager.findSuitableHostels(dateIn, dateOut, city, guests, paginationControl);
            }

            session.setAttribute(PARAM_HOSTELS, hostels);
            response.sendRedirect(request.getContextPath() + PARAM_CATALOG + PARAM_REQUEST_PAGE + paginationControl.getCurrentPage() +
                    PARAM_REQUEST_PAGES + paginationControl.getNumOfPages() + PARAM_REQUEST_PERPAGE + paginationControl.getRecordsPerPage() +
                    PARAM_REQUEST_TYPE + type + requestString);
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
