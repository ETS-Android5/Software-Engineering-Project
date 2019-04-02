package comp3350.breadtunes.application;

import android.app.Application;
import android.content.Context;

import java.io.File;

import comp3350.breadtunes.R;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.AppState;
import comp3350.breadtunes.services.ServiceGateway;

public class BreadTunesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        // Initialize the database
        final String databaseAssetPath = context.getString(R.string.database_asset_path);
        File databaseDirectory = context.getDir(databaseAssetPath, MODE_PRIVATE);
        ServiceGateway.getDatabaseManager().initializeDatabase(databaseDirectory, new Logger());

        // Set global Context
        AppState.applicationContext = context;
    }
}
