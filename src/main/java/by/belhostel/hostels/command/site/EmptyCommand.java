package by.belhostel.hostels.command.site;

import by.belhostel.hostels.command.ActionCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Roman on 25.11.2016.
 */
public class EmptyCommand implements ActionCommand {

    /** The Constant PARAM_INDEX. */
    private static final String PARAM_INDEX = "/index.jsp";

    /**
     * Execute.
     *
     * @param request is servlet's request
     * @param response is servlet's response
     * @return the string
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return PARAM_INDEX;
    }
}
