package comp3350.breadtunes.tests.business.observables;

import org.junit.Before;
import org.junit.Test;

import java.util.Observable;

import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.observers.TestObserver;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;

public class SongObservableTest extends TestLogger {
    TestObserver<Song> testSongObserver;

    @Before
    public void setup() {
        // Create anonymous instance of TestObserver
        testSongObserver = new TestObserver<Song>() {
            Song lastReceived = null;

            @Override
            public Song getMostRecent() {
                return lastReceived;
            }

            @Override
            public void update(Observable obs, Object obj) {
                if (obs instanceof SongObservable) {
                    lastReceived = ((SongObservable) obs).getSong();
                }
            }
        };
    }

    @Test
    public void songObservableTest() {
        // Arrange
        Song testSong = MockSongs.getMockSongList().get(1);
        SongObservable observable = new SongObservable();
        observable.addObserver(testSongObserver);

        // Act
        observable.setSong(testSong);

        // Assert
        Song observedSong = testSongObserver.getMostRecent();
        assertNotNull(observedSong);
        assertEquals(observedSong, testSong);
    }
}
