package comp3350.breadtunes.application;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;

import java.io.File;

import comp3350.breadtunes.R;
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
        ServiceGateway.getDatabaseManager().initializeDatabase(databaseDirectory);

        // Set global Context
        AppState.applicationContext = context;

        // Set global audio manager
        AppState.audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }
}
