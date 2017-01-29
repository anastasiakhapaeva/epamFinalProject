package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 25.11.2016.
 */
public class LanguageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_LOCALE = "locale";
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "";
        String lang = request.getParameter(PARAM_LOCALE);
        HttpSession session = request.getSession(true);

        if (lang.isEmpty()) {
            session.setAttribute(PARAM_LOCALE, DEFAULT_LOCALE);
        } else {
            session.setAttribute(PARAM_LOCALE, lang);
        }

        try {
            response.getWriter().write(Boolean.toString(true));
        } catch (IOException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
