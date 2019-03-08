package comp3350.breadtunes.presentation.loaders;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Song;

public class AlbumLoader {
    // Get songs from MediaStore before splitting them
    public static List<Album> getAllAlbums(Context context) {
        List<Song> songs = SongLoader.getAllSongs(context);
        return getAlbumsFromSongs(songs);
    }

    // Split an existing list of songs into albums
    public static List<Album> getAllAlbums(List<Song> songs) {
        return getAlbumsFromSongs(songs);
    }

    private static List<Album> getAlbumsFromSongs(List<Song> songs) {
        List<Album> albums;

        if (songs.isEmpty()) {
            albums = new ArrayList<>();
        } else {
            albums = splitSongsIntoAlbums(songs);
            Collections.sort(albums); // Sort by album name
        }

        return albums;
    }

    private static List<Album> splitSongsIntoAlbums(@NonNull List<Song> songs) {
        Collections.sort(songs, new SongComparator());
        List<Album> albums = new ArrayList<>();

        int prevAlbum = songs.get(0).getAlbumId();
        String prevAlbumName = "";
        List<Song> albumSongs = new ArrayList<>();

        for (Song song: songs) {
            // If looking at a new album, add the previous album to the list
            if (song.getAlbumId() != prevAlbum) {
                albums.add(new Album(prevAlbum, prevAlbumName, albumSongs));

                // Reset list of songs in album
                albumSongs = new ArrayList<>();
            }

            // Add this song to the list of songs in the album
            albumSongs.add(song);

            prevAlbum = song.getAlbumId();
            prevAlbumName = song.getAlbumName();
        }

        // Add the last album after the for loop ends
        albums.add(new Album(prevAlbum, prevAlbumName, albumSongs));

        return albums;
    }

    private static class SongComparator implements Comparator<Song> {
        @Override
        public int compare(Song song1, Song song2) {
            // Sort by albumID, then by trackNumber
            Integer s1AlbumId = song1.getAlbumId();
            Integer s2AlbumId = song2.getAlbumId();
            int albumIdComparison = s1AlbumId.compareTo(s2AlbumId);

            // If the albums are the same, order by track number
            if (albumIdComparison == 0) {
                Integer s1TrackNumber = song1.getTrackNumber();
                Integer s2TrackNumber = song2.getTrackNumber();

                return s1TrackNumber.compareTo(s2TrackNumber);
            }
            // If the albums are different, order by album name
            else {
                return albumIdComparison;
            }
        }
    }
}
