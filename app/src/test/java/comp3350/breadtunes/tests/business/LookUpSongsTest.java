package comp3350.breadtunes.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.objects.*;
import comp3350.breadtunes.tests.mocks.MockAlbums;
import comp3350.breadtunes.tests.mocks.MockArtists;
import comp3350.breadtunes.tests.mocks.MockSongs;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class LookUpSongsTest {
    private List<Song> mockSongList;
    private List<Album> mockAlbumList;
    private List<Artist> mockArtistList;

    private LookUpSongs testTarget;

    @Before
    public void setup() {
        mockSongList = MockSongs.getMockSongList();
        mockAlbumList = MockAlbums.getMockAlbumList();
        mockArtistList = MockArtists.getMockArtistList();

        testTarget = new LookUpSongs(mockSongList, mockAlbumList, mockArtistList);
    }

    @Test
    public void searchSongsExactMatchTest() {
        System.out.println("\nStarting searchSongsExactMatchTest");

        // Act
        ArrayList<Song> songList = testTarget.searchSongs("Haydn Adagio");

        // Assert
        assertTrue(songList.size() == 1);
        assertEquals(songList.get(0).getName(), "Haydn Adagio");

        System.out.println("Finished searchSongsExactMatchTest");
    }

    @Test
    public void searchSongsCaseInsensitiveTest() {
        System.out.println("\nStarting searchSongsCaseInsensitiveTest");

        // Act
        ArrayList<Song> songList = testTarget.searchSongs("haydn adagio");

        // Assert
        assertTrue(songList.size() == 1);
        assertEquals(songList.get(0).getName(), "Haydn Adagio");

        System.out.println("Finished searchSongsCaseInsensitiveTest");
    }

    @Test
    public void searchSongsNameSubstringTest() {
        System.out.println("\nStarting searchSongsNameSubstringTest");

        // Act
        ArrayList<Song> songList = testTarget.searchSongs("Prayer");

        // Assert
        assertEquals(songList.size(), 1);
        assertEquals(songList.get(0).getName(), "Bloch Prayer");

        System.out.println("Finished searchSongsNameSubstringTest");
    }

    @Test
    public void searchSongsEmptyStringTest() {
        System.out.println("\nStarting searchSongsEmptyStringTest");

        // Act
        ArrayList<Song> songList = testTarget.searchSongs("");

        // Assert
        assertTrue(songList.size() == 0);

        System.out.println("Finished searchSongsEmptyStringTest");
    }

    @Test
    public void searchAlbumsExactMatchTest() {
        System.out.println("\nStarting searchAlbumsExactMatchTest");

        // Act
        ArrayList<Album> albumList = testTarget.searchAlbums("2019 Top Hits");

        // Assert
        assertTrue(albumList.size() == 1);
        assertEquals(albumList.get(0).getName(), "2019 Top Hits");

        System.out.println("Finished searchAlbumsExactMatchTest");
    }

    @Test
    public void searchAlbumsCaseInsensitiveTest() {
        System.out.println("\nStarting searchAlbumsCaseInsensitiveTest");

        // Act
        ArrayList<Album> albumList = testTarget.searchAlbums("2019 top hits");

        // Assert
        assertTrue(albumList.size() == 1);
        assertEquals(albumList.get(0).getName(), "2019 Top Hits");

        System.out.println("Finished searchAlbumsCaseInsensitiveTest");
    }

    @Test
    public void searchAlbumsNameSubstringTest() {
        System.out.println("\nStarting searchAlbumsNameSubstringTest");

        // Act
        ArrayList<Album> albumList = testTarget.searchAlbums("Top Hits");

        // Assert
        assertTrue(albumList.size() == 1);
        assertEquals(albumList.get(0).getName(), "2019 Top Hits");

        System.out.println("Finished searchAlbumsNameSubstringTest");
    }

    @Test
    public void searchAlbumsEmptyStringTest() {
        System.out.println("\nStarting searchAlbumsEmptyStringTest");

        // Act
        ArrayList<Album> albumList = testTarget.searchAlbums("");

        // Assert
        assertTrue(albumList.size() == 0);

        System.out.println("Finished searchAlbumsEmptyStringTest");
    }

    @Test
    public void searchArtistsExactMatchTest() {
        System.out.println("\nStarting searchArtistsExactMatchTest");

        // Act
        ArrayList<Artist> artistList = testTarget.searchArtists("Beyonce");

        // Assert
        assertTrue(artistList.size() == 1);
        assertEquals(artistList.get(0).getName(), "Beyonce");

        System.out.println("Finished searchAlbumsExactMatchTest");
    }

    @Test
    public void searchArtistsCaseInsensitiveTest() {
        System.out.println("\nStarting searchArtistsCaseInsensitiveTest");

        // Act
        ArrayList<Artist> artistList = testTarget.searchArtists("beyonce");

        // Assert
        assertTrue(artistList.size() == 1);
        assertEquals(artistList.get(0).getName(), "Beyonce");

        System.out.println("Finished searchArtistsCaseInsensitiveTest");
    }

    @Test
    public void searchArtistsNameSubstringTest() {
        System.out.println("\nStarting searchArtistsNameSubstringTest");

        // Act
        ArrayList<Artist> artistList = testTarget.searchArtists("yonce");

        // Assert
        assertEquals(artistList.size(),1);
        assertEquals(artistList.get(0).getName(), "Beyonce");

        System.out.println("Finished searchArtistsNameSubstringTest");
    }

    @Test
    public void searchArtistsMultipleMatchTest() {
        System.out.println("\nStarting searchArtistsMultipleMatchTest");

        // Act
        List<Artist> artistList = testTarget.searchArtists("ee");

        // Assert
        assertTrue(artistList.size() == 2);
        for (Artist artist: artistList) {
            assertTrue(artist.getName().equals("Bruce Springsteen") || artist.getName().equals("Beethoven"));
        }

        System.out.println("Finished searchArtistsMultipleMatchTest");
    }

    @Test
    public void searchArtistsEmptyStringTest() {
        System.out.println("\nStarting searchArtistsEmptyStringTest");

        // Act
        ArrayList<Artist> artistList = testTarget.searchArtists("");

        // Assert
        assertTrue(artistList.size() == 0);

        System.out.println("Finished searchArtistsEmptyStringTest");
    }
}
