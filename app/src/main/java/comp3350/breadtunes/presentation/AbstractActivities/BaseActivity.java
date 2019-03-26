package comp3350.breadtunes.presentation.AbstractActivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import comp3350.breadtunes.presentation.enums.DatabaseState;
import comp3350.breadtunes.objects.*;
import comp3350.breadtunes.persistence.SongPersistence;
import comp3350.breadtunes.persistence.loaders.*;
import comp3350.breadtunes.services.AppState;
import comp3350.breadtunes.services.ServiceGateway;
import java8.util.concurrent.CompletableFuture;

/**
 * Partial credit due to Karim Abou Zeid for his work on the Phonograph app:
 *   https://github.com/kabouzeid/Phonograph
 *
 * Super helpful page on CompletableFuture:
 *   http://codeflex.co/java-multithreading-completablefuture-explained/
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_READ_EXTERNAL_REQUEST = 60000;
    private static boolean baseInitialized = false;

    private static List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (baseInitialized == true) return;

        requestReadExternalStoragePermission();

        if (AppState.externalReadAccessAllowed) {
            notifyDatabaseUpdateAsync(updateMediaDatabaseAsync(loadMediaAsync()));
        }

        baseInitialized = true;
    }

    protected void requestReadExternalStoragePermission() {
        String readExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        // If build version is at least Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(readExternalStoragePermission) != PackageManager.PERMISSION_GRANTED) {
                // User wants to see the rationale for accepting read external storage
                if (shouldShowRequestPermissionRationale(readExternalStoragePermission)) {
                    Toast.makeText(getApplicationContext(),
                            "Permission is required to read your audio files.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                // User does not want to see rationale, just ask for permission
                else {
                    String[] permissions = {readExternalStoragePermission};
                    requestPermissions(permissions, MY_PERMISSION_READ_EXTERNAL_REQUEST);
                }
            }
            else {
                AppState.externalReadAccessAllowed = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_READ_EXTERNAL_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We can now read the external storage
                    AppState.externalReadAccessAllowed = true;
                    notifyDatabaseUpdateAsync(updateMediaDatabaseAsync(loadMediaAsync()));
                }
            }
        }
    }

    private CompletableFuture<Void> loadMediaAsync() {
        // Get songs from device
        return CompletableFuture.supplyAsync(() -> SongLoader.getAllSongs(this))
                .thenAccept(allSongs -> BaseActivity.songs = allSongs);
    }

    private CompletableFuture<Boolean> updateMediaDatabaseAsync(CompletableFuture<Void> cf) {
        return cf.thenApply(x -> {
            // Update database with just Songs for now
            SongPersistence songPersistence = ServiceGateway.getSongPersistence();
            boolean databaseUpdated = songPersistence.insertSongsNoDuplicates(BaseActivity.songs);
            return databaseUpdated;
        });
    }

    private CompletableFuture<Void> notifyDatabaseUpdateAsync(CompletableFuture<Boolean> cf) {
        return cf.thenAccept(databaseUpdated -> {
            if (databaseUpdated) {
                ServiceGateway.updateDatabaseState(DatabaseState.DatabaseUpdated);
            } else if (songs == null || songs.isEmpty()) {
                ServiceGateway.updateDatabaseState(DatabaseState.DatabaseEmpty);
            }
        });
    }
}