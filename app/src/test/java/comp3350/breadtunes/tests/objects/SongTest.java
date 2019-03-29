package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static org.junit.Assert.*;

public class SongTest extends TestLogger {
    @Test
    public void testSongBuilder() {
        // Arrange
        SongDuration duration = new SongDuration(3, 3);

        // Act
        Song song = new Song.Builder()
                .songId(1)
                .name("Test Song")
                .year(2000)
                .trackNumber(1)
                .duration(duration)
                .artistName("Test Artist")
                .artistId(123)
                .albumName("Test Album")
                .albumId(456)
                .songUri(null)
                .build();

        // Assert
        assertNotNull(song);
        assertEquals(song.getSongId(), 1);
        assertEquals(song.getName(), "Test Song");
        assertEquals(song.getYear(), 2000);
        assertEquals(song.getTrackNumber(), 1);
        assertEquals(song.getDuration(), duration);
        assertEquals(song.getArtistName(), "Test Artist");
        assertEquals(song.getArtistId(), 123);
        assertEquals(song.getAlbumName(), "Test Album");
        assertEquals(song.getAlbumId(), 456);
        assertEquals(song.getSongUri(), null);
    }
}
