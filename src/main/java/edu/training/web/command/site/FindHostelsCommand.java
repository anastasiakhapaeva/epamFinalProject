package edu.training.web.command.site;

import com.google.gson.Gson;
import edu.training.web.command.ActionCommand;
import edu.training.web.command.FindCommandEnum;
import edu.training.web.command.PaginationControl;
import edu.training.web.entity.Hostel;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Roman on 27.12.2016.
 */
public class FindHostelsCommand implements ActionCommand {
    private static final String PARAM_CATALOG = "/service?command=go&page=catalog";
    private static final String PARAM_CITY = "city";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_HOSTELS = "hostels";
    private static final String PARAM_DATE_IN = "date_in";
    private static final String PARAM_DATE_OUT = "date_out";
    private static final String PARAM_GUESTS = "guests";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    private static final String PARAM_CATALOG_PAGE = "/resources/jsp/catalog.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";

        PaginationControl paginationControl = new PaginationControl();
        if(request.getParameter("pageNum") != null) {
            paginationControl.setCurrentPage(Integer.parseInt(request.getParameter("pageNum")));
        }

        if(request.getParameter("perPage") != null){
            paginationControl.setRecordsPerPage(Integer.parseInt(request.getParameter("perPage")));
        }
        HttpSession session = request.getSession(true);

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
                    requestString = "&city=" + city;
                    break;
                case CLAIM:
                    //LocalDate dateIn = LocalDate.parse(request.getParameter(PARAM_DATE_IN), DateTimeFormatter.ofPattern(DATE_FORMAT));
                    //LocalDate dateOut = LocalDate.parse(request.getParameter(PARAM_DATE_OUT), DateTimeFormatter.ofPattern(DATE_FORMAT));
                    int guests = Integer.parseInt(request.getParameter(PARAM_GUESTS));
                    hostels = HostelManager.findSuitableHostels(request.getParameter(PARAM_DATE_IN),request.getParameter(PARAM_DATE_OUT), city,guests, paginationControl);
//                    Iterator<Hostel> hostelIterator = hostels.iterator();
//                    while (hostelIterator.hasNext()) {
//                        Hostel hostel = hostelIterator.next();
//                        if (guests > HostelManager.countFreePlaces(hostel.getHostelId(), dateIn, dateOut)) {
//                            hostelIterator.remove();
//                        }
//                    }

            }

            session.setAttribute(PARAM_HOSTELS, hostels);
            response.sendRedirect(request.getContextPath() + PARAM_CATALOG + "&currentPage=" + paginationControl.getCurrentPage() + "&noOfPages=" + paginationControl.getNumOfPages() + "&perPage=" + paginationControl.getRecordsPerPage() + "&type=" + type + requestString);
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
