package edu.training.web.dao;


import edu.training.web.entity.UserProfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 25.12.2016.
 */
public class UserProfileDAO extends AbstractDAO<Integer, UserProfile> {
    private static final String SQL_SELECT_ALL_USERPROFILES = "SELECT * FROM UserProfile";
    private static final String SQL_INSERT_NEW_USERPROFILE =
            "INSERT INTO `UserProfile` (`user_id`, `firstname`, `lastname`, `phone`, `city`, `email`)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_PROFILE = "SELECT * FROM UserProfile WHERE user_id=?";

    public UserProfileDAO(Connection connection) {
        super(connection);
    }

    public UserProfile findUserProfile(int id) {
        UserProfile userProfile = new UserProfile();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_PROFILE);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            userProfile.setUserId(resultSet.getInt("user_id"));
            userProfile.setFirstName(resultSet.getString("firstname"));
            userProfile.setLastName(resultSet.getString("lastname"));
            userProfile.setPhone(resultSet.getString("phone"));
            userProfile.setCity(resultSet.getString("city"));
            userProfile.setEmail(resultSet.getString("email"));
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return userProfile;
    }

    @Override
    public List<UserProfile> findAll() {
        List<UserProfile> userProfiles = new ArrayList<UserProfile>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERPROFILES);
            while (resultSet.next()) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(resultSet.getInt("user_id"));
                userProfile.setFirstName(resultSet.getString("firstname"));
                userProfile.setLastName(resultSet.getString("lastname"));
                userProfile.setPhone(resultSet.getString("phone"));
                userProfile.setCity(resultSet.getString("city"));
                userProfile.setEmail(resultSet.getString("email"));
                userProfiles.add(userProfile);
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(st);
        }
        return userProfiles;
    }

    @Override
    public boolean create(UserProfile entity) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_USERPROFILE);
            ps.setInt(1, entity.getUserId());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getPhone());
            ps.setString(5, entity.getCity());
            ps.setString(6, entity.getEmail());
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOG.error(e);
            flag = false;
        } finally {
            close(ps);
        }
        return flag;
    }
}
