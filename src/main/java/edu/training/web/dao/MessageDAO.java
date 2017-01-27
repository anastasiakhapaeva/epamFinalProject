package edu.training.web.dao;

import edu.training.web.entity.Message;
import edu.training.web.exception.DAOException;
import edu.training.web.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 08.01.2017.
 */
public class MessageDAO extends AbstractDAO<Message> {
    private static final Logger LOG = LogManager.getLogger();
    private static final String SQL_SELECT_MESSAGE_BY_ID = "SELECT * FROM Message WHERE message_id=?";
    private static final String SQL_DELETE_MESSAGE_BY_ID = "UPDATE Message SET is_deleted=1 WHERE message_id=?";
    private static final String SQL_SELECT_MESSAGES_BY_ID="SELECT * FROM Message WHERE user_id=? and is_deleted=0";
    private static final String SQL_INSERT_NEW_MESSAGE =
            "INSERT INTO `Message` (`message_id`, `user_id`, `sender`, `text`, `is_deleted`) VALUES (?, ?, ?, ?, ?)";

    public MessageDAO(ProxyConnection connection) {
        super(connection);
    }

    public Message findMessageById(int messageId) throws DAOException{
        Message message;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_MESSAGE_BY_ID);
            ps.setInt(1, messageId);
            ResultSet resultSet = ps.executeQuery();
            message = takeMessages(resultSet).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return message;
    }

    public boolean deleteMessage(int messageId) throws DAOException{
        int deleted = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_MESSAGE_BY_ID);
            ps.setInt(1, messageId);
            deleted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return deleted > 0;
    }

    public ArrayList<Message> findMessagesByUserId(int userId) throws DAOException{
        ArrayList<Message> messages;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_MESSAGES_BY_ID);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            messages = takeMessages(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return messages;
    }
    public List<Message> findAll() {
        return null;
    }

    public boolean create(Message entity) throws DAOException{
        int flag = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_MESSAGE, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, entity.getUserId());
            ps.setString(3, entity.getSender());
            ps.setString(4, entity.getText());
            ps.setBoolean(5, entity.isDeleted());
            flag = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setMessageId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

    private ArrayList<Message> takeMessages(ResultSet rs) throws DAOException{
        ArrayList<Message> messages = new ArrayList<>();
        try {
            while (rs.next()) {
                Message message = new Message();
                message.setMessageId(rs.getInt("message_id"));
                message.setUserId(rs.getInt("user_id"));
                message.setSender(rs.getString("sender"));
                message.setText(rs.getString("text"));
                message.setDeleted(rs.getBoolean("is_deleted"));
                messages.add(message);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return messages;
    }
}
