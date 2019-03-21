package comp3350.breadtunes.persistence.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;

public class ArtistLoader {
    // Get songs from MediaStore before splitting them
    public static List<Artist> getAllArtists(Context context) {
        List<Album> albums = AlbumLoader.getAllAlbums(context);
        return getArtistsFromSongs(albums);
    }

    // Split an existing list of songs into albums
    public static List<Artist> getAllArtists(List<Album> albums) {
        return getArtistsFromSongs(albums);
    }

    private static List<Artist> getArtistsFromSongs(List<Album> albums) {
        List<Artist> artists;

        if (albums.isEmpty()) {
            artists = new ArrayList<>();
        } else {
            artists = splitAlbumsIntoArtists(albums);
            Collections.sort(artists);
        }

        return artists;
    }

    private static List<Artist> splitAlbumsIntoArtists(List<Album> albums) {
        Collections.sort(albums, new AlbumArtistComparator());
        List<Artist> artists = new ArrayList<>();

        int prevArtist = albums.get(0).getSongs().get(0).getArtistId();
        String prevArtistName = "";
        List<Album> artistAlbums = new ArrayList<>();

        for (Album album: albums) {
            // If looking at a new artist, add the previous artist to the list
            if (album.getSongs().get(0).getArtistId() != prevArtist) {
                artists.add(new Artist(prevArtist, prevArtistName, artistAlbums));

                // Reset list of albums for artist
                artistAlbums = new ArrayList<>();
            }

            artistAlbums.add(album);

            prevArtist = album.getSongs().get(0).getArtistId();
            prevArtistName = album.getSongs().get(0).getArtistName();
        }

        // Add the last artist after the loop ends
        artists.add(new Artist(prevArtist, prevArtistName, artistAlbums));

        return artists;
    }

    private static class AlbumArtistComparator implements Comparator<Album> {
        @Override
        public int compare(Album album1, Album album2) {
            // Sort by artist, then by album
            Integer artist1 = album1.getSongs().get(0).getArtistId();
            Integer artist2 = album2.getSongs().get(0).getArtistId();

            int artistComparison = artist1.compareTo(artist2);

            // If artists are the same, order by album
            if (artistComparison == 0) {
                return album1.compareTo(album2);
            }
            // If the artists are different, order by artist
            else {
                return artistComparison;
            }
        }
    }
}
