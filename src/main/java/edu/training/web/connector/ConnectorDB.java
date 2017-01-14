package edu.training.web.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Roman on 07.12.2016.
 */
 class ConnectorDB {
    private static final Logger LOG = LogManager.getLogger();
    private static final String BUNDLE_NAME = "database";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    static{
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }catch (SQLException e){
            LOG.fatal(e);
            throw new RuntimeException();
        }
    }
    static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle(BUNDLE_NAME);
        String url = resource.getString(DATABASE_URL);
        String user = resource.getString(DATABASE_USER);
        String pass = resource.getString(DATABASE_PASSWORD);
        return DriverManager.getConnection(url, user, pass);
    }
}

