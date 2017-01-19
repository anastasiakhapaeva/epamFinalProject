package edu.training.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Roman on 25.11.2016.
 */
public class AjaxLanguageCommand implements ActionCommand {
//    private static final String PARAM_LANG = "lang";
    private static final String PARAM_LOCALE = "locale";
    private static final String DEFAULT_LOCALE = "ru_RU";
//    private static final String PARAM_PAGE = "page";
//    private static final String PARAM_INDEX = "path.page.index";
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter(PARAM_LOCALE);
        HttpSession session = request.getSession(true);

        if(lang.isEmpty()){
//            session.setAttribute(PARAM_LANG, DEFAULT_LOCALE);
            session.setAttribute(PARAM_LOCALE, DEFAULT_LOCALE);
        }else{
//            session.setAttribute(PARAM_LANG, lang);
            session.setAttribute(PARAM_LOCALE, lang);
        }

        try{
            response.getWriter().write(Boolean.toString(true));
        }catch (IOException e){
            LOG.error(e);
        }
//        String currPage = (String) session.getAttribute(PARAM_PAGE);

//        if(currPage == null){
//            currPage = PARAM_INDEX;
//        }
        return "";
    }
}
