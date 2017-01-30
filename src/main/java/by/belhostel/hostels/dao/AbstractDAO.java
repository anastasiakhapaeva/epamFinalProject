package by.belhostel.hostels.dao;

import by.belhostel.hostels.entity.Entity;
import by.belhostel.hostels.exception.DAOException;
import by.belhostel.hostels.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Roman on 07.12.2016.
 *
 * @param <T> the generic type
 */
public abstract class AbstractDAO<T extends Entity> {

    /** The Constant LOG. */
    protected static final Logger LOG = LogManager.getLogger();

    /** The connection. */
    ProxyConnection connection;

    /**
     * Instantiates a new abstract DAO.
     *
     * @param connection the connection
     */
    AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    /**
     * Creates the.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean create(T entity) throws DAOException;

    /**
     * Close statement.
     *
     * @param st the st
     */
    public void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            LOG.error("Cannot close statement: ", e);
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     */
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

    /**
     * Rollback connection.
     *
     * @param connection the connection
     */
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
