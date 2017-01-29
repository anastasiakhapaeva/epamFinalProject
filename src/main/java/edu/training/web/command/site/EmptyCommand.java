package edu.training.web.command.site;

import edu.training.web.command.ActionCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Roman on 25.11.2016.
 */
public class EmptyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_INDEX = "/index.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return PARAM_INDEX;
    }
}
