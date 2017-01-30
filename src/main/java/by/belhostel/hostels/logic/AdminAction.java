package by.belhostel.hostels.logic;

import by.belhostel.hostels.dao.*;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.entity.HostelImage;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.pool.ConnectionPool;
import by.belhostel.hostels.pool.ProxyConnection;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Roman on 07.01.2017.
 */
public class AdminAction {

    /**
     * Confirm claim by id.
     *
     * @param claimId the claim id
     * @return true, if successful
     * @throws LogicException the logic exception
     */
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

    /**
     * Find all users.
     *
     * @return ArrayList of users
     * @throws LogicException the logic exception
     */

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

    /**
     * Delete claim by id.
     *
     * @param claimId the claim id
     * @return true, if successful
     * @throws LogicException the logic exception
     */

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

    /**
     * Ban user by id.
     *
     * @param userId the user id
     * @param ban the user ban status
     * @return ban status
     * @throws LogicException the logic exception
     */

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

    /**
     * Set discount for user.
     *
     * @param userId the user id
     * @param discount the user discount
     * @return true, if successful
     * @throws LogicException the logic exception
     */

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

    /**
     * Edit hostel.
     *
     * @param editedHostel the edited hostel
     * @return true, if successful
     * @throws LogicException the logic exception
     */

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

    /**
     * Add hostel.
     *
     * @param hostel the new hostel
     * @return true, if successful
     * @throws LogicException the logic exception
     */

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

    /**
     * Add hostel image.
     *
     * @param img the hostel image
     * @return true, if successful
     * @throws LogicException the logic exception
     */

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
