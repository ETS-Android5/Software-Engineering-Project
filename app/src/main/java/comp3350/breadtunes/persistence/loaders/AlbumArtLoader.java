package comp3350.breadtunes.persistence.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import comp3350.breadtunes.objects.Song;

public class AlbumArtLoader {
    private Context context;

    public AlbumArtLoader(Context context) {
        this.context = context;
    }

    private static final String[] defaultProjection = new String[]{
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ART
    };

    public Uri getAlbumArt(Song song) {
        if (song.getAlbumId() < 0) {
            return null;
        }

        Uri albumArt = null;

        Cursor cursor = getDefaultCursor(song.getAlbumId());

        if (cursor != null && cursor.moveToFirst()) {
            int albumArtColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            String path = cursor.getString(albumArtColumnIndex);

            if (path != null) {
                albumArt = Uri.parse(path);
            }
        }

        return albumArt;
    }

    private Cursor getDefaultCursor(int albumId) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = String.format("%s=%s", MediaStore.Audio.Albums._ID, String.valueOf(albumId));
        return contentResolver.query(uri, defaultProjection, selection, null, null);
    }
}
