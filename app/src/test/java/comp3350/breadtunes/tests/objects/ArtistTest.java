package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;

import static org.junit.Assert.*;

public class ArtistTest {
    @Test
    public void testArtist() {
        System.out.println("\nRunning testArtist");

        // Arrange
        ArrayList<Album> albums = new ArrayList<>();

        // Act
        Artist artist = new Artist("Test Artist", albums);

        // Assert
        assertNotNull(artist);
        assertTrue(artist.getName().equals("Test Artist"));
        assertTrue(artist.getAlbums().equals(albums));

        System.out.println("Finished testArtist");
    }
}
