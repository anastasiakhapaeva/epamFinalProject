package by.belhostel.hostels.command.user;

import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.logic.HostelManager;
import by.belhostel.hostels.entity.Message;
import by.belhostel.hostels.exception.LogicException;
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
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ERROR = "/resources/jsp/error.jsp";
    private static final String PARAM_MAIN = "/resources/jsp/main.jsp";

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = "";
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if(currentUser != null) {
            try {
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
        } else {
            page = PARAM_MAIN;
        }
        return page;
    }
}
