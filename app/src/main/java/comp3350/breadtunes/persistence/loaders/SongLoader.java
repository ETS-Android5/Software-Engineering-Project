package comp3350.breadtunes.persistence.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;


/**
 * Credit to:
 *   Karim Abou Zeid for his work on the Phonograph app: github.com/kabouzeid/Phonograph
 *   This question about the Android MediaStore: stackoverflow.com/questions/13568798/list-all-music-in-mediastore-with-the-paths
 */
public class SongLoader {
    private static final String[] defaultProjection = new String[]{
            MediaStore.MediaColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.TRACK,
            MediaStore.Audio.AudioColumns.YEAR,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.ALBUM_ID,
            MediaStore.Audio.AudioColumns.ARTIST_ID,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ARTIST,
    };

    public static List<Song> getAllSongs(Context context) {
        Cursor cursor = getDefaultCursor(context);
        List<Song> songList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                songList.add(getSongFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return songList;
    }

    private static Cursor getDefaultCursor(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = String.format("%s=1 AND %s!=''", MediaStore.Audio.Media.IS_MUSIC, MediaStore.Audio.Media.TITLE);
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        return contentResolver.query(uri, defaultProjection, selection, null, sortOrder);
    }

    private static Song getSongFromCursor(Cursor cursor) {
        int songIdIndex = cursor.getColumnIndex(MediaStore.MediaColumns._ID);
        int titleIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int trackIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TRACK);
        int yearIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR);
        int durationIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
        int albumIdIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID);
        int artistIdIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST_ID);
        int albumNameIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
        int artistNameIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);

        int songId = cursor.getInt(songIdIndex);

        return new Song.Builder()
                .songId(cursor.getInt(songIdIndex))
                .name(cursor.getString(titleIndex))
                .trackNumber(cursor.getInt(trackIndex))
                .year(cursor.getInt(yearIndex))
                .duration(SongDuration.convertMillisToDuration(cursor.getLong(durationIndex)))
                .songUri(Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(songId)))
                .albumId(cursor.getInt(albumIdIndex))
                .artistId(cursor.getInt(artistIdIndex))
                .albumName(cursor.getString(albumNameIndex))
                .artistName(cursor.getString(artistNameIndex))
                .flaggedAsInappropriate(false)
                .build();
    }
}
