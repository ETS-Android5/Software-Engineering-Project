package comp3350.breadtunes.persistence;

import android.content.Context;
import android.util.Log;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.breadtunes.R;

public class DatabaseManager {
    private File temporaryDatabase = null;
    private String databasePath = null;
    private static DatabaseManager instance;

    private Connection dbConnection;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public void initializeDatabase(Context appContext) {
        try {
            databasePath = getDatabasePath(appContext);

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

            Log.i("HSQLDB", String.format("Database connection took %d milliseconds to create", System.currentTimeMillis() - start));

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
     * @param tempDatabasePath Path to folder of the database
     */
    public void createAndUseDatabaseCopy(String tempDatabasePath) {
        if (dbConnection == null || databasePath == null) {
            throw new IllegalStateException("Cannot call createAndUseDatabaseCopy before initializing database.");
        }

        try {
            File currentDatabase = new File(databasePath + ".script");
            temporaryDatabase = new File(tempDatabasePath + ".script");

            Files.copy(currentDatabase, temporaryDatabase);

            // Change connection to temp database
            String connectionUrl = String.format("jdbc:hsqldb:file:%s", tempDatabasePath);
            dbConnection = DriverManager.getConnection(connectionUrl, "SA", "");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
        if(temporaryDatabase != null) {
            temporaryDatabase.delete();
        }
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    private String getDatabasePath(Context appContext) {
        final String databaseAssetPath = appContext.getString(R.string.database_asset_path);
        File dataDirectory = appContext.getDir(databaseAssetPath, Context.MODE_PRIVATE);
        return new File(dataDirectory, appContext.getString(R.string.database_name)).toString();
    }

    private void createDatabase() throws SQLException {
        dbConnection.setAutoCommit(false);

        Statement statement = dbConnection.createStatement();
        for (String query : DatabaseCreationQueries.createDb) {
            statement.addBatch(query);
        }
        statement.executeBatch();

        dbConnection.setAutoCommit(true);

        Log.i("HSQLDB", "Initialized database tables because database did not exist.");
    }

    public Connection getDbConnection() {
        return dbConnection;
    }
}
