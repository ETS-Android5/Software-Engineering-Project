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

    @Test
    public void testSetSongs()
    {
        // Arrange
        ArrayList<Song> songList = new ArrayList<>();
        ArrayList<Song> songList2 = new ArrayList<>();
        Song a = new Song();
        songList2.add(a);

        // Act
        Album testTarget = new Album("Album 1", songList);

        assertEquals(songList, testTarget.getSongs());

        testTarget.setSongs(songList2);

        assertEquals(songList2, testTarget.getSongs());
    }

    @Test
    public void testSetGenre()
    {
        ArrayList<Song> songList = new ArrayList<>();
        Genre genre = new Genre("Test Genre");
        Genre genre2 = new Genre("Test Genre 2");

        Album testTarget = new Album("Album 1", songList, genre);

        assertEquals(genre, testTarget.getGenre());

        testTarget.setGenre(genre2);

        assertEquals(genre2, testTarget.getGenre());
    }
}