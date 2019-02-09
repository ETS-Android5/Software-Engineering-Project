package comp3350.breadtunes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.breadtunes.tests.business.MusicPlayerStateTest;
import comp3350.breadtunes.tests.business.QueueTest;
import comp3350.breadtunes.tests.business.LookUpSongsTest;
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
        MusicPlayerStateTest.class
})
public class AllTests { }
