package edu.training.web.dao;

import edu.training.web.entity.Entity;
import edu.training.web.exception.DAOException;
import edu.training.web.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Roman on 07.12.2016.
 */
public abstract class AbstractDAO<T extends Entity> {
    protected static final Logger LOG = LogManager.getLogger();
    ProxyConnection connection;

    AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public abstract boolean create(T entity) throws DAOException;

    public void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            LOG.error("Cannot close statement: ", e);
        }
    }

    public void closeConnection(ProxyConnection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            LOG.error("Cannot return connection to pool: ", e);
        }
    }

    public void rollbackConnection(ProxyConnection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            LOG.error("Cannot rollback connection: ", e);
        }
    }
}
