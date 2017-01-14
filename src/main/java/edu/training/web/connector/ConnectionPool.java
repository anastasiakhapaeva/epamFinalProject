package edu.training.web.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Roman on 07.12.2016.
 */
public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private static final int MAX_CONNECTIONS_AMOUNT = 5;
    private BlockingQueue<Connection> connections = new ArrayBlockingQueue<Connection>(MAX_CONNECTIONS_AMOUNT);

    private ConnectionPool() throws SQLException {
        for (int i = 0; i < MAX_CONNECTIONS_AMOUNT; i++) {
            connections.add(ConnectorDB.getConnection());
        }
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
                isCreated.getAndSet(true);
            } catch (SQLException e) {
                LOG.fatal(e);
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        }
            return instance;

    }

    public Connection getConnection() {

        try {
            return connections.take();
        } catch (InterruptedException e) {
            LOG.error(e);
            return null; // ???
        }

    }

    public void putConnection(Connection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

    public void closePool() {
        if (instance != null) {
            for (int i = 0; i < connections.size(); i++) {
                try {
                    connections.take().close();
                } catch (InterruptedException e) {
                    LOG.error(e);
                } catch (SQLException e) {
                    LOG.error(e);
                }
            }
        }
    }

}
