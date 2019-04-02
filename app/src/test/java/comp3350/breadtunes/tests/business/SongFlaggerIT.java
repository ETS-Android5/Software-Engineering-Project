package comp3350.breadtunes.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.SongFlagger;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.hsql.SongPersistenceHSQL;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.values.BreadTunesIntegrationTests;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SongFlaggerIT {
    SongFlagger testTarget;
    Song testSong;
    SongPersistenceHSQL songPersistence;
    Logger mockLogger = mock(Logger.class);

    @Before
    public void setup() {
        // Make DatabaseManager use a copy of the integration test database
        File realDirectory = new File(BreadTunesIntegrationTests.realDatabasePath);
        File copyDirectory = new File(BreadTunesIntegrationTests.copyDatabasePath);
        ServiceGateway.getDatabaseManager().initializeDatabase(realDirectory, mockLogger);
        ServiceGateway.getDatabaseManager().createAndUseDatabaseCopy(copyDirectory);

        // Create SongFlagger class
        songPersistence = new SongPersistenceHSQL();
        testTarget = new SongFlagger(songPersistence);
        testSong = MockSongs.getMockSongList().get(0);//not sure of this
    }

    @Test
    public void setSongFlagTrueTest() {
        testTarget.flagSong(testSong, true);

        verify(songPersistence, times(1)).setSongFlagged(testSong, true);
    }

    @Test
    public void setSongFlagFalseTest() {
        testTarget.flagSong(testSong, false);

        verify(songPersistence, times(1)).setSongFlagged(testSong, false);

    }

    @Test
    public void getSongFlaggedTrueTest() {
        when(songPersistence.isSongFlagged(testSong)).thenReturn(true);

        boolean songIsFlagged = testTarget.songIsFlagged(testSong);

        verify(songPersistence, times(1)).isSongFlagged(testSong);
        assertTrue(songIsFlagged);
    }

    @Test
    public void getSongFlaggedFalseTest() {
        when(songPersistence.isSongFlagged(testSong)).thenReturn(false);

        boolean songIsFlagged = testTarget.songIsFlagged(testSong);

        verify(songPersistence, times(1)).isSongFlagged(testSong);
        assertFalse(songIsFlagged);
    }
}
