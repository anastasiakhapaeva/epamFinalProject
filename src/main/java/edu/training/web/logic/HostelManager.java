package edu.training.web.logic;

import edu.training.web.connector.ConnectionPool;
import edu.training.web.dao.*;
import edu.training.web.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Roman on 27.12.2016.
 */
public class HostelManager {
    private static final Logger LOG = LogManager.getLogger();
    public static Hostel findHostelById(int hostelId){
        Connection cn = null;
        Hostel hostel = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            hostel = hostelDAO.findHostelById(hostelId);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return hostel;
    }

    public static Claim findClaimById(int claimId){
        Connection cn = null;
        Claim claim = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            ClaimDAO claimDAO = new ClaimDAO(cn);
            claim = claimDAO.findClaimById(claimId);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return claim;
    }

    public static User findUserById(int userId){
        Connection cn = null;
        User user = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(cn);
            user = userDAO.findUserById(userId);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return user;
    }

    public static Message findMessageById(int messageId){
        Connection cn = null;
        Message message = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            MessageDAO messageDAO = new MessageDAO(cn);
            message = messageDAO.findMessageById(messageId);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return message;
    }


    public static ArrayList<Hostel> findHostelsByCity(String city){
        Connection cn = null;
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            hostels = hostelDAO.findHostelsByCity(city);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return hostels;
    }

    public static ArrayList<Hostel> findAllHostels(){
        Connection cn = null;
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            hostels = (ArrayList<Hostel>) hostelDAO.findAll();
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return hostels;
    }

    public static String loadMainImageForHostel(int id){
        Connection cn = null;
        String imgPath = "";
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            imgPath = hostelDAO.loadImageForHostel(id);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return imgPath;
    }
    public static ArrayList<String> loadAllImagesForHostel(int id){
        Connection cn = null;
        ArrayList<String> imgPathes = new ArrayList<String>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            imgPathes = hostelDAO.loadAllImagesForHostel(id);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return imgPathes;
    }
    public static boolean bookHostel(Claim claim){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            ClaimDAO claimDao = new ClaimDAO(cn);
            result = claimDao.create(claim);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{

            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean deleteMessage(int messageId){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            MessageDAO messageDao = new MessageDAO(cn);
            result = messageDao.deleteMessage(messageId);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean sendMessageToUser(Message message){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            MessageDAO messageDAO = new MessageDAO(cn);
            result = messageDAO.create(message);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{

            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static Integer countFreePlaces(int hostelId, LocalDate dateIn, LocalDate dateOut){
        Connection cn = null;
        int freePlaces = 0;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            ClaimDAO claimDao = new ClaimDAO(cn);
            HostelDAO hostelDao = new HostelDAO(cn);
            Hostel current = hostelDao.findHostelById(hostelId);
            freePlaces = current.getFreePlaces();
            ArrayList<Claim> claims = claimDao.findClaimsByHostelId(hostelId);
            for(Claim claim : claims){
                if(dateIn.compareTo(claim.getDateIn())>=0 && dateIn.compareTo(claim.getDateOut()) <=0){
                    freePlaces-=claim.getRequiredPlaces();
                }else if(dateIn.compareTo(claim.getDateIn())<0 && dateOut.compareTo(claim.getDateIn())>=0){
                    freePlaces-=claim.getRequiredPlaces();
                }
            }

        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return freePlaces;
    }
    public static Claim findClaimByIds(int userId, int hostelId){
        Connection cn = null;
        Claim claim = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            ClaimDAO claimDao = new ClaimDAO(cn);
            claim = claimDao.findClaimByIds(userId, hostelId);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return claim;
    }



    public static boolean cancelBooking(int userId, int hostelId){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            ClaimDAO claimDao = new ClaimDAO(cn);
            result = claimDao.cancelBooking(userId, hostelId);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean depositMoney(int userId, double amount){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserDAO userDao = new UserDAO(cn);
            result = userDao.updateMoney(userId, amount);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static ArrayList<Hostel> findBookedHostelsForUser(int userId) {
        Connection cn = null;
        ArrayList<Hostel> bookedHostels = new ArrayList<Hostel>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            bookedHostels = hostelDAO.findBookedHostels(userId);
        } finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return bookedHostels;
    }

    public static ArrayList<Hostel> findPaidHostelsForUser(int userId) {
        Connection cn = null;
        ArrayList<Hostel> paidHostels = new ArrayList<Hostel>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            HostelDAO hostelDAO = new HostelDAO(cn);
            paidHostels = hostelDAO.findPaidHostels(userId);
        } finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return paidHostels;
    }

    public static boolean paymentForHostel(int userId, double amount) {
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserDAO userDAO = new UserDAO(cn);
            result = userDAO.updateMoney(userId, -1*amount);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        } catch (SQLException e){
            LOG.error(e);
        }finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static ArrayList<Claim> findUnconfirmedClaims(){
        Connection cn = null;
        ArrayList<Claim> unconfirmed = new ArrayList<Claim>();
        try {
            cn = ConnectionPool.getInstance().getConnection();
            ClaimDAO claimDAO = new ClaimDAO(cn);
            unconfirmed = claimDAO.findUnconfirmedClaims();
        } finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return unconfirmed;
    }

    public static boolean returnMoney(int userId, double amount) {
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserDAO userDAO = new UserDAO(cn);
            result = userDAO.updateMoney(userId, amount);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        } catch (SQLException e){
            LOG.error(e);
        }finally {
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
}
