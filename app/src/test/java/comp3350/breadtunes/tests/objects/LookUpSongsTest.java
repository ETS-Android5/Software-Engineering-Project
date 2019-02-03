package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.business.LookUpSongs;

import static junit.framework.TestCase.assertEquals;

public class LookUpSongsTest {
        @Test
        public void searchTest() {
            LookUpSongs search = new LookUpSongs();
            assertEquals(0, search.searchArtists("Someone Like You.mp3").size());
        }
}
