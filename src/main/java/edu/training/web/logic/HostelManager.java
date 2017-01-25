package edu.training.web.logic;

import edu.training.web.exception.DAOException;
import edu.training.web.exception.LogicException;
import edu.training.web.pool.ConnectionPool;
import edu.training.web.dao.*;
import edu.training.web.entity.*;
import edu.training.web.pool.ProxyConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Roman on 27.12.2016.
 */
public class HostelManager {
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


    public static ArrayList<Hostel> findHostelsByCity(String city) throws LogicException{
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            hostels = hostelDAO.findHostelsByCity(city);
        }catch (DAOException e){
            throw new LogicException("Cannot find hostels by city", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return hostels;
    }

    public static ArrayList<Hostel> findAllHostels() throws LogicException{
        ArrayList<Hostel> hostels;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            hostels = (ArrayList<Hostel>) hostelDAO.findAll();
        }catch (DAOException e){
            throw new LogicException("Cannot find all hostels", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return hostels;
    }

    public static String loadMainImageForHostel(int id) throws LogicException{
        String imgPath;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            imgPath = hostelDAO.loadImageForHostel(id);
        }catch (DAOException e){
            throw new LogicException("Cannot load main image for hostel", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return imgPath;
    }
    public static ArrayList<String> loadAllImagesForHostel(int id) throws LogicException{
        ArrayList<String> imgPathes;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            imgPathes = hostelDAO.loadAllImagesForHostel(id);
        }catch (DAOException e){
            throw new LogicException("Cannot load images for hostel", e);
        }finally{
            hostelDAO.closeConnection(cn);
        }
        return imgPathes;
    }
    public static boolean bookHostel(Claim claim) throws LogicException{
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

    public static boolean deleteMessage(int messageId) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        MessageDAO messageDAO = new MessageDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = messageDAO.deleteMessage(messageId);
            cn.commit();
        }catch (SQLException | DAOException e){
            messageDAO.rollbackConnection(cn);
            throw new LogicException("Cannot delete message", e);
        }finally{
            messageDAO.closeConnection(cn);
        }
        return result;
    }

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

    public static boolean cancelBooking(int userId, int hostelId) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        ClaimDAO claimDAO = new ClaimDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = claimDAO.cancelBooking(userId, hostelId);
            cn.commit();
        }catch (SQLException | DAOException e){
            claimDAO.rollbackConnection(cn);
            throw new LogicException("Cannot cancel booking", e);
        }finally{
            claimDAO.closeConnection(cn);
        }
        return result;
    }

    public static boolean depositMoney(int userId, double amount) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.updateMoney(userId, amount);
            cn.commit();
        }catch (SQLException | DAOException e){
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot deposit money", e);
        }finally{
            userDAO.closeConnection(cn);
        }
        return result;
    }

    public static ArrayList<Hostel> findBookedHostelsForUser(int userId) throws LogicException{
        ArrayList<Hostel> bookedHostels;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            bookedHostels = hostelDAO.findBookedHostels(userId);
        } catch (DAOException e){
            throw new LogicException("Cannot find booked hostels for user", e);
        }finally {
            hostelDAO.closeConnection(cn);
        }
        return bookedHostels;
    }

    public static ArrayList<Hostel> findPaidHostelsForUser(int userId) throws LogicException{
        ArrayList<Hostel> paidHostels;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        HostelDAO hostelDAO = new HostelDAO(cn);
        try {
            paidHostels = hostelDAO.findPaidHostels(userId);
        }catch (DAOException e){
            throw new LogicException("Cannot find paid hostel for user", e);
        }finally {
            hostelDAO.closeConnection(cn);
        }
        return paidHostels;
    }

    public static boolean paymentForHostel(int userId, double amount) throws LogicException{
        boolean result = false;
        ProxyConnection cn = ConnectionPool.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(cn);
        try {
            cn.setAutoCommit(false);
            result = userDAO.updateMoney(userId, -1*amount);
            cn.commit();
        } catch (SQLException | DAOException e){
            userDAO.rollbackConnection(cn);
            throw new LogicException("Cannot pay hostel", e);
        }finally {
            userDAO.closeConnection(cn);
        }
        return result;
    }

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
