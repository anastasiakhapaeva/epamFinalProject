package edu.training.web.command;

import edu.training.web.entity.Hostel;
import edu.training.web.listener.UserSessions;
import edu.training.web.logic.AdminAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 11.01.2017.
 */
public class AdminEditHostelCommand implements ActionCommand {
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_HOSTEL_NAME = "hostelName";
    private static final String PARAM_HOSTEL_CITY= "hostelCity";
    private static final String PARAM_HOSTEL_ADDRESS = "hostelAddress";
    private static final String PARAM_HOSTEL_PHONE = "hostelPhone";
    private static final String PARAM_HOSTEL_PRICE = "hostelPrice";
    private static final String PARAM_HOSTEL_PLACES = "hostelPlaces";
    private static final String PARAM_HOSTEL_DESC = "hostelDesc";
    private static final String PARAM_HOSTELS = "hostels";
    private static final String PARAM_HOSTEL = "hostel";
    private static final String PARAM_HOSTEL_PAGE = "/service?command=go&page=hostel";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Hostel currentHostel = (Hostel) session.getAttribute(PARAM_HOSTEL);
        int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
        String hostelName = request.getParameter(PARAM_HOSTEL_NAME);
        String hostelCity = request.getParameter(PARAM_HOSTEL_CITY);
        String hostelAddress = request.getParameter(PARAM_HOSTEL_ADDRESS);
        String hostelPhone = request.getParameter(PARAM_HOSTEL_PHONE);
        double hostelPrice = Double.parseDouble(request.getParameter(PARAM_HOSTEL_PRICE));
        int hostelPlaces = Integer.parseInt(request.getParameter(PARAM_HOSTEL_PLACES));
        String hostelDesc = request.getParameter(PARAM_HOSTEL_DESC);
        Hostel editedHostel = new Hostel(hostelId, hostelName, hostelPlaces, hostelPrice, hostelPhone, hostelCity, hostelDesc, hostelAddress);
        boolean res = AdminAction.editHostel(editedHostel);
        if(res){
            for(HttpSession userSession : UserSessions.getAllSessions()){
                ArrayList<Hostel> hostels = ((ArrayList<Hostel>)userSession.getAttribute(PARAM_HOSTELS));
                if(hostels != null) {
                    if (hostels.contains(currentHostel)) {
                        int index = hostels.indexOf(currentHostel);
                        hostels.remove(index);
                        hostels.add(index, editedHostel);
                    }
                }
                Hostel currentUserHostel = (Hostel) userSession.getAttribute(PARAM_HOSTEL);
                if(currentUserHostel != null) {
                    if (currentHostel.equals(currentUserHostel)) {
                        userSession.setAttribute(PARAM_HOSTEL, editedHostel);
                    }
                }
            }
        }

        try {
            response.sendRedirect(request.getContextPath() + PARAM_HOSTEL_PAGE);
        } catch (IOException e) {
            LOG.error(e);
        }
        return "";
    }
}
