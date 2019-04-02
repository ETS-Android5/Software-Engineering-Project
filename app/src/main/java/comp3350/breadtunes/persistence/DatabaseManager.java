package comp3350.breadtunes.persistence;

import android.provider.MediaStore;
import android.util.Log;

import com.google.common.io.Files;

import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.breadtunes.presentation.Logger.Logger;

public class DatabaseManager {
    private File temporaryDatabaseDirectory = null;
    private File databaseDirectory = null;
    private static DatabaseManager instance;
    private Logger logger;

    private Connection dbConnection;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public void initializeDatabase(File databaseDirectory, Logger logger) {
        try {
            this.logger = logger;
            this.databaseDirectory = databaseDirectory;

            String databasePath = new File(databaseDirectory, DatabaseInfo.databaseName).toString();

            boolean databaseCreated = true;

            // This statement is tightly coupled with HSQLDB, since HSQLDB uses a .script file to
            // represent a database in the file system. This checks if that file exists or not.
            if (!new File(databasePath + ".script").exists()) {
                databaseCreated = false;
            }

            long start = System.currentTimeMillis();

            // Create database connection (this is an expensive set of operations)
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            String connectionUrl = String.format("jdbc:hsqldb:file:%s", databasePath);
            dbConnection = DriverManager.getConnection(connectionUrl, "SA", "");

            logger.i("HSQLDB", String.format("Database connection took %d milliseconds to create", System.currentTimeMillis() - start));

            if (!databaseCreated) {
                createDatabase();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a copy of the database into a temporary file, and sets the dbConnection to the new
     * database copy.
     *
     * This function should be called AFTER initializeDatabase() has been called.
     *
     * @param copyDatabaseDirectory Path to folder of the database copy
     */
    public void createAndUseDatabaseCopy(File copyDatabaseDirectory) {
        if (dbConnection == null || databaseDirectory == null) {
            throw new IllegalStateException("Cannot call createAndUseDatabaseCopy before initializing database.");
        }

        try {
            // Shutdown current database
            dbConnection.prepareStatement("SHUTDOWN").execute();
            dbConnection.close();

            // Create database copy
            temporaryDatabaseDirectory = copyDatabaseDirectory;
            FileUtils.copyDirectory(databaseDirectory, temporaryDatabaseDirectory);

            // Set connection to temporary database
            String tempDatabasePath = new File(temporaryDatabaseDirectory, DatabaseInfo.databaseName).toString();
            String connectionUrl = String.format("jdbc:hsqldb:file:%s", tempDatabasePath);
            dbConnection = DriverManager.getConnection(connectionUrl, "SA", "");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * After calling this method, the state of the database manager is no longer valid and it should
     * not be used.
     *
     * This deletes the temporary database if it exists, and closes the connection to the database.
     */
    public void destroyTempDatabaseAndCloseConnection() {
        if (dbConnection != null) try {
            dbConnection.prepareStatement("SHUTDOWN").execute();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(temporaryDatabaseDirectory != null) try {
            FileUtils.deleteDirectory(temporaryDatabaseDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase() throws SQLException {
        dbConnection.setAutoCommit(false);

        Statement statement = dbConnection.createStatement();
        for (String query : DatabaseInfo.createDbQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();

        dbConnection.setAutoCommit(true);

        logger.i("HSQLDB", "Initialized database tables because database did not exist.");
    }

    public Connection getDbConnection() {
        return dbConnection;
    }
}
