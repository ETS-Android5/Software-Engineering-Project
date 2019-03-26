package comp3350.breadtunes.application;

import android.app.Application;

import java.sql.Connection;

import comp3350.breadtunes.persistence.DatabaseManager;

public class BreadTunesApplication extends Application {
    private String databasePath;
    private static Connection dbConnection;

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager dbInstance = DatabaseManager.getInstance();
        dbConnection = dbInstance.initializeDatabase(getApplicationContext());
    }

    public static Connection getDbConnection() {
        return dbConnection;
    }
}
