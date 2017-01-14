package edu.training.web.dao;

import edu.training.web.entity.Entity;
import edu.training.web.entity.Hostel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Roman on 07.12.2016.
 */
public abstract class AbstractDAO <K, T extends Entity> {
    protected static final Logger LOG = LogManager.getLogger();
    protected Connection connection;
    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll();
    public abstract boolean create(T entity);
    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

}
