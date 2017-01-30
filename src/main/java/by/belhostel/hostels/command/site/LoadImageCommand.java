package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.command.ImageLoadType;
import by.belhostel.hostels.exception.NoSuchTypeException;
import by.belhostel.hostels.logic.HostelManager;
import com.google.gson.Gson;
import by.belhostel.hostels.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Roman on 27.12.2016.
 */
public class LoadImageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_HOSTEL_ID = "hostelId";

    /** The Constant PARAM_LOAD_TYPE. */
    private static final String PARAM_LOAD_TYPE = "loadType";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
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
            ImageLoadType loadType = ImageLoadType.valueOf(request.getParameter(PARAM_LOAD_TYPE).toUpperCase());
            switch (loadType) {
                case MAIN:
                    ArrayList<String> imgMainPaths = new ArrayList<>();
                    int[] hostelIds = new Gson().fromJson(request.getParameter(PARAM_HOSTEL_ID), int[].class);
                    for(int id : hostelIds){
                        imgMainPaths.add(HostelManager.loadMainImageForHostel(id));
                    }
                    response.getWriter().write(new Gson().toJson(imgMainPaths));
                    break;
                case ALL:
                    int hostelId = new Gson().fromJson(request.getParameter(PARAM_HOSTEL_ID), int.class);
                    ArrayList<String> imgPaths = HostelManager.loadAllImagesForHostel(hostelId);
                    response.getWriter().write(new Gson().toJson(imgPaths));
                    break;
                default:
                    throw new NoSuchTypeException("No such constant in ImageLoadType enum.");
            }
        } catch (IOException | NoSuchTypeException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
