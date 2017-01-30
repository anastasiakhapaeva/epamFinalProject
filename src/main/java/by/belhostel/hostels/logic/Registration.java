package by.belhostel.hostels.logic;

import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.pool.ConnectionPool;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.dao.UserDAO;
import by.belhostel.hostels.dao.UserProfileDAO;
import by.belhostel.hostels.pool.ProxyConnection;

import java.sql.SQLException;

/**
 * Created by Roman on 08.12.2016.
 */
public class Registration {

    /**
     * Register.
     *
     * @param user the user
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean register(User user) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        UserProfileDAO profileDAO = new UserProfileDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.create(user);
            user.getProfile().setUserId(user.getUserId());
            result = result && profileDAO.create(user.getProfile());
            cn.commit();
        }catch (SQLException | DAOException e){
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot register user", e);
        }finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Checks login existence.
     *
     * @param login the user login
     * @return true, if successful
     * @throws LogicException the logic exception
     */
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
