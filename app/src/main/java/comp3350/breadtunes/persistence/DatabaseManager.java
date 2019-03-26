package comp3350.breadtunes.persistence;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.breadtunes.R;

public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public Connection initializeDatabase(Context appContext) {
        try {
            String databasePath = getDatabasePath(appContext);

            boolean databaseCreated = true;
            if (!new File(databasePath + ".script").exists()) {
                databaseCreated = false;
            }

            long start = System.currentTimeMillis();

            // Create database connection
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            String connectionUrl = String.format("jdbc:hsqldb:file:%s", databasePath);
            Connection dbConnection = DriverManager.getConnection(connectionUrl, "SA", "");

            Log.i("HSQLDB", String.format("Database connection took %d milliseconds to create", System.currentTimeMillis() - start));

            if (!databaseCreated) {
                createDatabase(dbConnection);
            }

            return dbConnection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDatabasePath(Context appContext) {
        final String databaseAssetPath = appContext.getString(R.string.database_asset_path);
        File dataDirectory = appContext.getDir(databaseAssetPath, Context.MODE_PRIVATE);
        String databasePath = new File(dataDirectory, appContext.getString(R.string.database_name)).toString();
        return databasePath;
    }

    private void createDatabase(Connection dbConnection) throws SQLException {
        dbConnection.setAutoCommit(false);

        Statement statement = dbConnection.createStatement();
        for (String query : DatabaseCreationQueries.createDb) {
            statement.addBatch(query);
        }
        statement.executeBatch();

        dbConnection.setAutoCommit(true);

        Log.i("HSQLDB", "Initialized database tables because database did not exist.");
    }
}
