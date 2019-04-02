package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.breadtunes.tests.business.LookUpSongsIT;
import comp3350.breadtunes.tests.business.MusicPlayerStateIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LookUpSongsIT.class,
        MusicPlayerStateIT.class
})
public class IntegrationTests {
}

