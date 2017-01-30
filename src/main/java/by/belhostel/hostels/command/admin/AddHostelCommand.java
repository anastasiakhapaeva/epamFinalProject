package by.belhostel.hostels.command.admin;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.entity.HostelImage;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.logic.AdminAction;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by Roman on 11.01.2017.
 */
public class AddHostelCommand implements ActionCommand {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /** The Constant PARAM_HOSTEL_NAME. */
    private static final String PARAM_HOSTEL_NAME = "newHostelName";

    /** The Constant PARAM_HOSTEL_CITY. */
    private static final String PARAM_HOSTEL_CITY = "newHostelCity";

    /** The Constant PARAM_HOSTEL_ADDRESS. */
    private static final String PARAM_HOSTEL_ADDRESS = "newHostelAddress";

    /** The Constant PARAM_HOSTEL_PHONE. */
    private static final String PARAM_HOSTEL_PHONE = "newHostelPhone";

    /** The Constant PARAM_HOSTEL_PRICE. */
    private static final String PARAM_HOSTEL_PRICE = "newHostelPrice";

    /** The Constant PARAM_HOSTEL_PLACES. */
    private static final String PARAM_HOSTEL_PLACES = "newHostelPlaces";

    /** The Constant PARAM_HOSTEL_DESC. */
    private static final String PARAM_HOSTEL_DESC = "newHostelDesc";

    /** The Constant PARAM_PHOTO_ONE. */
    private static final String PARAM_PHOTO_ONE = "newHostelPhoto1";

    /** The Constant PARAM_PHOTO_TWO. */
    private static final String PARAM_PHOTO_TWO = "newHostelPhoto2";

    /** The Constant PARAM_PHOTO_THREE. */
    private static final String PARAM_PHOTO_THREE = "newHostelPhoto3";

    /** The Constant PARAM_ERROR_MESSAGE. */
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    /** The Constant PATH_BEG. */
    private static final String PATH_BEG = "../hostels/";

    /** The Constant IMG_TYPE. */
    private static final String IMG_TYPE = ".jpg";

    /** The Constant PARAM_HOSTEL. */
    private static final String PARAM_HOSTEL = "/service?command=show_hostel&id=";

    /** The Constant PARAM_MAIN. */
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    /** The Constant PARAM_ERROR. */
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "";
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if (currentUser != null && currentUser.isAdmin()) {
            String hostelName = request.getParameter(PARAM_HOSTEL_NAME);
            String hostelCity = request.getParameter(PARAM_HOSTEL_CITY);
            String hostelAddress = request.getParameter(PARAM_HOSTEL_ADDRESS);
            String hostelPhone = request.getParameter(PARAM_HOSTEL_PHONE);
            String hostelDesc = request.getParameter(PARAM_HOSTEL_DESC);
            try {
                double hostelPrice = Double.parseDouble(request.getParameter(PARAM_HOSTEL_PRICE));
                int hostelPlaces = Integer.parseInt(request.getParameter(PARAM_HOSTEL_PLACES));
                Hostel newHostel = new Hostel(hostelName, hostelPlaces, hostelPrice, hostelPhone, hostelCity, hostelDesc, hostelAddress);
                boolean res = AdminAction.addHostel(newHostel);
                if (res) {
                    InputStream inputStream;
                    OutputStream outputStream;
                    Part filePartOne = request.getPart(PARAM_PHOTO_ONE);
                    Part filePartTwo = request.getPart(PARAM_PHOTO_TWO);
                    Part filePartThree = request.getPart(PARAM_PHOTO_THREE);
                    Part[] parts = {filePartOne, filePartTwo, filePartThree};
                    int i = 1;
                    for (Part filePart : parts) {
                        if (filePart != null) {
                            boolean isMain = true;
                            if (i != 1){
                                isMain = false;
                            }
                            inputStream = filePart.getInputStream();
                            File photo = new File(PATH_BEG + newHostel.getName() + i + newHostel.hashCode() + IMG_TYPE);
                            HostelImage hostelImg = new HostelImage(newHostel.getHostelId(), isMain, newHostel.getName() + i + newHostel.hashCode() + IMG_TYPE);
                            AdminAction.addHostelImage(hostelImg);
                            outputStream = new FileOutputStream(photo);
                            IOUtils.copy(inputStream, outputStream);
                            inputStream.close();
                            outputStream.close();
                            i++;
                        }
                    }
                    response.sendRedirect(request.getContextPath() + PARAM_HOSTEL + newHostel.getHostelId());
                }
            } catch (IOException | NumberFormatException | LogicException e) {
                LOG.error(e);
                request.setAttribute(PARAM_ERROR_MESSAGE, e);
                page = PARAM_ERROR;
            }
        } else {
            page = PARAM_MAIN;
        }
        return page;
    }
}
