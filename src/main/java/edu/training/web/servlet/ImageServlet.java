package edu.training.web.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Roman on 11.01.2017.
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger();
    private static final String IMG_MAIN_PATH = "../hostels";
    public ImageServlet() {
        super();
    }

    public void init() throws ServletException {
        LOG.info("Servlet started!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(IMG_MAIN_PATH, filename);
        Files.copy(file.toPath(), response.getOutputStream());

    }

    public void destroy() {
        super.destroy();
        LOG.info("Servlet destroyed!");
    }
}
