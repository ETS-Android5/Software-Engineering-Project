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
            BaseColumns._ID,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.TRACK,
            MediaStore.Audio.AudioColumns.YEAR,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM_ID,
            MediaStore.Audio.AudioColumns.ARTIST_ID,
    };

    private Context context;

    public SongLoader(Context context) {
        this.context = context;
    }

    public List<Song> getAllSongs() {
        Cursor cursor = getDefaultCursor();
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

    public Cursor getDefaultCursor() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = String.format("%s=1 AND %s!=''", MediaStore.Audio.Media.IS_MUSIC, MediaStore.Audio.Media.TITLE);
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        return contentResolver.query(uri, defaultProjection, selection, null, sortOrder);
    }

    public Song getSongFromCursor(Cursor cursor) {
        try {
            int songIdIndex = cursor.getColumnIndex(BaseColumns._ID);
            int titleIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
            int trackIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TRACK);
            int yearIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR);
            int durationIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
            int dataIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
            int albumIdIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID);
            int artistIdIndex = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST_ID);

            return new Song.Builder()
                    .songId(cursor.getInt(songIdIndex))
                    .name(cursor.getString(titleIndex))
                    .trackNumber(cursor.getInt(trackIndex))
                    .year(cursor.getInt(yearIndex))
                    .duration(SongDuration.convertMillisToDuration(cursor.getLong(durationIndex)))
                    .songFile(new File(cursor.getString(dataIndex)))
                    .albumId(cursor.getInt(albumIdIndex))
                    .artistId(cursor.getInt(artistIdIndex))
                    .build();

        } catch (Exception ex) {
            System.out.println(ex);
            return new Song();
        }
    }
}
