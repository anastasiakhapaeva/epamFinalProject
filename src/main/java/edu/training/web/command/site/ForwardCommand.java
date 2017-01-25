package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Roman on 04.01.2017.
 */
public class ForwardCommand implements ActionCommand {
    private static final String PARAM_PAGE = "page";
    private static final String PATH_BEG = "/resources/jsp/";
    private static final String PATH_END = ".jsp";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return PATH_BEG + request.getParameter(PARAM_PAGE) + PATH_END;
    }
}
