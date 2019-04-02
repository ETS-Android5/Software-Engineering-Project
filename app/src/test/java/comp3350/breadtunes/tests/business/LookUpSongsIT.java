package comp3350.breadtunes.tests.business;

import android.app.Instrumentation;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.ServiceTestCase;

import org.junit.After;
import org.junit.Before;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.Test;
import comp3350.breadtunes.R;
import comp3350.breadtunes.application.BreadTunesApplication;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.hsql.SongPersistenceHSQL;
import comp3350.breadtunes.presentation.HomeActivity;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.ServiceGateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LookUpSongsIT{
    private MusicPlayerState musicPlayerState;
    private LookUpSongs testTarget;
    Context context;



    @Before
    public void setup() {

        try {
            //Method getTestContext = ServiceTestCase.class.getMethod("getTestContext");
            //context = (Context) getTestContext.invoke(this);
            //Instrumentation.newApplication();
            //BreadTunesApplication application = new BreadTunesApplication();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        String dbAssetPath = context.getString(R.string.database_asset_path);
        File dataDirectory = new File(dbAssetPath);
        String fakePath = new File(dataDirectory, "MediaDBCopy").toString();

        ServiceGateway.getDatabaseManager().initializeDatabase(context);
        ServiceGateway.getDatabaseManager().createAndUseDatabaseCopy(fakePath);
        final SongPersistenceHSQL songPersistence = new SongPersistenceHSQL();
        musicPlayerState = new MusicPlayerState(songPersistence,new Logger());

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
        //destroy temp database and close connection
        ServiceGateway.getDatabaseManager().destroyTempDatabaseAndCloseConnection();
    }
}
