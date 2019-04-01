package comp3350.breadtunes.application;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;

import comp3350.breadtunes.services.AppState;
import comp3350.breadtunes.services.ServiceGateway;

public class BreadTunesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ServiceGateway.getDatabaseManager().initializeDatabase(getApplicationContext());
        AppState.applicationContext = getApplicationContext();
        AppState.audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }
}
