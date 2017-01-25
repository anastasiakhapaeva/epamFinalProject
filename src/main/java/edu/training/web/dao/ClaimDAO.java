package edu.training.web.dao;

import edu.training.web.entity.Claim;
import edu.training.web.entity.ClaimType;
import edu.training.web.exception.DAOException;
import edu.training.web.pool.ProxyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 02.01.2017.
 */
public class ClaimDAO extends AbstractDAO<Claim> {
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
    public ClaimDAO(ProxyConnection connection) {
        super(connection);
    }

    public Claim findClaimById(int claimId) throws DAOException{
        Claim claim;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            ResultSet resultSet = ps.executeQuery();
            claim = takeClaims(resultSet).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return claim;
    }

    public boolean cancelBooking(int userId, int hostelId) throws DAOException {
        boolean isCanceled = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_CLAIM_BY_IDS);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            ps.executeUpdate();
            isCanceled = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return isCanceled;
    }

    public boolean confirmClaim(int claimId) throws DAOException{
        boolean isConfirmed = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            ps.executeUpdate();
            isConfirmed = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return isConfirmed;
    }

    public boolean deleteClaim(int claimId) throws DAOException{
        boolean isDeleted = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            ps.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return isDeleted;
    }

    public ArrayList<Claim> findClaimsByHostelId(int hostelId) throws DAOException{
        ArrayList<Claim> claims;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_HOSTEL_ID);
            ps.setInt(1, hostelId);
            ResultSet resultSet = ps.executeQuery();
            claims = takeClaims(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(ps);
        }
        return claims;
    }

    public ArrayList<Claim> findUnconfirmedClaims() throws DAOException{
        ArrayList<Claim> claims;
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_UNCONFIRMED_CLAIMS);
            claims = takeClaims(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            closeStatement(st);
        }
        return claims;
    }

    public List<Claim> findAll() {
        return null;
    }

    public boolean create(Claim entity) throws DAOException{
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
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag;
    }

    private ArrayList<Claim> takeClaims(ResultSet rs) throws DAOException{
        ArrayList<Claim> claims = new ArrayList<>();
        try {
            while (rs.next()) {
                Claim claim = new Claim();
                claim.setClaimId(rs.getInt("claim_id"));
                claim.setHostelId(rs.getInt("hostel_id"));
                claim.setUserId(rs.getInt("user_id"));
                claim.setClaimType(ClaimType.valueOf(rs.getString("claimtype").toUpperCase()));
                claim.setRequiredPlaces(rs.getInt("required_places"));
                claim.setDateIn(rs.getDate("date_in").toLocalDate());
                claim.setDateOut(rs.getDate("date_out").toLocalDate());
                claim.setConfirmed(rs.getBoolean("is_confirmed"));
                claim.setDeleted(rs.getBoolean("is_deleted"));
                claims.add(claim);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return claims;
    }
}
