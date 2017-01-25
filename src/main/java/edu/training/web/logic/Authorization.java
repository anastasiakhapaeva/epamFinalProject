package edu.training.web.logic;

import edu.training.web.exception.DAOException;
import edu.training.web.exception.LogicException;
import edu.training.web.pool.ConnectionPool;
import edu.training.web.dao.MessageDAO;
import edu.training.web.dao.UserDAO;
import edu.training.web.dao.UserProfileDAO;
import edu.training.web.entity.Message;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import edu.training.web.pool.ProxyConnection;
import java.util.ArrayList;

/**
 * Created by Roman on 25.12.2016.
 */
public class Authorization {
    public static boolean login(String username, String password) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            result = userDAO.loginUser(username, password);
        }catch (DAOException e){
            throw new LogicException("Cannot login user", e);
        }finally{
            userDAO.closeConnection(cn);
        }
        return result;
    }
    public static User getCurrentUser(String login, String password) throws LogicException{
        User current;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            current = userDAO.findRegisteredUser(login, password);
        }catch (DAOException e){
            throw new LogicException("Cannot get current user", e);
        }finally{
            userDAO.closeConnection(cn);
        }
        return current;
    }
    public static UserProfile getUserProfile(int id) throws LogicException{
        UserProfile current;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserProfileDAO profileDAO = new UserProfileDAO(cn);
        try {
            current = profileDAO.findUserProfile(id);
        }catch (DAOException e){
            throw new LogicException("Cannot get user profile", e);
        }finally{
            profileDAO.closeConnection(cn);
        }
        return current;
    }

    public static ArrayList<Message> findMessagesForUser(int userId) throws LogicException{
        ArrayList<Message> messages;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        MessageDAO messageDAO = new MessageDAO(cn);
        try {
            messages = messageDAO.findMessagesByUserId(userId);
        }catch (DAOException e){
            throw new LogicException("Cannot find messages for user", e);
        } finally {
            messageDAO.closeConnection(cn);
        }
        return messages;
    }
}
