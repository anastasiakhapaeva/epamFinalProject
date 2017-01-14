package edu.training.web.logic;

import edu.training.web.connector.ConnectionPool;
import edu.training.web.dao.UserDAO;
import edu.training.web.dao.UserProfileDAO;
import edu.training.web.entity.User;
import edu.training.web.entity.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Roman on 08.12.2016.
 */
public class Registration {
    private static final Logger LOG = LogManager.getLogger();
    public static boolean register(User user, UserProfile userProfile){
        boolean result = false;
        Connection cn = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserDAO dao = new UserDAO(cn);
            result = dao.create(user);
            userProfile.setUserId(user.getUserId());
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){

            LOG.error(e);
        }finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
    public static boolean createUserProfile(UserProfile userProfile){
        boolean result = false;
        Connection cn = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserProfileDAO dao = new UserProfileDAO(cn);
            result = dao.create(userProfile);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
    public static boolean checkLoginExistance(String login){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(cn);
            result = userDAO.findUserByLogin(login);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
}
