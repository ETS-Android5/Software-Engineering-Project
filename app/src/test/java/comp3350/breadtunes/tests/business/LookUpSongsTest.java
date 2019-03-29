package comp3350.breadtunes.tests.business;

import org.junit.*;

import java.util.List;

import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.*;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LookUpSongsTest extends TestLogger {
    List<Song> mockSongList;
    MusicPlayerState mockMusicPlayerState = mock(MusicPlayerState.class);
    LookUpSongs testTarget;


    @Before
    public void setup() {
        mockSongList = MockSongs.getMockSongList();
        when(mockMusicPlayerState.getCurrentSongList()).thenReturn(mockSongList);

        testTarget = new LookUpSongs(mockMusicPlayerState);
    }

    @Test
    public void searchSongsExactMatchTest() {
        List<Song> songs = testTarget.searchSongs("Bloch Prayer");

        assertFalse(songs.isEmpty());
        assertEquals(songs.get(0).getName(), "Bloch Prayer");
    }

    @Test
    public void searchSongsCaseInsensitiveTest() {
        List<Song> songs = testTarget.searchSongs("bloch prayer");

        assertFalse(songs.isEmpty());
        assertEquals(songs.get(0).getName(), "Bloch Prayer");
    }

    @Test
    public void searchSongsNameSubstringTest() {
        List<Song> songs = testTarget.searchSongs("Tapatio");

        assertFalse(songs.isEmpty());
        assertEquals(songs.get(0).getName(), "Jarabe Tapatio");
    }

    @Test
    public void searchSongsEmptyStringTest() {
        List<Song> songs = testTarget.searchSongs("");

        assertTrue(songs.isEmpty());
    }

    @Test
    public void searchSongsNoMatchTest() {
        List<Song> songs = testTarget.searchSongs("All of The Lights");

        assertTrue(songs.isEmpty());
    }

    @Test
    public void staticSongNameLookup_songFound_Test() {
        Song testSong = mockSongList.get(0);

        Song songFound = LookUpSongs.getSong(mockSongList, testSong.getName());

        assertNotNull(songFound);
        assertEquals(testSong, songFound);
    }

    @Test
    public void staticSongNameLookup_songNotFound_Test() {
        Song songFound = LookUpSongs.getSong(mockSongList, "NONEXISTENT");

        assertNull(songFound);
    }
}
