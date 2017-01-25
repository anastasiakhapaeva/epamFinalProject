package edu.training.web.factory;

import edu.training.web.command.ActionCommand;
import edu.training.web.command.CommandEnum;
import edu.training.web.command.site.EmptyCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Roman on 25.11.2016.
 */
public class ActionFactory {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_COMMAND = "command";

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter(PARAM_COMMAND);

        if (action == null || action.isEmpty()) {
            return current;
        }

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            LOG.error("Such command doesn't exists",e);
        }
        return current;
    }
}
