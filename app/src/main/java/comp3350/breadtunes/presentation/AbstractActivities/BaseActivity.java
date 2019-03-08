package comp3350.breadtunes.presentation.AbstractActivities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import comp3350.breadtunes.objects.*;
import comp3350.breadtunes.R;
import comp3350.breadtunes.persistence.SongPersistence;
import comp3350.breadtunes.presentation.loaders.*;
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
    private static List<Album> albums;
    private static List<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (baseInitialized == true) return;

        requestReadExternalStoragePermission();
        copyDatabaseToDevice();

        if (AppState.externalReadAccessAllowed) {
            notifyDatabaseUpdateAsync(updateMediaDatabaseAsync(loadMediaAsync()));
        } else {
            Toast.makeText(this, "External Read Access has been Denied", Toast.LENGTH_LONG);
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
                }
            }
        }
    }

    private void copyDatabaseToDevice() {
        final String databaseAssetPath = getString(R.string.database_asset_path);

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(databaseAssetPath, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(databaseAssetPath);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = databaseAssetPath + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            AppState.databasePath = new File(dataDirectory, getString(R.string.database_name)).toString();

        } catch (final IOException ioe) {
            Log.w("", "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    private CompletableFuture<Void> loadMediaAsync() {
        // Get songs from device, which cascades into getting albums and
        return CompletableFuture.supplyAsync(() -> SongLoader.getAllSongs(this))
                .thenApply(allSongs -> {
                    BaseActivity.songs = allSongs;
                    return AlbumLoader.getAllAlbums(allSongs);
                }).thenApply(allAlbums -> {
                    BaseActivity.albums = allAlbums;
                    return ArtistLoader.getAllArtists(allAlbums);
                }).thenAccept(allArtists ->
                        BaseActivity.artists = allArtists
                );
    }

    private CompletableFuture<Boolean> updateMediaDatabaseAsync(CompletableFuture<Void> cf) {
        return cf.thenApply(x -> {
            // Update database with just Songs for now
            boolean databaseUpdated = false;
            SongPersistence songPersistence = ServiceGateway.getSongPersistence();

            for (Song song: BaseActivity.songs) {
                databaseUpdated = songPersistence.insertNoDuplicates(song);
            }

            return databaseUpdated;
        });
    }

    private CompletableFuture<Void> notifyDatabaseUpdateAsync(CompletableFuture<Boolean> cf) {
        return cf.thenAccept(databaseUpdated -> {
            if (databaseUpdated) {
                // Notify
            } else if (songs == null || songs.isEmpty()) {
                // If there was no music, notify that the mock song list should be used
            }
        });
    }
}
