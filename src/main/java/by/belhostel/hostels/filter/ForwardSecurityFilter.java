package by.belhostel.hostels.filter;

import by.belhostel.hostels.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 30.01.2017.
 */
@WebFilter(urlPatterns = {"/service"},
        initParams = {@WebInitParam(name = "USERS", value = "users"),
                @WebInitParam(name = "COMMAND", value = "go"),
                @WebInitParam(name = "CLAIMS", value = "claims"),
                @WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class ForwardSecurityFilter implements Filter {
    private static final String PARAM_CURRENT_USER = "currentUser";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_COMMAND = "command";
    private String indexPath;
    private String usersPage;
    private String claimsPage;
    private String command;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
        usersPage = fConfig.getInitParameter("USERS");
        claimsPage = fConfig.getInitParameter("CLAIMS");
        command = fConfig.getInitParameter("COMMAND");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        User currentUser = (User) session.getAttribute(PARAM_CURRENT_USER);
        if(currentUser == null || !currentUser.isAdmin()) {
            String currentCommand = request.getParameter(PARAM_COMMAND);
            if(currentCommand != null && command.equals(currentCommand)) {
                String page = request.getParameter(PARAM_PAGE);
                if (page == null || usersPage.equals(page) || claimsPage.equals(page)) {
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPath);
                    dispatcher.forward(request, response);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
