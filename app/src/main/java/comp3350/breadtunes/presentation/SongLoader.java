package comp3350.breadtunes.presentation;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import java.io.File;
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
    protected static final String[] defaultProjection = new String[]{
            BaseColumns._ID,// 0
            MediaStore.Audio.AudioColumns.TITLE,// 1
            MediaStore.Audio.AudioColumns.TRACK,// 2
            MediaStore.Audio.AudioColumns.YEAR,// 3
            MediaStore.Audio.AudioColumns.DURATION,// 4
            MediaStore.Audio.AudioColumns.DATA,// 5
            MediaStore.Audio.AudioColumns.ALBUM_ID,// 6
            MediaStore.Audio.AudioColumns.ARTIST_ID,// 7
    };

    private Context context;

    public SongLoader(Context context) {
        this.context = context;
    }

    public List<Song> getAllSongs() {
        Cursor cursor = getDefaultCursor();
        List<Song> songList = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            do {
                songList.add(getSongFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return songList;
    }

    public Cursor getDefaultCursor() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = String.format("%s=1 AND %s=0", MediaStore.Audio.Media.IS_MUSIC, MediaStore.Audio.Media.TITLE);
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        return contentResolver.query(uri, defaultProjection, selection, null, sortOrder);
    }

    public Song getSongFromCursor(Cursor cursor) {
        return new Song.Builder()
                .songId(cursor.getInt(0))
                .name(cursor.getString(1))
                .trackNumber(cursor.getInt(2))
                .year(cursor.getInt(3))
                .duration(SongDuration.convertMillisToDuration(cursor.getLong(4)))
                .songFile(new File(cursor.getString(5)))
                .albumId(cursor.getInt(6))
                .artistId(cursor.getInt(7))
                .build();
    }
}
