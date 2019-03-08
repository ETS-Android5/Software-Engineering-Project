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
import comp3350.breadtunes.tests.watchers.TestLogger;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class LookUpSongsTest extends TestLogger {
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
        // Act
        List<Song> songList = testTarget.searchSongs("Haydn Adagio");

        // Assert
        assertTrue(songList.size() == 1);
        assertEquals(songList.get(0).getName(), "Haydn Adagio");
    }

    @Test
    public void searchSongsCaseInsensitiveTest() {
        // Act
        List<Song> songList = testTarget.searchSongs("haydn adagio");

        // Assert
        assertTrue(songList.size() == 1);
        assertEquals(songList.get(0).getName(), "Haydn Adagio");
    }

    @Test
    public void searchSongsNameSubstringTest() {
        // Act
        List<Song> songList = testTarget.searchSongs("Prayer");

        // Assert
        assertEquals(songList.size(), 1);
        assertEquals(songList.get(0).getName(), "Bloch Prayer");
    }

    @Test
    public void searchSongsEmptyStringTest() {
        // Act
        List<Song> songList = testTarget.searchSongs("");

        // Assert
        assertTrue(songList.size() == 0);
    }

    @Test
    public void searchAlbumsExactMatchTest() {
        // Act
        List<Album> albumList = testTarget.searchAlbums("2019 Top Hits");

        // Assert
        assertTrue(albumList.size() == 1);
        assertEquals(albumList.get(0).getName(), "2019 Top Hits");
    }

    @Test
    public void searchAlbumsCaseInsensitiveTest() {
        // Act
        List<Album> albumList = testTarget.searchAlbums("2019 top hits");

        // Assert
        assertTrue(albumList.size() == 1);
        assertEquals(albumList.get(0).getName(), "2019 Top Hits");
    }

    @Test
    public void searchAlbumsNameSubstringTest() {
        // Act
        List<Album> albumList = testTarget.searchAlbums("Top Hits");

        // Assert
        assertTrue(albumList.size() == 1);
        assertEquals(albumList.get(0).getName(), "2019 Top Hits");
    }

    @Test
    public void searchAlbumsEmptyStringTest() {
        // Act
        List<Album> albumList = testTarget.searchAlbums("");

        // Assert
        assertTrue(albumList.size() == 0);
    }

    @Test
    public void searchArtistsExactMatchTest() {
        // Act
        List<Artist> artistList = testTarget.searchArtists("Beyonce");

        // Assert
        assertTrue(artistList.size() == 1);
        assertEquals(artistList.get(0).getName(), "Beyonce");
    }

    @Test
    public void searchArtistsCaseInsensitiveTest() {
        // Act
        List<Artist> artistList = testTarget.searchArtists("beyonce");

        // Assert
        assertTrue(artistList.size() == 1);
        assertEquals(artistList.get(0).getName(), "Beyonce");
    }

    @Test
    public void searchArtistsNameSubstringTest() {
        // Act
        List<Artist> artistList = testTarget.searchArtists("yonce");

        // Assert
        assertEquals(artistList.size(),1);
        assertEquals(artistList.get(0).getName(), "Beyonce");
    }

    @Test
    public void searchArtistsMultipleMatchTest() {
        // Act
        List<Artist> artistList = testTarget.searchArtists("ee");

        // Assert
        assertTrue(artistList.size() == 2);
        for (Artist artist: artistList) {
            assertTrue(artist.getName().equals("Bruce Springsteen") || artist.getName().equals("Beethoven"));
        }
    }

    @Test
    public void searchArtistsEmptyStringTest() {
        // Act
        List<Artist> artistList = testTarget.searchArtists("");

        // Assert
        assertTrue(artistList.size() == 0);
    }

    @Test
    public void getSongTest()
    {
        // Act
        ArrayList<Song> testList = new ArrayList<Song>();
        testList.add(mockSongList.get(0));

        // Assert
        assertEquals(mockSongList.get(0), testTarget.getSong(testList, "Bloch Prayer"));
    }
}
