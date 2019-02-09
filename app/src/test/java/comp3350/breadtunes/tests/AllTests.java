package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
        HomeActivityHelperTest.class,
        LookUpSongsTest.class,
        MusicPlayerStateTest.class,
        MediaPlayerControllerTest.class,
})
public class AllTests { }
