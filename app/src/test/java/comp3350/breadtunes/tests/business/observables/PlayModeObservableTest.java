package comp3350.breadtunes.tests.business.observables;

import org.junit.*;

import java.util.List;

import comp3350.breadtunes.business.observables.PlayModeObservable;
import comp3350.breadtunes.testhelpers.observers.BreadTunesTestObserver;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;

public class PlayModeObservableTest extends TestLogger {
    BreadTunesTestObserver<String> testPlayModeObserver;
    PlayModeObservable playModeObservable;

    @Before
    public void setup() {
        testPlayModeObserver = new BreadTunesTestObserver<>();
        playModeObservable = new PlayModeObservable();

        playModeObservable.addObserver(testPlayModeObserver);
    }

    @Test
    public void playModeObservable_singleValue_Test() {
        String testMode = "Shuffle on";

        playModeObservable.setValue(testMode);

        String observedMode = testPlayModeObserver.getMostRecentReceived();
        assertNotNull(observedMode);
        assertEquals(observedMode, testMode);
    }

    @Test
    public void playModeObservable_multipleValues_Test() {
        String testMode1 = "Shuffle on";
        String testMode2 = "Shuffle off";

        playModeObservable.setValue(testMode1);
        playModeObservable.setValue(testMode2);

        List<String> observedValues = testPlayModeObserver.getAllReceived();
        assertEquals(observedValues.size(), 2);
        assertEquals(observedValues.get(0), testMode1);
        assertEquals(observedValues.get(1), testMode2);
    }
}
