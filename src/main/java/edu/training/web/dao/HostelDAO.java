package edu.training.web.dao;

import edu.training.web.entity.Hostel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 27.12.2016.
 */
public class HostelDAO extends AbstractDAO<Integer, Hostel> {
    private static final String SQL_SELECT_ALL_HOSTELS = "SELECT * FROM Hostel";
    private static final String SQL_SELECT_HOSTELS_BY_CITY = "SELECT * FROM Hostel WHERE city=?";
    private static final String SQL_SELECT_HOSTEL_BY_ID = "SELECT * FROM Hostel WHERE hostel_id=?";
    private static final String SQL_SELECT_HOSTEL_MAIN_IMG = "SELECT image_path FROM Image WHERE hostel_id=? AND main_img=1";
    private static final String SQL_SELECT_HOSTEL_ALL_IMG = "SELECT image_path FROM Image WHERE hostel_id=?";
    private static final String SQL_SELECT_BOOKED_HOSTELS = "SELECT `hostel`.`hostel_id`, `hostel`.`name`, `hostel`.`free_places`," +
            "`hostel`.`price`, `hostel`.`phone`, `hostel`.`city`, `hostel`.`address`," +
            "`hostel`.`description`, `hostel`.`is_deleted`" + " FROM Claim inner join Hostel on `claim`.`hostel_id`=`hostel`.`hostel_id`" +
            " where user_id=? AND `claim`.`is_deleted`=0 AND `claim`.`claimtype`='reservation' AND `claim`.`is_confirmed`=0";
    private static final String SQL_SELECT_PAID_HOSTELS = "SELECT `hostel`.`hostel_id`, `hostel`.`name`, `hostel`.`free_places`," +
            "`hostel`.`price`, `hostel`.`phone`, `hostel`.`city`, `hostel`.`address`," +
            "`hostel`.`description`, `hostel`.`is_deleted`" + " FROM Claim inner join Hostel on `claim`.`hostel_id`=`hostel`.`hostel_id`" +
            " where user_id=? AND `claim`.`is_deleted`=0 AND `claim`.`claimtype`='payment'";
    private static final String SQL_UPDATE_HOSTEL = "UPDATE Hostel SET `name`=?, `free_places`=?, `price`=?, `phone`=?, `city`=?, "+
            "`address`=?, `description`=? WHERE `hostel_id`=?";
    private static final String SQL_INSERT_NEW_HOSTEL =
            "INSERT INTO `Hostel` (`hostel_id`, `name`, `free_places`, `price`, `phone`, `city`, `address`, `description`, `is_deleted`)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String IMAGE_PATH = "/web/images/";

    public HostelDAO(Connection connection) {
        super(connection);
    }


