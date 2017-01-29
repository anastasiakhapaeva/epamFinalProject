package edu.training.web.dao;


import edu.training.web.entity.UserProfile;
import edu.training.web.exception.DAOException;
import edu.training.web.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Roman on 25.12.2016.
 */
public class UserProfileDAO extends AbstractDAO<UserProfile> {
    private static final String SQL_INSERT_NEW_USERPROFILE =
            "INSERT INTO `UserProfile` (`user_id`, `firstname`, `lastname`, `phone`, `city`, `email`)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PROFILE = "SELECT * FROM UserProfile WHERE user_id=?";

    public UserProfileDAO(ProxyConnection connection) {
        super(connection);
    }

    public UserProfile findUserProfileById(int id) throws DAOException {
        UserProfile userProfile;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_PROFILE);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            userProfile = takeProfiles(resultSet).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return userProfile;
    }

    @Override
    public boolean create(UserProfile entity) throws DAOException {
        int flag = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_USERPROFILE);
            ps.setInt(1, entity.getUserId());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getPhone());
            ps.setString(5, entity.getCity());
            ps.setString(6, entity.getEmail());
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

    private ArrayList<UserProfile> takeProfiles(ResultSet rs) throws DAOException {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        try {
            while (rs.next()) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(rs.getInt("user_id"));
                userProfile.setFirstName(rs.getString("firstname"));
                userProfile.setLastName(rs.getString("lastname"));
                userProfile.setPhone(rs.getString("phone"));
                userProfile.setCity(rs.getString("city"));
                userProfile.setEmail(rs.getString("email"));
                userProfiles.add(userProfile);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return userProfiles;
    }
}
