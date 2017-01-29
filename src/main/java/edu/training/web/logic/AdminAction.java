package edu.training.web.logic;

import edu.training.web.dao.*;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.HostelImage;
import edu.training.web.entity.User;
import edu.training.web.exception.DAOException;
import edu.training.web.exception.LogicException;
import edu.training.web.pool.ConnectionPool;
import edu.training.web.pool.ProxyConnection;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Roman on 07.01.2017.
 */
public class AdminAction {
    public static boolean confirmClaimById(int claimId) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = claimDAO.confirmClaimById(claimId);
            cn.commit();
        } catch (SQLException | DAOException e) {
            claimDAO.rollbackConnection(cn);
            throw new LogicException("Cannot confirm claim", e);
        } finally {
            claimDAO.closeConnection(cn);
        }
        return result;
    }

    public static ArrayList<User> findAllUsers() throws LogicException {
        ArrayList<User> users = new ArrayList<User>();
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        UserProfileDAO profileDAO = new UserProfileDAO(cn);
        try {
            users = userDAO.findAllUsers();
            for (User user : users) {
                user.setProfile(profileDAO.findUserProfileById(user.getUserId()));
            }
        } catch (DAOException e) {
            throw new LogicException("Cannot find hostels by city", e);
        } finally {
            userDAO.closeConnection(cn);
        }
        return users;
    }

    public static boolean deleteClaimById(int claimId) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = claimDAO.deleteClaimById(claimId);
            cn.commit();
        } catch (SQLException | DAOException e) {
            claimDAO.rollbackConnection(cn);
            throw new LogicException("Cannot delete claim", e);
        } finally {
            claimDAO.closeConnection(cn);
        }
        return result;
    }

    public static boolean banUserById(int userId, boolean ban) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.banUserById(userId, ban);
            claimDAO.deleteAllClaimsForUser(userId);
            cn.commit();
        } catch (SQLException | DAOException e) {
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot ban user", e);
        } finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }

    public static boolean setDiscountForUser(int userId, double discount) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.setDiscountForUser(userId, discount);
            cn.commit();
        } catch (SQLException | DAOException e) {
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot set discount", e);
        } finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }

    public static boolean editHostel(Hostel editedHostel) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = hostelDAO.editHostel(editedHostel);
            cn.commit();
        } catch (SQLException | DAOException e) {
            hostelDAO.rollbackConnection(cn);
            throw new LogicException("Cannot edit hostel", e);
        } finally {
            hostelDAO.closeConnection(cn);
        }
        return result;
    }

    public static boolean addHostel(Hostel hostel) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = hostelDAO.create(hostel);
            cn.commit();
        } catch (SQLException | DAOException e) {
            hostelDAO.rollbackConnection(cn);
            throw new LogicException("Cannot add hostel", e);
        } finally {
            hostelDAO.closeConnection(cn);
        }
        return result;
    }

    public static boolean addHostelImage(HostelImage img) throws LogicException {
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ImageDAO imageDAO = new ImageDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = imageDAO.create(img);
            cn.commit();
        } catch (SQLException | DAOException e) {
            imageDAO.rollbackConnection(cn);
            throw new LogicException("Cannot add hostel image", e);
        } finally {
            imageDAO.closeConnection(cn);
        }
        return result;
    }
}
