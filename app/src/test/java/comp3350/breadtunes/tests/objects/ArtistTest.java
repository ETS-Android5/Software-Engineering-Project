package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static org.junit.Assert.*;

public class ArtistTest extends TestLogger {
    @Test
    public void testArtist() {
        // Arrange
        ArrayList<Album> albums = new ArrayList<>();

        // Act
        Artist artist = new Artist("Test Artist", albums);

        // Assert
        assertNotNull(artist);
        assertTrue(artist.getName().equals("Test Artist"));
        assertTrue(artist.getAlbums().equals(albums));
    }
}
