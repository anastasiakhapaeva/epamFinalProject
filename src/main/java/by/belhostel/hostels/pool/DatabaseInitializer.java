package by.belhostel.hostels.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Roman on 07.12.2016.
 */
class DatabaseInitializer {

    /** The Constant LOG. */
    private static final Logger LOG = LogManager.getLogger();

    /** The Constant PARAM_BUNDLE_NAME. */
    private static final String PARAM_BUNDLE_NAME = "database";

    /** The Constant PARAM_DATABASE_URL. */
    private static final String PARAM_DATABASE_URL = "db.url";

    /** The Constant PARAM_DATABASE_USER. */
    private static final String PARAM_DATABASE_USER = "db.user";

    /** The Constant PARAM_DATABASE_PASSWORD. */
    private static final String PARAM_DATABASE_PASSWORD = "db.password";

    /** The Constant PARAM_POOL_SIZE. */
    private static final String PARAM_POOL_SIZE = "db.poolsize";

    /** The bundle. */
    final ResourceBundle BUNDLE;

    /** The database url. */
    final String DATABASE_URL;

    /** The database user. */
    final String DATABASE_USER;

    /** The database password. */
    final String DATABASE_PASSWORD;

    /** The pool size. */
    final int POOL_SIZE;

    /**
     * Instantiates a new database initializer.
     */
    public DatabaseInitializer() {
        try {
            BUNDLE = ResourceBundle.getBundle(PARAM_BUNDLE_NAME);
            DATABASE_URL = BUNDLE.getString(PARAM_DATABASE_URL);
            DATABASE_USER = BUNDLE.getString(PARAM_DATABASE_USER);
            DATABASE_PASSWORD = BUNDLE.getString(PARAM_DATABASE_PASSWORD);
            POOL_SIZE = Integer.parseInt(BUNDLE.getString(PARAM_POOL_SIZE));
        }catch (NumberFormatException | MissingResourceException e){
            LOG.fatal("Database initializing error: ", e);
            throw new RuntimeException("Database initializing error: ", e);
        }
    }
}

