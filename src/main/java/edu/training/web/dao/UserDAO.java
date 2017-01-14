package edu.training.web.dao;

import edu.training.web.converter.MD5Converter;
import edu.training.web.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 07.12.2016.
 */
public class UserDAO extends AbstractDAO<Integer, User> {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM User";
    private static final String SQL_INSERT_NEW_USER =
            "INSERT INTO `User` (`user_id`, `username`, `password`, `money`, `is_admin`, `discount`, `is_banned`)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM User WHERE username=?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM User WHERE user_id=?";
    private static final String SQL_LOGIN_USER = "SELECT * FROM User WHERE username=? AND password=?";
    private static final String SQL_UPDATE_MONEY_USER = "UPDATE User SET money=money+? WHERE user_id=?";
    private static final String SQL_UPDATE_BAN_USER = "UPDATE User SET `is_banned` = ? WHERE user_id=?";
    private static final String SQL_UPDATE_DISCOUNT_USER = "UPDATE User SET `discount` = ? WHERE user_id=?";
    private MD5Converter converter = new MD5Converter();


    public UserDAO(Connection connection) {
        super(connection);
    }

    public boolean updateMoney(int userId, double amount) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_MONEY_USER);
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return flag;
    }

    public boolean banUser(int userId, boolean ban) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_BAN_USER);
            ps.setBoolean(1, ban);
            ps.setInt(2, userId);
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return ban;
    }

    public boolean setDiscount(int userId, double discount) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_DISCOUNT_USER);
            ps.setDouble(1, discount);
            ps.setInt(2, userId);
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return flag;
    }

    public boolean findUserByLogin(String login) {
        boolean isFounded = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME);
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.isBeforeFirst() ) {
                isFounded = true;
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return isFounded;
    }

    public User findUserById(int userId) {
        PreparedStatement ps = null;
        User user = new User();
        try {
            ps = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setMoney(resultSet.getDouble("money"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setBanned(resultSet.getBoolean("is_banned"));
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return user;
    }

    public User findRegisteredUser(String login, String password) {
        User current = new User();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_LOGIN_USER);
            ps.setString(1, login);
            ps.setString(2, converter.convert(password));
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                current.setUserId(resultSet.getInt("user_id"));
                current.setUsername(resultSet.getString("username"));
                current.setPassword(resultSet.getString("password"));
                current.setMoney(resultSet.getDouble("money"));
                current.setAdmin(resultSet.getBoolean("is_admin"));
                current.setDiscount(resultSet.getDouble("discount"));
                current.setBanned(resultSet.getBoolean("is_banned"));
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return current;
    }

    public boolean loginUser(String username, String password) {
        boolean login = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_LOGIN_USER);
            ps.setString(1, username);
            ps.setString(2, converter.convert(password));
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.isBeforeFirst() ) {
                login = true;
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return login;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setMoney(resultSet.getDouble("money"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setDiscount(resultSet.getDouble("discount"));
                user.setBanned(resultSet.getBoolean("is_banned"));
                users.add(user);
            }
            if(resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(st);
        }
        return users;

    }

    @Override
    public boolean create(User entity) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setString(2, entity.getUsername());
            ps.setString(3, converter.convert(entity.getPassword()));
            ps.setDouble(4, entity.getMoney());
            ps.setBoolean(5, entity.isAdmin());
            ps.setDouble(6, entity.getDiscount());
            ps.setBoolean(7, entity.isBanned());
            ps.executeUpdate();
            flag = true;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setUserId(rs.getInt(1));
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
