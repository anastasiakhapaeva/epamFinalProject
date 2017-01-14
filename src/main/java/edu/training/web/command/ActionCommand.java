package edu.training.web.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 25.11.2016.
 */
public interface ActionCommand {
    Logger LOG = LogManager.getLogger();
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
