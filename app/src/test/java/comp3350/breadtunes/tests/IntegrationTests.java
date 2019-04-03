package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.breadtunes.tests.business.CredentialManagerIT;
import comp3350.breadtunes.tests.business.LookUpSongsIT;
import comp3350.breadtunes.tests.business.MusicPlayerStateIT;
import comp3350.breadtunes.tests.business.SongFlaggerIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LookUpSongsIT.class,
        CredentialManagerIT.class,
        MusicPlayerStateIT.class,
        SongFlaggerIT.class
})
public class IntegrationTests {
}

