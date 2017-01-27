package edu.training.web.dao;

import edu.training.web.entity.HostelImage;
import edu.training.web.exception.DAOException;
import edu.training.web.pool.ProxyConnection;

import java.sql.*;
import java.util.List;

/**
 * Created by Roman on 11.01.2017.
 */
public class ImageDAO extends AbstractDAO<HostelImage> {
    private static final String SQL_INSERT_NEW_IMAGE = "INSERT INTO `Image` (`image_id`, `hostel_id`, `main_img`, `image_path`)" +
            "VALUES (?, ?, ?, ?)";
    public ImageDAO(ProxyConnection connection) {
        super(connection);
    }

    public List<HostelImage> findAll() {
        return null;
    }

    public boolean create(HostelImage entity) throws DAOException{
        int flag = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_IMAGE, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, entity.getHostelId());
            ps.setBoolean(3, entity.isMain());
            ps.setString(4, entity.getFileName());
            flag = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setImageId(rs.getInt(1));
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag > 0;
    }

}
