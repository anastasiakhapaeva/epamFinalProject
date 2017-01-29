package edu.training.web.command.site;

import com.google.gson.Gson;
import edu.training.web.command.ActionCommand;
import edu.training.web.command.ImageLoadType;
import edu.training.web.exception.LogicException;
import edu.training.web.exception.NoSuchTypeException;
import edu.training.web.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 27.12.2016.
 */
public class LoadImageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_HOSTEL_ID = "hostelId";
    private static final String PARAM_LOAD_TYPE = "loadType";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        try {
            int hostelId = Integer.parseInt(request.getParameter(PARAM_HOSTEL_ID));
            ImageLoadType loadType = ImageLoadType.valueOf(request.getParameter(PARAM_LOAD_TYPE).toUpperCase());
            switch (loadType) {
                case MAIN:
                    String imgPath = HostelManager.loadMainImageForHostel(hostelId);
                    response.getWriter().write(imgPath);
                    break;
                case ALL:
                    ArrayList<String> imgPaths = HostelManager.loadAllImagesForHostel(hostelId);
                    String json = new Gson().toJson(imgPaths);
                    response.getWriter().write(json);
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
