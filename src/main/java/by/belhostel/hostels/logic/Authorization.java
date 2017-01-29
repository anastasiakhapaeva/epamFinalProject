package by.belhostel.hostels.logic;

import by.belhostel.hostels.pool.ConnectionPool;
import by.belhostel.hostels.dao.MessageDAO;
import by.belhostel.hostels.dao.UserDAO;
import by.belhostel.hostels.dao.UserProfileDAO;
import by.belhostel.hostels.entity.Message;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.entity.UserProfile;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.pool.ProxyConnection;

import java.util.ArrayList;

/**
 * Created by Roman on 25.12.2016.
 */
public class Authorization {
    public static boolean login(String username, String password) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            result = userDAO.loginUser(username, password);
        } catch (DAOException e) {
            throw new LogicException("Cannot login user", e);
        } finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }

    public static User findCurrentUser(String login, String password) throws LogicException {
        User current;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            current = userDAO.findRegisteredUser(login, password);
        } catch (DAOException e) {
            throw new LogicException("Cannot get current user", e);
        } finally {
            userDAO.closeConnection(cn);
        }
        return current;
    }

    public static UserProfile findUserProfileById(int id) throws LogicException {
        UserProfile current;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserProfileDAO profileDAO = new UserProfileDAO(cn);
        try {
            current = profileDAO.findUserProfileById(id);
        } catch (DAOException e) {
            throw new LogicException("Cannot get user profile", e);
        } finally {
            profileDAO.closeConnection(cn);
        }
        return current;
    }

    public static ArrayList<Message> findMessagesForUser(int userId) throws LogicException {
        ArrayList<Message> messages;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        MessageDAO messageDAO = new MessageDAO(cn);
        try {
            messages = messageDAO.findMessagesByUserId(userId);
        } catch (DAOException e) {
            throw new LogicException("Cannot find messages for user", e);
        } finally {
            messageDAO.closeConnection(cn);
        }
        return messages;
    }
}
