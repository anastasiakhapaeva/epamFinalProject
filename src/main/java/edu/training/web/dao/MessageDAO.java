package edu.training.web.dao;

import edu.training.web.entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 08.01.2017.
 */
public class MessageDAO extends AbstractDAO<Integer, Message> {
    private static final Logger LOG = LogManager.getLogger();
    private static final String SQL_SELECT_MESSAGE_BY_ID = "SELECT * FROM Message WHERE message_id=?";
    private static final String SQL_DELETE_MESSAGE_BY_ID = "UPDATE Message SET is_deleted=1 WHERE message_id=?";
    private static final String SQL_SELECT_MESSAGES_BY_ID="SELECT * FROM Message WHERE user_id=? and is_deleted=0";
    private static final String SQL_INSERT_NEW_MESSAGE =
            "INSERT INTO `Message` (`message_id`, `user_id`, `sender`, `text`, `is_deleted`) VALUES (?, ?, ?, ?, ?)";

    public MessageDAO(Connection connection) {
        super(connection);
    }

    public Message findMessageById(int messageId) {
        Message message = new Message();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_MESSAGE_BY_ID);
            ps.setInt(1, messageId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                message.setMessageId(resultSet.getInt("message_id"));
                message.setUserId(resultSet.getInt("user_id"));
                message.setSender(resultSet.getString("sender"));
                message.setText(resultSet.getString("text"));
                message.setDeleted(resultSet.getBoolean("is_deleted"));
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return message;
    }

    public boolean deleteMessage(int messageId) {
        boolean isDeleted = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_MESSAGE_BY_ID);
            ps.setInt(1, messageId);
            ps.executeUpdate();
            isDeleted = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return isDeleted;
    }

    public ArrayList<Message> findMessagesByUserId(int userId) {
        ArrayList<Message> messages = new ArrayList<Message>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_MESSAGES_BY_ID);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setMessageId(resultSet.getInt("message_id"));
                message.setUserId(resultSet.getInt("user_id"));
                message.setSender(resultSet.getString("sender"));
                message.setText(resultSet.getString("text"));
                message.setDeleted(resultSet.getBoolean("is_deleted"));
                messages.add(message);
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return messages;
    }
    public List<Message> findAll() {
        return null;
    }

    public boolean create(Message entity) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_MESSAGE, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, entity.getUserId());
            ps.setString(3, entity.getSender());
            ps.setString(4, entity.getText());
            ps.setBoolean(5, entity.isDeleted());
            ps.executeUpdate();
            flag = true;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setMessageId(rs.getInt(1));
        } catch (SQLException e) {
            LOG.error(e);
            flag = false;
        } finally {
            close(ps);
        }
        return flag;
    }
}
