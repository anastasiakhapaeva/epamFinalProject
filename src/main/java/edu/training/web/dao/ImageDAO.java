package edu.training.web.dao;

import edu.training.web.entity.HostelImage;

import java.sql.*;
import java.util.List;

/**
 * Created by Roman on 11.01.2017.
 */
public class ImageDAO extends AbstractDAO<Integer, HostelImage> {
    private static final String SQL_INSERT_NEW_IMAGE = "INSERT INTO `Image` (`image_id`, `hostel_id`, `main_img`, `image_path`)" +
            "VALUES (?, ?, ?, ?)";
    public ImageDAO(Connection connection) {
        super(connection);
    }

    public List<HostelImage> findAll() {
        return null;
    }

    public boolean create(HostelImage entity) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_IMAGE, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, entity.getHostelId());
            ps.setBoolean(3, entity.isMain());
            ps.setString(4, entity.getFileName());
            ps.executeUpdate();
            flag = true;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setImageId(rs.getInt(1));
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
