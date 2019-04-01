package comp3350.breadtunes.tests.business.observables;

import org.junit.*;

import java.util.List;

import comp3350.breadtunes.business.observables.DatabaseUpdatedObservable;
import comp3350.breadtunes.presentation.enums.DatabaseState;
import comp3350.breadtunes.testhelpers.observers.BreadTunesTestObserver;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;

public class DatabaseStateObservableTest extends TestLogger {
    DatabaseUpdatedObservable dbStateObservable;
    BreadTunesTestObserver<DatabaseState> testDbStateObserver;

    @Before
    public void setup() {
        dbStateObservable = new DatabaseUpdatedObservable();
        testDbStateObserver = new BreadTunesTestObserver<>();

        dbStateObservable.addObserver(testDbStateObserver);
    }

    @Test
    public void dbObservable_singleValue_Test() {
        dbStateObservable.setValue(DatabaseState.DatabaseUpdated);

        DatabaseState observedState = testDbStateObserver.getMostRecentReceived();
        assertEquals(observedState, DatabaseState.DatabaseUpdated);
    }

    @Test
    public void dbObservable_multipleValues_Test() {
        dbStateObservable.setValue(DatabaseState.DatabaseEmpty);
        dbStateObservable.setValue(DatabaseState.DatabaseUpdated);

        List<DatabaseState> observedStates = testDbStateObserver.getAllReceived();
        assertEquals(observedStates.size(), 2);
        assertEquals(observedStates.get(0), DatabaseState.DatabaseEmpty);
        assertEquals(observedStates.get(1), DatabaseState.DatabaseUpdated);
    }
}
