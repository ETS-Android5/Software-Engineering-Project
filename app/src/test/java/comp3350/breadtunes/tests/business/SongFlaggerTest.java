package comp3350.breadtunes.tests.business;

import org.junit.*;

import comp3350.breadtunes.business.SongFlagger;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

public class SongFlaggerTest extends TestLogger {
    SongPersistence mockSongPersistence;
    SongFlagger testTarget;
    Song testSong;

    @Before
    public void setup() {
        mockSongPersistence = mock(SongPersistence.class);
        testTarget = new SongFlagger(mockSongPersistence);

        testSong = MockSongs.getMockSongList().get(0);
    }

    @Test
    public void setSongFlagTrueTest() {
        testTarget.flagSong(testSong, true);

        verify(mockSongPersistence, times(1)).setSongFlagged(testSong, true);
    }

    @Test
    public void setSongFlagFalseTest() {
        testTarget.flagSong(testSong, false);

        verify(mockSongPersistence, times(1)).setSongFlagged(testSong, false);

    }

    @Test
    public void getSongFlaggedTrueTest() {
        when(mockSongPersistence.isSongFlagged(testSong)).thenReturn(true);

        boolean songIsFlagged = testTarget.songIsFlagged(testSong);

        verify(mockSongPersistence, times(1)).isSongFlagged(testSong);
        assertTrue(songIsFlagged);
    }

    @Test
    public void getSongFlaggedFalseTest() {
        when(mockSongPersistence.isSongFlagged(testSong)).thenReturn(false);

        boolean songIsFlagged = testTarget.songIsFlagged(testSong);

        verify(mockSongPersistence, times(1)).isSongFlagged(testSong);
        assertFalse(songIsFlagged);
    }
}
