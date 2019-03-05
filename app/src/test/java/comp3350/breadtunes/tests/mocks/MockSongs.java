package comp3350.breadtunes.tests.mocks;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;

public class MockSongs {
    public static List<Song> getMockSongList() {
        List<Song> songs = new ArrayList<>();

        songs.add(new Song.Builder()
                .name("Bloch Prayer")
                .rawName("prayer")
                .trackNumber(1)
                .artistName("Artist 1")
                .albumName("Album 1")
                .duration(new SongDuration(0, 0))
                .build());

        songs.add(new Song.Builder()
                .name("Haydn Adagio")
                .rawName("adagio")
                .trackNumber(1)
                .artistName("Artist 2")
                .albumName("Album 2")
                .duration(new SongDuration(0, 0))
                .build());

        songs.add(new Song.Builder()
                .name("Nocturne")
                .rawName("nocturne")
                .trackNumber(1)
                .artistName("Artist 3")
                .albumName("Album 3")
                .duration(new SongDuration(0, 0))
                .build());

        songs.add(new Song.Builder()
                .name("Jaraba Tapatio")
                .rawName("jarabe")
                .trackNumber(1)
                .artistName("Artist 4")
                .albumName("Album 4")
                .duration(new SongDuration(0, 0))
                .build());

        return songs;
    }
}
