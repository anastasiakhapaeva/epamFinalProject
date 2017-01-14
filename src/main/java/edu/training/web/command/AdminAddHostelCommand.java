package edu.training.web.command;

import edu.training.web.entity.Hostel;
import edu.training.web.entity.HostelImage;
import edu.training.web.logic.AdminAction;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by Roman on 11.01.2017.
 */
public class AdminAddHostelCommand implements ActionCommand {
    private static final String PARAM_HOSTEL_NAME = "newHostelName";
    private static final String PARAM_HOSTEL_CITY= "newHostelCity";
    private static final String PARAM_HOSTEL_ADDRESS = "newHostelAddress";
    private static final String PARAM_HOSTEL_PHONE = "newHostelPhone";
    private static final String PARAM_HOSTEL_PRICE = "newHostelPrice";
    private static final String PARAM_HOSTEL_PLACES = "newHostelPlaces";
    private static final String PARAM_HOSTEL_DESC = "newHostelDesc";
    private static final String PARAM_PHOTO_ONE = "newHostelPhoto1";
    private static final String PARAM_PHOTO_TWO = "newHostelPhoto2";
    private static final String PARAM_PHOTO_THREE = "newHostelPhoto3";
    private static final String PATH_BEG = "../hostels/";
    private static final String IMG_TYPE = ".jpg";
    private static final String PARAM_CATALOG = "/resources/jsp/catalog.jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hostelName = request.getParameter(PARAM_HOSTEL_NAME);
        String hostelCity = request.getParameter(PARAM_HOSTEL_CITY);
        String hostelAddress = request.getParameter(PARAM_HOSTEL_ADDRESS);
        String hostelPhone = request.getParameter(PARAM_HOSTEL_PHONE);
        double hostelPrice = Double.parseDouble(request.getParameter(PARAM_HOSTEL_PRICE));
        int hostelPlaces = Integer.parseInt(request.getParameter(PARAM_HOSTEL_PLACES));
        String hostelDesc = request.getParameter(PARAM_HOSTEL_DESC);
        Hostel newHostel = new Hostel(hostelName, hostelPlaces, hostelPrice, hostelPhone, hostelCity, hostelDesc, hostelAddress);
        boolean res = AdminAction.addHostel(newHostel);
        if(res) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            Part filePartOne = request.getPart(PARAM_PHOTO_ONE);
            Part filePartTwo = request.getPart(PARAM_PHOTO_TWO);
            Part filePartThree = request.getPart(PARAM_PHOTO_THREE);
            if (filePartOne != null) {
                inputStream = filePartOne.getInputStream();
                File photo = new File(PATH_BEG + hostelName + "1" + IMG_TYPE);
                HostelImage photoOne = new HostelImage(newHostel.getHostelId(), true, hostelName + "1" + IMG_TYPE);
                AdminAction.addHostelImage(photoOne);
                outputStream = new FileOutputStream(photo);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            }
            if (filePartTwo != null) {
                inputStream = filePartTwo.getInputStream();
                File photo = new File(PATH_BEG + hostelName + "2" + IMG_TYPE);
                HostelImage photoTwo = new HostelImage(newHostel.getHostelId(), false, hostelName + "2" + IMG_TYPE);
                AdminAction.addHostelImage(photoTwo);
                outputStream = new FileOutputStream(photo);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            }
            if (filePartThree != null) {
                inputStream = filePartThree.getInputStream();
                File photo = new File(PATH_BEG + hostelName + "3" + IMG_TYPE);
                HostelImage photoThree = new HostelImage(newHostel.getHostelId(), false, hostelName + "3" + IMG_TYPE);
                AdminAction.addHostelImage(photoThree);
                outputStream = new FileOutputStream(photo);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            }
        }
        return PARAM_CATALOG;
    }
}
