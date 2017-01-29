package edu.training.web.command.user;

import edu.training.web.command.ActionCommand;
import edu.training.web.entity.Message;
import edu.training.web.exception.LogicException;
import edu.training.web.logic.HostelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 14.01.2017.
 */
public class DeleteMessageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PARAM_MESSAGE_ID = "messageId";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "";
        try {
            HttpSession session = request.getSession();
            int messageId = Integer.parseInt(request.getParameter(PARAM_MESSAGE_ID));
            boolean res = HostelManager.deleteMessageById(messageId);
            if (res) {
                Message current = HostelManager.findMessageById(messageId);
                ArrayList<Message> messages = (ArrayList<Message>) session.getAttribute(PARAM_MESSAGES);
                messages.remove(current);
            }
            response.getWriter().write(Boolean.toString(res));
        } catch (IOException | NumberFormatException | LogicException e) {
            LOG.error(e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = PARAM_ERROR;
        }
        return page;
    }
}
