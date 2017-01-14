package edu.training.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 27.12.2016.
 */
@WebFilter(urlPatterns = {"/service"})
public class PageSaverFilter implements Filter {
    private static final String PARAM_PAGE = "page";
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        String path1 = request.getQueryString();
        String page = request.getRequestURI().toString();
        session.setAttribute(PARAM_PAGE, page);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
