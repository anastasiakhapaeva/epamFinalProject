package edu.training.web.logic;

import edu.training.web.connector.ConnectionPool;
import edu.training.web.dao.ClaimDAO;
import edu.training.web.dao.HostelDAO;
import edu.training.web.dao.ImageDAO;
import edu.training.web.dao.UserDAO;
import edu.training.web.entity.Hostel;
import edu.training.web.entity.HostelImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Roman on 07.01.2017.
 */
public class AdminAction {
    private static final Logger LOG = LogManager.getLogger();
    public static boolean confirmClaim(int claimId){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            ClaimDAO claimDao = new ClaimDAO(cn);
            result = claimDao.confirmClaim(claimId);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean deleteClaim(int claimId){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            ClaimDAO claimDao = new ClaimDAO(cn);
            result = claimDao.deleteClaim(claimId);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean banUser(int userId, boolean ban){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserDAO userDao = new UserDAO(cn);
            result = userDao.banUser(userId, ban);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean setDiscount(int userId, double discount){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            UserDAO userDao = new UserDAO(cn);
            result = userDao.setDiscount(userId, discount);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean editHostel(Hostel editedHostel){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            HostelDAO hostelDao = new HostelDAO(cn);
            result = hostelDao.editHostel(editedHostel);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{
            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }

    public static boolean addHostel(Hostel hostel){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            HostelDAO hostelDao = new HostelDAO(cn);
            result = hostelDao.create(hostel);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{

            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
    public static boolean addHostelImage(HostelImage img){
        Connection cn = null;
        boolean result = false;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            boolean autoCommit = cn.getAutoCommit();
            cn.setAutoCommit(false);
            ImageDAO imageDao = new ImageDAO(cn);
            result = imageDao.create(img);
            cn.commit();
            cn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            LOG.error(e);
        }finally{

            ConnectionPool.getInstance().putConnection(cn);
        }
        return result;
    }
}
