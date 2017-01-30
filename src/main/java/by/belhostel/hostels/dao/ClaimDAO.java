package by.belhostel.hostels.dao;

import by.belhostel.hostels.entity.Claim;
import by.belhostel.hostels.entity.ClaimType;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.pool.ProxyConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Roman on 02.01.2017.
 */
public class ClaimDAO extends AbstractDAO<Claim> {

    /** The Constant SQL_SELECT_CLAIM_BY_ID. */
    private static final String SQL_SELECT_CLAIM_BY_ID = "SELECT * FROM Claim WHERE claim_id=?";

    /** The Constant SQL_SELECT_CLAIM_BY_IDS. */
    private static final String SQL_SELECT_CLAIM_BY_IDS = "SELECT * FROM Claim WHERE (is_deleted=0 AND is_confirmed=0) AND (user_id=? AND hostel_id=?)";

    /** The Constant SQL_SELECT_UNCONFIRMED_CLAIMS. */
    private static final String SQL_SELECT_UNCONFIRMED_CLAIMS = "SELECT * FROM Claim WHERE is_deleted=0 AND is_confirmed=0 AND claimtype='reservation'";

    /** The Constant SQL_SELECT_CLAIM_BY_HOSTEL_ID. */
    private static final String SQL_SELECT_CLAIM_BY_HOSTEL_ID = "SELECT * FROM Claim WHERE is_deleted=0 AND hostel_id=?";

    /** The Constant SQL_UPDATE_CLAIM_BY_IDS. */
    private static final String SQL_UPDATE_CLAIM_BY_IDS = "UPDATE Claim SET is_deleted=1 WHERE user_id=? AND hostel_id=? AND is_confirmed=0 AND is_deleted = 0";

    /** The Constant SQL_UPDATE_CLAIM_BY_ID. */
    private static final String SQL_UPDATE_CLAIM_BY_ID = "UPDATE Claim SET is_confirmed=1 WHERE claim_id=?";

    /** The Constant SQL_DELETE_CLAIM_BY_ID. */
    private static final String SQL_DELETE_CLAIM_BY_ID = "UPDATE Claim SET is_deleted=1 WHERE claim_id=?";

    /** The Constant SQL_INSERT_NEW_CLAIM. */
    private static final String SQL_INSERT_NEW_CLAIM =
            "INSERT INTO `Claim` (`claim_id`, `hostel_id`, `user_id`, `claimtype`, `required_places`, `date_in`, `date_out`, `is_confirmed`)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /** The Constant SQL_DELETE_ALL_CLAIMS_BY_ID. */
    private static final String SQL_DELETE_ALL_CLAIMS_BY_ID = "UPDATE Claim SET is_deleted=1 WHERE user_id=? AND is_confirmed=0 AND is_deleted=0";

    /**
     * Instantiates a new claim DAO.
     *
     * @param connection the connection
     */
    public ClaimDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Find claim by id.
     *
     * @param claimId the claim id
     * @return the claim
     * @throws DAOException the DAO exception
     */
    public Claim findClaimById(int claimId) throws DAOException {
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

    /**
     * Delete all claims for user.
     *
     * @param userId the user id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean deleteAllClaimsForUser(int userId) throws DAOException {
        int deleted = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_ALL_CLAIMS_BY_ID);
            ps.setInt(1, userId);
            deleted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return deleted > 0;
    }

    /**
     * Find claim by ids.
     *
     * @param userId the user id
     * @param hostelId the hostel id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean findClaimByIds(int userId, int hostelId) throws DAOException {
        boolean isBooked = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_IDS);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.isBeforeFirst()) {
                isBooked = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return isBooked;
    }

    /**
     * Cancel booking by ids.
     *
     * @param userId the user id
     * @param hostelId the hostel id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean cancelBookingByIds(int userId, int hostelId) throws DAOException {
        int canceled = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_CLAIM_BY_IDS);
            ps.setInt(1, userId);
            ps.setInt(2, hostelId);
            canceled = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return canceled > 0;
    }

    /**
     * Confirm claim by id.
     *
     * @param claimId the claim id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean confirmClaimById(int claimId) throws DAOException {
        int confirmed = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            confirmed = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return confirmed > 0;
    }

    /**
     * Delete claim by id.
     *
     * @param claimId the claim id
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public boolean deleteClaimById(int claimId) throws DAOException {
        int deleted = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_CLAIM_BY_ID);
            ps.setInt(1, claimId);
            deleted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return deleted > 0;
    }

    /**
     * Find claims by hostel id.
     *
     * @param hostelId the hostel id
     * @return the array list
     * @throws DAOException the DAO exception
     */
    public ArrayList<Claim> findClaimsByHostelId(int hostelId) throws DAOException {
        ArrayList<Claim> claims;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_CLAIM_BY_HOSTEL_ID);
            ps.setInt(1, hostelId);
            ResultSet resultSet = ps.executeQuery();
            claims = takeClaims(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return claims;
    }

    /**
     * Find unconfirmed claims.
     *
     * @return the array list
     * @throws DAOException the DAO exception
     */
    public ArrayList<Claim> findUnconfirmedClaims() throws DAOException {
        ArrayList<Claim> claims;
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_UNCONFIRMED_CLAIMS);
            claims = takeClaims(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
        }
        return claims;
    }

    public boolean create(Claim entity) throws DAOException {
        int flag = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_CLAIM, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, entity.getHostelId());
            ps.setInt(3, entity.getUserId());
            ps.setString(4, entity.getClaimType().toString().toLowerCase());
            ps.setInt(5, entity.getRequiredPlaces());
            ps.setDate(6, Date.valueOf(entity.getDateIn()));
            ps.setDate(7, Date.valueOf(entity.getDateOut()));
            ps.setBoolean(8, entity.isConfirmed());
            flag = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setClaimId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

    /**
     * Take claims.
     *
     * @param rs the rs
     * @return the array list
     * @throws DAOException the DAO exception
     */
    private ArrayList<Claim> takeClaims(ResultSet rs) throws DAOException {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return claims;
    }
}
