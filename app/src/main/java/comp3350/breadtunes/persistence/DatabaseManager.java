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
        statement.addBatch("CREATE MEMORY TABLE PUBLIC.SONGS(SONGID INTEGER NOT NULL PRIMARY KEY, NAME VARCHAR(256), YEAR INTEGER, TRACK INTEGER, DURATION VARCHAR(256), ARTISTID INTEGER, ARTISTNAME VARCHAR(256), ALBUMID INTEGER, ALBUMNAME VARCHAR(256), URI VARCHAR(256));");
        statement.addBatch("CREATE MEMORY TABLE PUBLIC.CREDENTIALS(CID INTEGER IDENTITY PRIMARY KEY, PIN CHAR(64) NOT NULL, SECURITYQUESTION VARCHAR(80) NOT NULL, SECURITYQUESTIONANS CHAR(64) NOT NULL, DATEUPDATED VARCHAR(64));");
        statement.addBatch("INSERT INTO SONGS VALUES (-10, 'Haydn Adagio', 2000, 1, 'H:0M:3S:1', -10, 'Joseph Haydn', -10, 'Classical Tunes', 'android.resource://comp3350.breadtunes/raw/adagio');");
        statement.addBatch("INSERT INTO SONGS VALUES (-11, 'Jarabe Tapatio', 2000, 1, 'H:0M:3S:3', -11, 'Mexico', -11, 'Mexican Tunes', 'android.resource://comp3350.breadtunes/raw/jarabe');");
        statement.addBatch("INSERT INTO SONGS VALUES (-12, 'Nocturne', 2000, 1, 'H:0M:3S:5', -12, 'Frederic Chopin', -12, 'Classical Tunes', 'android.resource://comp3350.breadtunes/raw/nocturne');");
        statement.addBatch("INSERT INTO SONGS VALUES (-13, 'Bloch Prayer', 2000, 1, 'H:0M:3S:6', -13, 'Ernest Bloch', -13, 'Classical Tunes', 'android.resource://comp3350.breadtunes/raw/prayer');");
        statement.executeBatch();
        dbConnection.setAutoCommit(true);

        Log.i("HSQLDB", "Initialized database tables because database did not exist.");
    }
}
