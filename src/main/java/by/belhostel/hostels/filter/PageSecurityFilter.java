package by.belhostel.hostels.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Roman on 29.01.2017.
 */
@WebFilter(urlPatterns = {"/resources/jsp/*", "/service?command=go&page="},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp"),
                    @WebInitParam(name = "PAGES", value = "users, claims")})
public class PageSecurityFilter implements Filter {
    private String indexPath;
    private String pages;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
        pages = fConfig.getInitParameter("PAGES");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String page = request.getParameter("page");
        if(page == null || pages.contains(page)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
