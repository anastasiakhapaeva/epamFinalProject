package edu.training.web.logic;

import edu.training.web.exception.DAOException;
import edu.training.web.exception.LogicException;
import edu.training.web.pool.ConnectionPool;
import edu.training.web.dao.UserDAO;
import edu.training.web.dao.UserProfileDAO;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import edu.training.web.pool.ProxyConnection;

import java.sql.SQLException;

/**
 * Created by Roman on 08.12.2016.
 */
public class Registration {
    public static boolean register(User user, UserProfile userProfile) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.create(user);
            userProfile.setUserId(user.getUserId());
            cn.commit();
        }catch (SQLException | DAOException e){
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot register user", e);
        }finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }
    public static boolean createUserProfile(UserProfile userProfile) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserProfileDAO profileDAO = new UserProfileDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = profileDAO.create(userProfile);
            cn.commit();
        }catch (SQLException | DAOException e){
            profileDAO.rollbackConnection(cn);
            throw new LogicException("Cannot create user profile", e);
        }finally {
            profileDAO.closeConnection(cn);
        }
        return result;
    }
    public static boolean checkLoginExistence(String login) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            result = userDAO.findUserByLogin(login);
        }catch (DAOException e){
            throw new LogicException("Cannot check login existence", e);
        }finally{
            userDAO.closeConnection(cn);
        }
        return result;
    }
}
