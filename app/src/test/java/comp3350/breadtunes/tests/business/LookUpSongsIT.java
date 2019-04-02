package comp3350.breadtunes.tests.business;

import org.junit.*;

import java.io.File;
import java.util.List;

import comp3350.breadtunes.business.*;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.hsql.SongPersistenceHSQL;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.testhelpers.values.BreadTunesIntegrationTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class LookUpSongsIT {
    private LookUpSongs testTarget;

    @Before
    public void setup() {
        // Make DatabaseManager use a copy of the integration test database
        File realDirectory = new File(BreadTunesIntegrationTests.realDatabasePath);
        File copyDirectory = new File(BreadTunesIntegrationTests.copyDatabasePath);
        ServiceGateway.getDatabaseManager().initializeDatabase(realDirectory, mock(Logger.class));
        ServiceGateway.getDatabaseManager().createAndUseDatabaseCopy(copyDirectory);

        // Create LookUpSongs class
        final SongPersistenceHSQL songPersistence = new SongPersistenceHSQL();
        MusicPlayerState musicPlayerState = new MusicPlayerState(songPersistence, mock(Logger.class));
        testTarget = new LookUpSongs(musicPlayerState);
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

    @After
    public void tearDown(){
        ServiceGateway.getDatabaseManager().destroyTempDatabaseAndCloseConnection();
    }
}
