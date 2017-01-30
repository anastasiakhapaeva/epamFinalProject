package by.belhostel.hostels.listener;

import by.belhostel.hostels.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Roman on 07.01.2017.
 */
@WebListener
public class UserSessions implements HttpSessionListener {

    /** The active sessions. */
    private static Map<String, HttpSession> activeSessions = new ConcurrentHashMap<String, HttpSession>();

    /** The Constant PARAM_CURRENT_USER. */
    private static final String PARAM_CURRENT_USER = "currentUser";

    /**
     * Session created.
     *
     * @param httpSessionEvent the http session event
     */
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        activeSessions.put(session.getId(), session);
    }

    /**
     * Session destroyed.
     *
     * @param httpSessionEvent the http session event
     */
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        activeSessions.remove(session.getId());
    }

    /**
     * Gets the user sessions.
     *
     * @param userId the user id
     * @return the user sessions
     */
    public static List<HttpSession> getUserSessions(int userId){
        List<HttpSession> sessions = new CopyOnWriteArrayList<HttpSession>();
        for(HttpSession session : activeSessions.values()){
            User user = (User) session.getAttribute(PARAM_CURRENT_USER);
            if(user == null) {
                continue;
            }else if(user.getUserId()==userId){
                sessions.add(session);
            }
        }
        return sessions;
    }

    /**
     * Gets the admins sessions.
     *
     * @return the admins sessions
     */
    public static List<HttpSession> getAdminsSessions(){
        List<HttpSession> sessions = new CopyOnWriteArrayList<HttpSession>();
        for(HttpSession session : activeSessions.values()){
            User user = (User) session.getAttribute(PARAM_CURRENT_USER);
            if(user == null) {
                continue;
            }else if(user.isAdmin()){
                sessions.add(session);
            }
        }
        return sessions;
    }

    /**
     * Gets the all sessions.
     *
     * @return the all sessions
     */
    public static Collection<HttpSession> getAllSessions(){
        return activeSessions.values();
    }
}
