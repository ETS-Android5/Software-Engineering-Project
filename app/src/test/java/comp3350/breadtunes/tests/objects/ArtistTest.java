package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.tests.watchers.TestLogger;
import comp3350.breadtunes.objects.Song;

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

    @Test
    public void testSetAlbums()
    {
        // Arrange
        ArrayList<Song> songList = new ArrayList<>();
        ArrayList<Song> songList2 = new ArrayList<>();
        Song a = new Song();
        songList2.add(a);
        Album mockAlbum1 = new Album("Album 1", songList);
        Album mockAlbum2 = new Album("Album 2", songList2);
        ArrayList<Album> mockAlbumsList= new ArrayList<>();

        // Act
        Artist testTarget = new Artist("testName", null);
        assertEquals(testTarget.getAlbums(), null);

        mockAlbumsList.add(mockAlbum1);
        mockAlbumsList.add(mockAlbum2);

        testTarget.setAlbums(mockAlbumsList);

        assertEquals(testTarget.getAlbums(),mockAlbumsList);
    }
}
