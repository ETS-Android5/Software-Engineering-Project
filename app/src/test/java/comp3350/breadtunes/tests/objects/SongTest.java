package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.io.File;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;

import static org.junit.Assert.*;

public class SongTest {
    @Test
    public void testSongConstructor1() {
        System.out.println("\nStarting testSongConstructor1");

        // Arrange
        SongDuration duration = new SongDuration(3, 3);
        Artist artist = new Artist("Test Artist", null);
        Album album = new Album("Test Album", null);
        File file = new File("no file");

        // Act
        Song song = new Song("Test Song", 1, duration, artist, album, file);
        song.setYear(2018);

        // Assert
        assertNotNull(song);
        assertTrue(song.getName().equals("Test Song"));
        assertTrue(song.getTrackNumber() == 1);
        assertTrue(song.getDuration().getMinutes() == 3);
        assertTrue(song.getArtist().getName().equals("Test Artist"));
        assertTrue(song.getAlbum().getName().equals("Test Album"));
        assertTrue(song.getSongFile().equals(file));
        assertTrue(song.getYear() == 2018);

        System.out.println("Finished testSongConstructor1");
    }

    @Test
    public void testSongConstructor2() {
        System.out.println("\nStarting testSongConstructor2");

        // Arrange
        SongDuration duration = new SongDuration(3, 3);
        Artist artist = new Artist("Test Artist", null);
        Album album = new Album("Test Album", null);

        // Act
        Song song = new Song("Test Song", 1, duration, artist, album, "no file");
        song.setYear(2018);

        // Assert
        assertNotNull(song);
        assertTrue(song.getName().equals("Test Song"));
        assertTrue(song.getTrackNumber() == 1);
        assertTrue(song.getDuration().getMinutes() == 3);
        assertTrue(song.getArtist().getName().equals("Test Artist"));
        assertTrue(song.getAlbum().getName().equals("Test Album"));
        assertNotNull(song.getSongFile());
        assertTrue(song.getYear() == 2018);

        System.out.println("Finished testSongConstructor2");
    }
}
