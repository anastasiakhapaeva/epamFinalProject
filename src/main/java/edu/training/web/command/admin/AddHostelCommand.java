package edu.training.web.command.admin;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.HostelImage;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.AdminAction;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by Roman on 11.01.2017.
 */
public class AddHostelCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_HOSTEL_NAME = "newHostelName";
    private static final String PARAM_HOSTEL_CITY = "newHostelCity";
    private static final String PARAM_HOSTEL_ADDRESS = "newHostelAddress";
    private static final String PARAM_HOSTEL_PHONE = "newHostelPhone";
    private static final String PARAM_HOSTEL_PRICE = "newHostelPrice";
    private static final String PARAM_HOSTEL_PLACES = "newHostelPlaces";
    private static final String PARAM_HOSTEL_DESC = "newHostelDesc";
    private static final String PARAM_PHOTO_ONE = "newHostelPhoto1";
    private static final String PARAM_PHOTO_TWO = "newHostelPhoto2";
    private static final String PARAM_PHOTO_THREE = "newHostelPhoto3";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PATH_BEG = "../hostels/";
    private static final String IMG_TYPE = ".jpg";
    private static final String PARAM_CATALOG = "/resources/jsp/catalog.jsp";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = PARAM_CATALOG;
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
                        inputStream = filePart.getInputStream();
                        File photo = new File(PATH_BEG + hostelName + i + IMG_TYPE);
                        HostelImage hostelImg = new HostelImage(newHostel.getHostelId(), true, hostelName + (i++) + IMG_TYPE);
                        AdminAction.addHostelImage(hostelImg);
                        outputStream = new FileOutputStream(photo);
                        IOUtils.copy(inputStream, outputStream);
                        inputStream.close();
                        outputStream.close();
                    }
                }
            }
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
