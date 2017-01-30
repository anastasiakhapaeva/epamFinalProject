package by.belhostel.hostels.logic;

import by.belhostel.hostels.command.PaginationControl;
import by.belhostel.hostels.dao.ClaimDAO;
import by.belhostel.hostels.dao.HostelDAO;
import by.belhostel.hostels.dao.MessageDAO;
import by.belhostel.hostels.dao.UserDAO;
import by.belhostel.hostels.entity.Claim;
import by.belhostel.hostels.entity.Hostel;
import by.belhostel.hostels.entity.Message;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.pool.ConnectionPool;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.exception.LogicException;
import by.belhostel.hostels.pool.ProxyConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Roman on 27.12.2016.
 */
public class HostelManager {

    /**
     * Find hostel by id.
     *
     * @param hostelId the hostel id
     * @return the hostel
     * @throws LogicException the logic exception
     */
    public static Hostel findHostelById(int hostelId) throws LogicException{
        Hostel hostel;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            hostel = hostelDAO.findHostelById(hostelId);
        }catch (DAOException e){
            throw new LogicException("Cannot find hostel by id", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return hostel;
    }

    /**
     * Find claim by id.
     *
     * @param claimId the claim id
     * @return the claim
     * @throws LogicException the logic exception
     */
    public static Claim findClaimById(int claimId) throws LogicException{
        Claim claim;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            claim = claimDAO.findClaimById(claimId);
        }catch (DAOException e){
            throw new LogicException("Cannot find claim by id", e);
        }finally{
            claimDAO.closeConnection(cn);
        }
        return claim;
    }

