package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Genre;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static org.junit.Assert.*;

public class AlbumTest extends TestLogger {
	@Test
	public void testAlbumConstructor1()
	{
		// Arrange
		ArrayList<Song> songList = new ArrayList<>();

		// Act
		Album album = new Album("Album 1", songList);

		// Assert
		assertNotNull(album);
		assertTrue(album.getName().equals("Album 1"));
		assertTrue(album.getSongs().equals(songList));
	}

    @Test
    public void testAlbumConstructor2()
    {
        // Arrange
        ArrayList<Song> songList = new ArrayList<>();
        Genre genre = new Genre("Test Genre");

        // Act
        Album album = new Album("Album 2", songList, genre);

        // Assert
        assertNotNull(album);
        assertTrue(album.getName().equals("Album 2"));
        assertTrue(album.getSongs().equals(songList));
        assertTrue(album.getGenre().equals(genre));
    }
}