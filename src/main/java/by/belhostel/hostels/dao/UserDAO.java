package by.belhostel.hostels.dao;

import by.belhostel.hostels.converter.MD5Converter;
import by.belhostel.hostels.entity.User;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.pool.ProxyConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Roman on 07.12.2016.
 */
public class UserDAO extends AbstractDAO<User> {
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


    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    public boolean updateMoneyForUser(int userId, double amount) throws DAOException {
        int flag = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_MONEY_USER);
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

    public boolean banUserById(int userId, boolean ban) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_BAN_USER);
            ps.setBoolean(1, ban);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return ban;
    }

    public boolean setDiscountForUser(int userId, double discount) throws DAOException {
        int flag = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_DISCOUNT_USER);
            ps.setDouble(1, discount);
            ps.setInt(2, userId);
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

    public boolean findUserByLogin(String login) throws DAOException {
        boolean isFounded = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME);
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.isBeforeFirst()) {
                isFounded = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return isFounded;
    }

    public User findUserById(int userId) throws DAOException {
        PreparedStatement ps = null;
        User user;
        try {
            ps = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            user = takeUsers(resultSet).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return user;
    }

    public User findRegisteredUser(String login, String password) throws DAOException {
        User current;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_LOGIN_USER);
            ps.setString(1, login);
            ps.setString(2, converter.convert(password));
            ResultSet resultSet = ps.executeQuery();
            current = takeUsers(resultSet).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return current;
    }

    public boolean loginUser(String username, String password) throws DAOException {
        boolean login = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_LOGIN_USER);
            ps.setString(1, username);
            ps.setString(2, converter.convert(password));
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.isBeforeFirst()) {
                login = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return login;
    }

    public ArrayList<User> findAllUsers() throws DAOException {
        ArrayList<User> users;
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_USERS);
            users = takeUsers(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
        }
        return users;

    }

    @Override
    public boolean create(User entity) throws DAOException {
        int flag = 0;
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
            flag = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setUserId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

    private ArrayList<User> takeUsers(ResultSet rs) throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setMoney(rs.getDouble("money"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setDiscount(rs.getDouble("discount"));
                user.setBanned(rs.getBoolean("is_banned"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }
}
