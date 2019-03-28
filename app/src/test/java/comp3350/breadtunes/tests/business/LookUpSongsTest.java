package comp3350.breadtunes.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.breadtunes.objects.*;
import comp3350.breadtunes.tests.mocks.MockSongs;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class LookUpSongsTest extends TestLogger {
    private List<Song> mockSongList;

    @Before
    public void setup() {
        mockSongList = MockSongs.getMockSongList();
    }

    @Test
    public void searchSongsExactMatchTest() {
    }

    @Test
    public void searchSongsCaseInsensitiveTest() {
    }

    @Test
    public void searchSongsNameSubstringTest() {
    }

    @Test
    public void searchSongsEmptyStringTest() {
    }
}
