package comp3350.breadtunes.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import comp3350.breadtunes.business.SongFlagger;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.hsql.SongPersistenceHSQL;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.testhelpers.values.BreadTunesIntegrationTests;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

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
        testSong = songPersistence.getAll().get(0);
        testTarget = new SongFlagger(songPersistence);
    }

    @Test
    public void setSongFlagTrueTest() {
        testTarget.flagSong(testSong, true);
        assertTrue(songPersistence.isSongFlag);
    }

    @Test
    public void setSongFlagFalseTest() {
        testTarget.flagSong(testSong, false);
        assertFalse(songPersistence.isSongFlag);
    }

    @Test
    public void getSongFlaggedTrueTest() {
        assertTrue(songPersistence.isSongFlagged(testSong));

        boolean songIsFlagged = testTarget.songIsFlagged(testSong);
        assertTrue(songPersistence.inSongFlaggedMethod);
        assertTrue(songIsFlagged);
    }

    @Test
    public void getSongFlaggedFalseTest() {
        assertFalse(songPersistence.isSongFlagged(testSong));

        boolean songIsFlagged = testTarget.songIsFlagged(testSong);

        //verify(songPersistence, times(1)).isSongFlagged(testSong);
        assertFalse(songIsFlagged);
    }

    @After
    public void tearDown(){
        ServiceGateway.getDatabaseManager().destroyTempDatabaseAndCloseConnection();
    }
}
