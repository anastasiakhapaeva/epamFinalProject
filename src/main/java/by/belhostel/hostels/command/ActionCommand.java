package by.belhostel.hostels.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 25.11.2016.
 */
public interface ActionCommand {

    /**
     * Execute.
     *
     * @param request the request
     * @param response the response
     * @return the string
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
