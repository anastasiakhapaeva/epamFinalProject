package edu.training.web.logic;

import edu.training.web.connector.ConnectionPool;
import edu.training.web.dao.HostelDAO;
import edu.training.web.dao.MessageDAO;
import edu.training.web.dao.UserDAO;
import edu.training.web.dao.UserProfileDAO;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.Message;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Roman on 25.12.2016.
 */
public class Authorization {
    private static final Logger LOG = LogManager.getLogger();
    public static boolean login(String username, String password){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(cn);
            result = userDAO.loginUser(username, password);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
    public static User getCurrentUser(String login, String password){
        Connection cn = null;
        User current = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(cn);
            current = userDAO.findRegisteredUser(login, password);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return current;
    }
    public static UserProfile getUserProfile(int id){
        Connection cn = null;
        UserProfile current = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            UserProfileDAO profileDAO = new UserProfileDAO(cn);
            current = profileDAO.findUserProfile(id);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return current;
    }

    public static ArrayList<Message> findMessagesForUser(int userId) {
        Connection cn = null;
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            MessageDAO messageDAO = new MessageDAO(cn);
            messages = messageDAO.findMessagesByUserId(userId);
        } finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return messages;
    }
}
