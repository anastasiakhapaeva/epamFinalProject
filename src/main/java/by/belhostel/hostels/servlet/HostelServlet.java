package by.belhostel.hostels.servlet;


import by.belhostel.hostels.command.ActionCommand;
import by.belhostel.hostels.command.RequestChecker;
import by.belhostel.hostels.pool.ConnectionPool;
import by.belhostel.hostels.factory.ActionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 22.11.2016.
 */
@WebServlet("/service")
@MultipartConfig(maxFileSize = 16177215)
public class HostelServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger();
    public HostelServlet() {
        super();
    }

    public void init() throws ServletException {
        LOG.info("Servlet started!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);

        if(RequestChecker.isAjaxRequest(request)){
            command.execute(request, response);
        }else {
            page = command.execute(request, response);
            if(page.isEmpty()){
                return;
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closePool();
        LOG.info("Servlet destroyed!");
    }
}