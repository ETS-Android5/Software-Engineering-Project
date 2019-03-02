package comp3350.breadtunes.presentation.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import comp3350.breadtunes.objects.*;
import comp3350.breadtunes.presentation.loaders.*;
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

    private static List<Song> songs;
    private static List<Album> albums;
    private static List<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        requestReadExternalStoragePermission();

        // Load Songs, Albums, Artists asynchronously
        CompletableFuture<Void> completableFuture1 = loadMediaAsync();

        // Load data into database asynchronously
        CompletableFuture<Boolean> completableFuture2 = updateMediaDatabaseAsync(completableFuture1);

        // Notify users database has updated after loading if there's updated media
        notifyDatabaseUpdateAsync(completableFuture2);
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
                }
            }
        }
    }

    private CompletableFuture<Void> loadMediaAsync() {
        // Get songs from device, which cascades into getting albums and
        return CompletableFuture.supplyAsync(() -> SongLoader.getAllSongs(this))
                .thenApply(allSongs -> {
                    BaseActivity.setSongs(allSongs);
                    return AlbumLoader.getAllAlbums(allSongs);
                }).thenApply(allAlbums -> {
                    BaseActivity.setAlbums(allAlbums);
                    return ArtistLoader.getAllArtists(allAlbums);
                }).thenAccept(allArtists ->
                    BaseActivity.setArtists(allArtists)
                );
    }

    private CompletableFuture<Boolean> updateMediaDatabaseAsync(CompletableFuture<Void> cf) {
        return cf.thenApply(x -> {
            // Update database with Songs, Albums, and Artists
            boolean databaseUpdated = false;

            // Return whether anything in the database changed, so the UI knows to update
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

    private static void setSongs(List<Song> songs) {
        BaseActivity.songs = songs;
    }

    private static void setAlbums(List<Album> albums) {
        BaseActivity.albums = albums;
    }

    private static void setArtists(List<Artist> artists) {
        BaseActivity.artists = artists;
    }
}
