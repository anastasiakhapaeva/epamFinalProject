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

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Instantiates a new hostel servlet.
     */
    public HostelServlet() {
        super();
    }

    /**
     * Inits the servlet.
     *
     * @throws ServletException the servlet exception
     */
    public void init() throws ServletException {
        LOG.info("Servlet started!");
    }

    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
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

    /**
     * Destroy.
     */
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closePool();
        LOG.info("Servlet destroyed!");
    }
}