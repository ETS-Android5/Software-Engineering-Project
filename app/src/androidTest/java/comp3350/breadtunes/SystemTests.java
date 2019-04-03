package comp3350.breadtunes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationTest.class,
        DisplaySongInformationFeatureTest.class,
        FindMusicFeatureTest.class,
        MusicQueueFeatureTest.class,
        ParentalControlFeatureTest.class,
        PlayMusicFeatureTest.class
})

public class SystemTests {
}
