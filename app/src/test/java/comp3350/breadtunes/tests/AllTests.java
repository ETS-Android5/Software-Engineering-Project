package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.breadtunes.tests.business.*;
import comp3350.breadtunes.tests.business.observables.*;
import comp3350.breadtunes.tests.objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongDurationTest.class,
        SongTest.class,
        SecureCredentialsTest.class,
        LookUpSongsTest.class,
        MusicPlayerStateTest.class,
        CredentialManagerTest.class,
        DateTimeHelperTest.class,
        StringHasherTest.class,
        SongFlaggerTest.class,
        SongObservableTest.class,
        DatabaseStateObservableTest.class,
        ParentalControlStatusObservableTest.class,
        PlayModeObservableTest.class,
})
public class AllTests {
}
