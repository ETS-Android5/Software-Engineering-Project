package comp3350.breadtunes.tests.business.observables;

import org.junit.*;

import java.util.List;

import comp3350.breadtunes.business.observables.ParentalControlStatusObservable;
import comp3350.breadtunes.testhelpers.observers.BreadTunesTestObserver;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;

public class ParentalControlStatusObservableTest extends TestLogger {
    BreadTunesTestObserver<Boolean> parentalStatusTestObserver;
    ParentalControlStatusObservable parentalStatusObservable;

    @Before
    public void setup() {
        parentalStatusTestObserver = new BreadTunesTestObserver<>();
        parentalStatusObservable = new ParentalControlStatusObservable();

        parentalStatusObservable.addObserver(parentalStatusTestObserver);
    }

    @Test
    public void parentalControlObservable_singleValue_Test() {
        parentalStatusObservable.setValue(false);

        boolean observedValue = parentalStatusTestObserver.getMostRecentReceived();
        assertEquals(observedValue, false);
    }

    @Test
    public void parentalControlObservable_multipleValues_Test() {
        parentalStatusObservable.setValue(false);
        parentalStatusObservable.setValue(true);

        List<Boolean> observedValues = parentalStatusTestObserver.getAllReceived();
        assertEquals(observedValues.size(), 2);
        assertEquals(observedValues.get(0).booleanValue(), false);
        assertEquals(observedValues.get(1).booleanValue(), true);
    }
}
