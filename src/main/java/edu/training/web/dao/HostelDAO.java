package edu.training.web.dao;

import edu.training.web.command.PaginationControl;
import edu.training.web.entity.Hostel;
import edu.training.web.exception.DAOException;
import edu.training.web.pool.ProxyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 27.12.2016.
 */
public class HostelDAO extends AbstractDAO<Hostel> {
    private static final String SQL_SELECT_ALL_HOSTELS = "SELECT SQL_CALC_FOUND_ROWS * FROM Hostel LIMIT ?, ?";
    private static final String SQL_SELECT_HOSTELS_BY_CITY = "SELECT SQL_CALC_FOUND_ROWS * FROM Hostel WHERE city=?";
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

    private static final String SQL_SELECT_SUITABLE_HOSTELS = "select * from Hostel where `hostel`.`city` = ? and " +
            "(`hostel`.`free_places` - ifnull((select sum(`claim`.`required_places`) from Claim where `claim`.`hostel_id` = `hostel`.`hostel_id` " +
            "AND `claim`.`is_deleted` = 0 AND ((DATE(?) between `claim`.`date_in` AND `claim`.`date_out`) OR (DATE(?) < `claim`.`date_in` AND DATE(?) >= `claim`.`date_in`))), 0))>=?";
    private static final String IMAGE_PATH = "/web/images/";

    public HostelDAO(ProxyConnection connection) {
        super(connection);
    }


    public boolean editHostel(Hostel editedHostel) throws DAOException{
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
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return isEdited;
    }

    public String loadImageForHostel(int id) throws DAOException{
        String path = new String();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTEL_MAIN_IMG);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            path = resultSet.getString("image_path");
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return IMAGE_PATH + path;
    }

    public ArrayList<String> loadAllImagesForHostel(int id) throws DAOException{
        ArrayList<String> imgPathes = new ArrayList<String>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTEL_ALL_IMG);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                imgPathes.add(IMAGE_PATH + resultSet.getString("image_path"));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return imgPathes;
    }

    public ArrayList<Hostel> findHostelsByCity(String city, PaginationControl control) throws DAOException{
        ArrayList<Hostel> hostels;
        PreparedStatement ps = null;
        int offset = (control.getCurrentPage()-1)*control.getRecordsPerPage();
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTELS_BY_CITY + " LIMIT " + offset +", " + control.getRecordsPerPage());
            ps.setString(1, city);
            ResultSet resultSet = ps.executeQuery();
            hostels = takeHostels(resultSet);
            resultSet.close();
            resultSet = ps.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()) {
                int numOfRecords = resultSet.getInt(1);
                control.setNumOfRecords(numOfRecords);
                control.setNumOfPages((int) Math.ceil(numOfRecords * 1.0 / control.getRecordsPerPage()));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return hostels;
    }

    public Hostel findHostelById(int hostelId) throws DAOException {
        Hostel hostel;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_HOSTEL_BY_ID);
            ps.setInt(1, hostelId);
            ResultSet resultSet = ps.executeQuery();
            hostel = takeHostels(resultSet).get(0);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return hostel;
    }

    public List<Hostel> findAll() throws DAOException{
        List<Hostel> hostels;
//        Statement st = null;
//        try {
//            st = connection.createStatement();
//            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_HOSTELS);
//            hostels = takeHostels(resultSet);
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        } finally {
//            closeStatement(st);
//        }
//        return hostels;
        return null;
    }

    public List<Hostel> findAll(PaginationControl control) throws DAOException{
        List<Hostel> hostels;
        Statement st = null;
        int offset = (control.getCurrentPage()-1)*control.getRecordsPerPage();
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT SQL_CALC_FOUND_ROWS * FROM Hostel LIMIT " + offset +", " + control.getRecordsPerPage());
            hostels = takeHostels(resultSet);
            resultSet.close();
            resultSet = st.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()) {
                int numOfRecords = resultSet.getInt(1);
                control.setNumOfRecords(numOfRecords);
                control.setNumOfPages((int) Math.ceil(numOfRecords * 1.0 / control.getRecordsPerPage()));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(st);
        }
        return hostels;
    }

    public ArrayList<Hostel> findSuitableHostels(String dateIn, String dateOut, String city, int places, PaginationControl control) throws DAOException{
        ArrayList<Hostel> hostels;
        PreparedStatement ps = null;
        int offset = (control.getCurrentPage()-1)*control.getRecordsPerPage();
        try {
            ps = connection.prepareStatement(SQL_SELECT_SUITABLE_HOSTELS + " LIMIT " + offset +", " + control.getRecordsPerPage());
            ps.setString(1, city);
            ps.setString(2, dateIn);
            ps.setString(3, dateIn);
            ps.setString(4, dateOut);
            ps.setInt(5, places);
            ResultSet resultSet = ps.executeQuery();
            hostels = takeHostels(resultSet);
            resultSet.close();
            resultSet = ps.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()) {
                int numOfRecords = resultSet.getInt(1);
                control.setNumOfRecords(numOfRecords);
                control.setNumOfPages((int) Math.ceil(numOfRecords * 1.0 / control.getRecordsPerPage()));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return hostels;
    }


    public ArrayList<Hostel> findBookedHostels(int userId) throws DAOException {
        ArrayList<Hostel> hostels;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_BOOKED_HOSTELS);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            hostels = takeHostels(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return hostels;
    }

    public ArrayList<Hostel> findPaidHostels(int userId) throws DAOException{
        ArrayList<Hostel> hostels;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SELECT_PAID_HOSTELS);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            hostels = takeHostels(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return hostels;
    }

    public boolean create(Hostel entity) throws DAOException {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(ps);
        }
        return flag;
    }

    private ArrayList<Hostel> takeHostels(ResultSet rs) throws DAOException{
        ArrayList<Hostel> hostels = new ArrayList<>();
        try {
            while (rs.next()) {
                Hostel hostel = new Hostel();
                hostel.setHostelId(rs.getInt("hostel_id"));
                hostel.setName(rs.getString("name"));
                hostel.setCity(rs.getString("city"));
                hostel.setAddress(rs.getString("address"));
                hostel.setPhone(rs.getString("phone"));
                hostel.setDescription(rs.getString("description"));
                hostel.setPrice(rs.getDouble("price"));
                hostel.setFreePlaces(rs.getInt("free_places"));
                hostel.setDeleted(rs.getBoolean("is_deleted"));
                hostels.add(hostel);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return hostels;
    }
}
