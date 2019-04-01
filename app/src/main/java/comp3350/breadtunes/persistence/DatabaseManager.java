package comp3350.breadtunes.persistence;

import android.util.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
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

    public void initializeDatabase(File databaseDirectory) {
        try {
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

    private void createDatabase() throws SQLException {
        dbConnection.setAutoCommit(false);

        Statement statement = dbConnection.createStatement();
        for (String query : DatabaseInfo.createDbQueries) {
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
