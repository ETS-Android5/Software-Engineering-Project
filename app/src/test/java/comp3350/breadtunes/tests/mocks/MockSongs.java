package comp3350.breadtunes.tests.mocks;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;

public class MockSongs {
    public static List<Song> getMockSongList() {
        List<Song> songs = new ArrayList<>();

        songs.add(new Song.Builder()
                .songId(1)
                .name("Bloch Prayer")
                .year(2001)
                .trackNumber(1)
                .duration(new SongDuration(0, 0))
                .artistId(1)
                .artistName("Artist 1")
                .albumId(1)
                .albumName("Album 1")
                .songUri(null)
                .flaggedAsInappropriate(false)
                .build());

        songs.add(new Song.Builder()
                .songId(2)
                .name("Haydn Adagio")
                .year(2002)
                .trackNumber(2)
                .duration(new SongDuration(3, 30))
                .artistId(2)
                .artistName("Artist 2")
                .albumId(2)
                .albumName("Album 2")
                .songUri(null)
                .flaggedAsInappropriate(false)
                .build());

        songs.add(new Song.Builder()
                .songId(3)
                .name("Nocturne")
                .year(2003)
                .trackNumber(3)
                .duration(new SongDuration(2, 0))
                .artistId(3)
                .artistName("Artist 3")
                .albumId(3)
                .albumName("Album 3")
                .songUri(null)
                .flaggedAsInappropriate(false)
                .build());

        songs.add(new Song.Builder()
                .songId(4)
                .name("Jarabe Tapatio")
                .year(2004)
                .trackNumber(4)
                .duration(new SongDuration(3, 0))
                .artistId(4)
                .artistName("Artist 4")
                .albumId(4)
                .albumName("Album 4")
                .songUri(null)
                .flaggedAsInappropriate(false)
                .build());

        return songs;
    }
}
