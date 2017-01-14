package edu.training.web.dao;

import edu.training.web.entity.Claim;
import edu.training.web.entity.ClaimType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 02.01.2017.
 */
public class ClaimDAO extends AbstractDAO<Integer, Claim> {

    private static final String SQL_SELECT_CLAIM_BY_ID = "SELECT * FROM Claim WHERE claim_id=?";
    private static final String SQL_SELECT_CLAIM_BY_IDS = "SELECT * FROM Claim WHERE (is_deleted=0 AND is_confirmed=0) AND (user_id=? AND hostel_id=?)";
    private static final String SQL_SELECT_UNCONFIRMED_CLAIMS = "SELECT * FROM Claim WHERE is_deleted=0 AND is_confirmed=0 AND claimtype='reservation'";
    private static final String SQL_SELECT_CLAIM_BY_HOSTEL_ID = "SELECT * FROM Claim WHERE is_deleted=0 AND hostel_id=?";
    private static final String SQL_UPDATE_CLAIM_BY_IDS = "UPDATE Claim SET is_deleted=1 WHERE user_id=? AND hostel_id=?";
    private static final String SQL_UPDATE_CLAIM_BY_ID = "UPDATE Claim SET is_confirmed=1 WHERE claim_id=?";
    private static final String SQL_DELETE_CLAIM_BY_ID = "UPDATE Claim SET is_deleted=1 WHERE claim_id=?";
    private static final String SQL_INSERT_NEW_CLAIM =
            "INSERT INTO `Claim` (`claim_id`, `hostel_id`, `user_id`, `claimtype`, `required_places`, `date_in`, `date_out`, `is_confirmed`)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public ClaimDAO(Connection connection) {
        super(connection);
    }

    public Claim findClaimByIds(int userId, int hostelId) {
        PreparedStatement ps = null;
        Claim claim = new Claim();
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_IDS);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                claim.setClaimId(resultSet.getInt("claim_id"));
                claim.setHostelId(resultSet.getInt("hostel_id"));
                claim.setUserId(resultSet.getInt("user_id"));
                claim.setClaimType(ClaimType.RESERVATION);
                claim.setRequiredPlaces(resultSet.getInt("required_places"));
                claim.setDateIn(resultSet.getDate("date_in").toLocalDate());
                claim.setDateOut(resultSet.getDate("date_out").toLocalDate());
                claim.setDeleted(resultSet.getBoolean("is_deleted"));
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return claim;
    }

    public Claim findClaimById(int claimId) {
        PreparedStatement ps = null;
        Claim claim = new Claim();
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                claim.setClaimId(resultSet.getInt("claim_id"));
                claim.setHostelId(resultSet.getInt("hostel_id"));
                claim.setUserId(resultSet.getInt("user_id"));
                claim.setClaimType(ClaimType.valueOf(resultSet.getString("claimtype").toUpperCase()));
                claim.setRequiredPlaces(resultSet.getInt("required_places"));
                claim.setDateIn(resultSet.getDate("date_in").toLocalDate());
                claim.setDateOut(resultSet.getDate("date_out").toLocalDate());
                claim.setConfirmed(resultSet.getBoolean("is_confirmed"));
                claim.setDeleted(resultSet.getBoolean("is_deleted"));
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return claim;
    }

    public boolean cancelBooking(int userId, int hostelId) {
        boolean isCanceled = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_CLAIM_BY_IDS);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            ps.executeUpdate();
            isCanceled = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return isCanceled;
    }

    public boolean confirmClaim(int claimId) {
        boolean isConfirmed = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            ps.executeUpdate();
            isConfirmed = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return isConfirmed;
    }

    public boolean deleteClaim(int claimId) {
        boolean isDeleted = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            ps.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return isDeleted;
    }

    public ArrayList<Claim> findClaimsByHostelId(int hostelId){
        ArrayList<Claim> claims = new ArrayList<Claim>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_HOSTEL_ID);
            ps.setInt(1, hostelId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setClaimId(resultSet.getInt("claim_id"));
                claim.setHostelId(resultSet.getInt("hostel_id"));
                claim.setUserId(resultSet.getInt("user_id"));
                claim.setClaimType(ClaimType.RESERVATION);
                claim.setRequiredPlaces(resultSet.getInt("required_places"));
                claim.setDateIn(resultSet.getDate("date_in").toLocalDate());
                claim.setDateOut(resultSet.getDate("date_out").toLocalDate());
                claim.setDeleted(resultSet.getBoolean("is_deleted"));
                claims.add(claim);
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        }finally {
            close(ps);
        }
        return claims;
    }

    public ArrayList<Claim> findUnconfirmedClaims(){
        ArrayList<Claim> claims = new ArrayList<Claim>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_UNCONFIRMED_CLAIMS);
            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setClaimId(resultSet.getInt("claim_id"));
                claim.setHostelId(resultSet.getInt("hostel_id"));
                claim.setUserId(resultSet.getInt("user_id"));
                claim.setClaimType(ClaimType.valueOf(resultSet.getString("claimtype").toUpperCase()));
                claim.setRequiredPlaces(resultSet.getInt("required_places"));
                claim.setDateIn(resultSet.getDate("date_in").toLocalDate());
                claim.setDateOut(resultSet.getDate("date_out").toLocalDate());
                claim.setConfirmed(resultSet.getBoolean("is_confirmed"));
                claim.setDeleted(resultSet.getBoolean("is_deleted"));
                claims.add(claim);
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        }finally {
            close(st);
        }
        return claims;
    }

    public List<Claim> findAll() {
        return null;
    }

    public boolean create(Claim entity) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_CLAIM, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, entity.getHostelId());
            ps.setInt(3, entity.getUserId());
            ps.setString(4, entity.getClaimType().toString().toLowerCase());
            ps.setInt(5, entity.getRequiredPlaces());
            ps.setDate(6, Date.valueOf(entity.getDateIn()));
            ps.setDate(7,Date.valueOf(entity.getDateOut()));
            ps.setBoolean(8, entity.isConfirmed());
            ps.executeUpdate();
            flag = true;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setClaimId(rs.getInt(1));
            if(rs != null){
                rs.close();
            }
        } catch (SQLException e) {
            LOG.error(e);

        } finally {
            close(ps);
        }
        return flag;
    }
}
