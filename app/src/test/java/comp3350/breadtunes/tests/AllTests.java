package comp3350.breadtunes.tests;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;

import comp3350.breadtunes.tests.business.*;
import comp3350.breadtunes.tests.objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlbumTest.class,
        ArtistTest.class,
        GenreTest.class,
        SongDurationTest.class,
        SongTest.class,
        QueueTest.class,
        LookUpSongsTest.class,
        MusicPlayerStateTest.class,
        MediaPlayerControllerTest.class,
})
public class AllTests {
}