    public boolean editHostel(Hostel editedHostel) {
        boolean isEdited = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_UPDATE_HOSTEL);
            ps.setString(1, editedHostel.getName());
            ps.setInt(2, editedHostel.getFreePlaces());
            ps.setDouble(3, editedHostel.getPrice());
            ps.setString(4, editedHostel.getPhone());
            ps.setString(5, editedHostel.getCity());
            ps.setString(6, editedHostel.getAddress());
            ps.setString(7, editedHostel.getDescription());
            ps.setInt(8, editedHostel.getHostelId());
            ps.executeUpdate();
            isEdited = true;
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return isEdited;
    }

    public String loadImageForHostel(int id) {
        String path = new String();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTEL_MAIN_IMG);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            path = resultSet.getString("image_path");
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return IMAGE_PATH + path;
    }

    public ArrayList<String> loadAllImagesForHostel(int id) {
        ArrayList<String> imgPathes = new ArrayList<String>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTEL_ALL_IMG);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                imgPathes.add(IMAGE_PATH + resultSet.getString("image_path"));
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return imgPathes;
    }

    public ArrayList<Hostel> findHostelsByCity(String city) {
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTELS_BY_CITY);
            ps.setString(1, city);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Hostel hostel = new Hostel();
                hostel.setHostelId(resultSet.getInt("hostel_id"));
                hostel.setName(resultSet.getString("name"));
                hostel.setCity(resultSet.getString("city"));
                hostel.setAddress(resultSet.getString("address"));
                hostel.setPhone(resultSet.getString("phone"));
                hostel.setDescription(resultSet.getString("description"));
                hostel.setPrice(resultSet.getDouble("price"));
                hostel.setFreePlaces(resultSet.getInt("free_places"));
                hostel.setDeleted(resultSet.getBoolean("is_deleted"));
                hostels.add(hostel);
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return hostels;
    }

    public Hostel findHostelById(int hostelId) {
        Hostel hostel = new Hostel();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTEL_BY_ID);
            ps.setInt(1, hostelId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                hostel.setHostelId(resultSet.getInt("hostel_id"));
                hostel.setName(resultSet.getString("name"));
                hostel.setCity(resultSet.getString("city"));
                hostel.setAddress(resultSet.getString("address"));
                hostel.setPhone(resultSet.getString("phone"));
                hostel.setDescription(resultSet.getString("description"));
                hostel.setPrice(resultSet.getDouble("price"));
                hostel.setFreePlaces(resultSet.getInt("free_places"));
                hostel.setDeleted(resultSet.getBoolean("is_deleted"));
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return hostel;
    }

    public List<Hostel> findAll() {
        List<Hostel> hostels = new ArrayList<Hostel>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_HOSTELS);
            while (resultSet.next()) {
                Hostel hostel = new Hostel();
                hostel.setHostelId(resultSet.getInt("hostel_id"));
                hostel.setName(resultSet.getString("name"));
                hostel.setCity(resultSet.getString("city"));
                hostel.setAddress(resultSet.getString("address"));
                hostel.setPhone(resultSet.getString("phone"));
                hostel.setDescription(resultSet.getString("description"));
                hostel.setPrice(resultSet.getDouble("price"));
                hostel.setFreePlaces(resultSet.getInt("free_places"));
                hostel.setDeleted(resultSet.getBoolean("is_deleted"));
                hostels.add(hostel);
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(st);
        }
        return hostels;
    }

    public ArrayList<Hostel> findBookedHostels(int userId) {
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_BOOKED_HOSTELS);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Hostel hostel = new Hostel();
                hostel.setHostelId(resultSet.getInt("hostel_id"));
                hostel.setName(resultSet.getString("name"));
                hostel.setCity(resultSet.getString("city"));
                hostel.setAddress(resultSet.getString("address"));
                hostel.setPhone(resultSet.getString("phone"));
                hostel.setDescription(resultSet.getString("description"));
                hostel.setPrice(resultSet.getDouble("price"));
                hostel.setFreePlaces(resultSet.getInt("free_places"));
                hostel.setDeleted(resultSet.getBoolean("is_deleted"));
                hostels.add(hostel);
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return hostels;
    }

    public ArrayList<Hostel> findPaidHostels(int userId) {
        ArrayList<Hostel> hostels = new ArrayList<Hostel>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_PAID_HOSTELS);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Hostel hostel = new Hostel();
                hostel.setHostelId(resultSet.getInt("hostel_id"));
                hostel.setName(resultSet.getString("name"));
                hostel.setCity(resultSet.getString("city"));
                hostel.setAddress(resultSet.getString("address"));
                hostel.setPhone(resultSet.getString("phone"));
                hostel.setDescription(resultSet.getString("description"));
                hostel.setPrice(resultSet.getDouble("price"));
                hostel.setFreePlaces(resultSet.getInt("free_places"));
                hostel.setDeleted(resultSet.getBoolean("is_deleted"));
                hostels.add(hostel);
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            close(ps);
        }
        return hostels;
    }

    public boolean create(Hostel entity) {
        boolean flag = false;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_INSERT_NEW_HOSTEL, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setString(2, entity.getName());
            ps.setInt(3, entity.getFreePlaces());
            ps.setDouble(4, entity.getPrice());
            ps.setString(5, entity.getPhone());
            ps.setString(6, entity.getCity());
            ps.setString(7, entity.getAddress());
            ps.setString(8, entity.getDescription());
            ps.setBoolean(9, entity.isDeleted());
            ps.executeUpdate();
            flag = true;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            entity.setHostelId(rs.getInt(1));
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