    /**
     * Find user by id.
     *
     * @param userId the user id
     * @return the user
     * @throws LogicException the logic exception
     */
    public static User findUserById(int userId) throws LogicException{
        User user;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            user = userDAO.findUserById(userId);
        }catch (DAOException e){
            throw new LogicException("Cannot find user by id", e);
        }finally{
            userDAO.closeConnection(cn);
        }
        return user;
    }

    /**
     * Find message by id.
     *
     * @param messageId the message id
     * @return the message
     * @throws LogicException the logic exception
     */
    public static Message findMessageById(int messageId) throws LogicException{
        Message message;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        MessageDAO messageDAO = new MessageDAO(cn);
        try {
            message = messageDAO.findMessageById(messageId);
        }catch (DAOException e){
            throw new LogicException("Cannot find message by id", e);
        }finally{
            messageDAO.closeConnection(cn);
        }
        return message;
    }


    /**
     * Find hostels by city.
     *
     * @param city the city
     * @param control the control
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<Hostel> findHostelsByCity(String city, PaginationControl control) throws LogicException{
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            hostels = hostelDAO.findHostelsByCity(city, control);
        }catch (DAOException e){
            throw new LogicException("Cannot find hostels by city", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return hostels;
    }

    /**
     * Find suitable hostels.
     *
     * @param dateIn the date in
     * @param dateOut the date out
     * @param city the city
     * @param places the places
     * @param control the control
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<Hostel> findSuitableHostels(String dateIn, String dateOut, String city, int places, PaginationControl control) throws LogicException{
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            hostels = hostelDAO.findSuitableHostels(dateIn, dateOut, city, places, control);
        }catch (DAOException e){
            throw new LogicException("Cannot find hostels by city", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return hostels;
    }

    /**
     * Find all hostels.
     *
     * @param control the control
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<Hostel> findAllHostels(PaginationControl control) throws LogicException{
        ArrayList<Hostel> hostels;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            hostels = (ArrayList<Hostel>) hostelDAO.findAllHostels(control);
        }catch (DAOException e){
            throw new LogicException("Cannot find all hostels", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return hostels;
    }

    /**
     * Load main image for hostel.
     *
     * @param id the id
     * @return the string
     * @throws LogicException the logic exception
     */
    public static String loadMainImageForHostel(int id) throws LogicException{
        String imgPath;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            imgPath = hostelDAO.loadImageForHostelById(id);
        }catch (DAOException e){
            throw new LogicException("Cannot load main image for hostel", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return imgPath;
    }

    /**
     * Load all images for hostel.
     *
     * @param id the id
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<String> loadAllImagesForHostel(int id) throws LogicException{
        ArrayList<String> imgPaths;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            imgPaths = hostelDAO.loadAllImagesForHostelById(id);
        }catch (DAOException e){
            throw new LogicException("Cannot load images for hostel", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return imgPaths;
    }

    /**
     * Book hostel by claim.
     *
     * @param claim the claim
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean bookHostelByClaim(Claim claim) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = claimDAO.create(claim);
            cn.commit();
        }catch (SQLException | DAOException e){
            claimDAO.rollbackConnection(cn);
            throw new LogicException("Cannot book hostel", e);
        }finally{
            claimDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Delete message by id.
     *
     * @param messageId the message id
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean deleteMessageById(int messageId) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        MessageDAO messageDAO = new MessageDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = messageDAO.deleteMessageById(messageId);
            cn.commit();
        }catch (SQLException | DAOException e){
            messageDAO.rollbackConnection(cn);
            throw new LogicException("Cannot delete message", e);
        }finally{
            messageDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Send message to user.
     *
     * @param message the message
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean sendMessageToUser(Message message) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        MessageDAO messageDAO = new MessageDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = messageDAO.create(message);
            cn.commit();
        }catch (SQLException | DAOException e){
            messageDAO.rollbackConnection(cn);
            throw new LogicException("Cannot book hostel", e);
        }finally{
            messageDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Count free places.
     *
     * @param hostelId the hostel id
     * @param dateIn the date in
     * @param dateOut the date out
     * @return the integer
     * @throws LogicException the logic exception
     */
    public static Integer countFreePlaces(int hostelId, LocalDate dateIn, LocalDate dateOut) throws LogicException{
        int freePlaces = 0;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            Hostel current = hostelDAO.findHostelById(hostelId);
            freePlaces = current.getFreePlaces();
            ArrayList<Claim> claims = claimDAO.findClaimsByHostelId(hostelId);
            for(Claim claim : claims){
                if(dateIn.compareTo(claim.getDateIn())>=0 && dateIn.compareTo(claim.getDateOut()) <=0){
                    freePlaces-=claim.getRequiredPlaces();
                }else if(dateIn.compareTo(claim.getDateIn())<0 && dateOut.compareTo(claim.getDateIn())>=0){
                    freePlaces-=claim.getRequiredPlaces();
                }
            }

        }catch (DAOException e){
            throw new LogicException("Cannot count free places", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return freePlaces;
    }

    /**
     * Cancel booking by ids.
     *
     * @param userId the user id
     * @param hostelId the hostel id
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean cancelBookingByIds(int userId, int hostelId) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = claimDAO.cancelBookingByIds(userId, hostelId);
            cn.commit();
        }catch (SQLException | DAOException e){
            claimDAO.rollbackConnection(cn);
            throw new LogicException("Cannot cancel booking", e);
        }finally{
            claimDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Deposit money for user.
     *
     * @param userId the user id
     * @param amount the amount
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean depositMoneyForUser(int userId, double amount) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.updateMoneyForUser(userId, amount);
            cn.commit();
        }catch (SQLException | DAOException e){
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot deposit money", e);
        }finally{
            userDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Checks if is booked hostel by user.
     *
     * @param userId the user id
     * @param hostelId the hostel id
     * @return true, if is booked hostel by user
     * @throws LogicException the logic exception
     */
    public static boolean isBookedHostelByUser(int userId, int hostelId) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = claimDAO.findClaimByIds(userId, hostelId);
            cn.commit();
        }catch (SQLException | DAOException e){
            claimDAO.rollbackConnection(cn);
            throw new LogicException("Cannot deposit money", e);
        }finally{
            claimDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Find booked hostels for user.
     *
     * @param userId the user id
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<Hostel> findBookedHostelsForUser(int userId) throws LogicException{
        ArrayList<Hostel> bookedHostels;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            bookedHostels = hostelDAO.findBookedHostelsForUser(userId);
        } catch (DAOException e){
            throw new LogicException("Cannot find booked hostels for user", e);
        }finally {
            hostelDAO.closeConnection(cn);
        }
        return bookedHostels;
    }

    /**
     * Find paid hostels for user.
     *
     * @param userId the user id
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<Hostel> findPaidHostelsForUser(int userId) throws LogicException{
        ArrayList<Hostel> paidHostels;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            paidHostels = hostelDAO.findPaidHostelsForUser(userId);
        }catch (DAOException e){
            throw new LogicException("Cannot find paid hostel for user", e);
        }finally {
            hostelDAO.closeConnection(cn);
        }
        return paidHostels;
    }

    /**
     * Pay for hostel.
     *
     * @param userId the user id
     * @param amount the amount
     * @return true, if successful
     * @throws LogicException the logic exception
     */
    public static boolean payForHostel(int userId, double amount) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.updateMoneyForUser(userId, -1*amount);
            cn.commit();
        } catch (SQLException | DAOException e){
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot pay hostel", e);
        }finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }

    /**
     * Find unconfirmed claims.
     *
     * @return the array list
     * @throws LogicException the logic exception
     */
    public static ArrayList<Claim> findUnconfirmedClaims() throws LogicException{
        ArrayList<Claim> unconfirmed = new ArrayList<Claim>();
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            unconfirmed = claimDAO.findUnconfirmedClaims();
        }catch (DAOException e){
            throw new LogicException("Cannot find unconfirmed claims", e);
        } finally {
            claimDAO.closeConnection(cn);
        }
        return unconfirmed;
    }
}
