package edu.training.web.command;

import edu.training.web.entity.Message;
import edu.training.web.logic.HostelManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 14.01.2017.
 */
public class AjaxDeleteMessageCommand implements ActionCommand {
    private static final String PARAM_MESSAGE_ID = "messageId";
    private static final String PARAM_MESSAGES = "messages";
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int messageId = Integer.parseInt(request.getParameter(PARAM_MESSAGE_ID));
            boolean res = HostelManager.deleteMessage(messageId);
            if(res){
                Message current = HostelManager.findMessageById(messageId);
                ArrayList<Message> messages = (ArrayList<Message>) session.getAttribute(PARAM_MESSAGES);
                messages.remove(current);
            }
            response.getWriter().write(Boolean.toString(res));
        }catch (IOException e){
            LOG.error(e);
        }
        return "";
    }
}
