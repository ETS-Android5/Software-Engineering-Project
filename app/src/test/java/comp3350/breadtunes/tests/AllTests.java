package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.breadtunes.tests.business.*;
import comp3350.breadtunes.tests.objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SongDurationTest.class,
        SongTest.class,
        LookUpSongsTest.class,
        MusicPlayerStateTest.class,
})
public class AllTests {
}
