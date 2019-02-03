package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Song;

import static org.junit.Assert.*;

public class AlbumTest
{
	@Test
	public void testAlbum()
	{
		// Arrange
		Album album;
		ArrayList<Song> songList = new ArrayList<Song>();

		System.out.println("\nStarting testAlbum");

		// Act
		album = new Album("Album 1", songList);

		// Assert
		assertNotNull(album);
		assertTrue(album.getName().equals("Album 1"));
		assertTrue(album.getSongs().equals(songList));

		System.out.println("Finished testAlbum");
	}
}