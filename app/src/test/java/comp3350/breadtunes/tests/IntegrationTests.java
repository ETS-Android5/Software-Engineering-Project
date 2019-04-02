package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.breadtunes.tests.business.LookUpSongsIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LookUpSongsIT.class
})
public class IntegrationTests {
}

