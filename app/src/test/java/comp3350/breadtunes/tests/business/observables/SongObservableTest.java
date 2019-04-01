package comp3350.breadtunes.tests.business.observables;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.observers.BreadTunesTestObserver;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;

public class SongObservableTest extends TestLogger {
    BreadTunesTestObserver<Song> testSongObserver;
    SongObservable songObservable;

    @Before
    public void setup() {
        testSongObserver = new BreadTunesTestObserver<>();
        songObservable = new SongObservable();

        songObservable.addObserver(testSongObserver);
    }

    @Test
    public void songObservable_singleValue_Test() {
        Song testSong = MockSongs.getMockSongList().get(1);

        songObservable.setValue(testSong);

        Song observedSong = testSongObserver.getMostRecentReceived();
        assertNotNull(observedSong);
        assertEquals(observedSong, testSong);
    }

    @Test
    public void songObservable_multipleValues_Test() {
        List<Song> songList = MockSongs.getMockSongList();
        Song testSong1 = songList.get(0);
        Song testSong2 = songList.get(1);

        songObservable.setValue(testSong1);
        songObservable.setValue(testSong2);

        List<Song> observedValues = testSongObserver.getAllReceived();
        assertEquals(observedValues.size(), 2);
        assertEquals(observedValues.get(0), testSong1);
        assertEquals(observedValues.get(1), testSong2);
    }
}
