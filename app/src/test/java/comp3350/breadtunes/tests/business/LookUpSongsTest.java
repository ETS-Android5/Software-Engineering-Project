package comp3350.breadtunes.tests.business;

import org.junit.Test;

import comp3350.breadtunes.business.LookUpSongs;

import static junit.framework.TestCase.assertEquals;

public class LookUpSongsTest {

    @Test
    public void searchSongsTest() {
        LookUpSongs search = new LookUpSongs();
        assertEquals(0, search.searchSongs("Someone Like You").size());
    }

    @Test
    public void searchAlbumsTest() {
        LookUpSongs search = new LookUpSongs();
        assertEquals(0, search.searchAlbums("21").size());
    }

    @Test
    public void searchArtistsTest() {
        LookUpSongs search = new LookUpSongs();
        assertEquals(0, search.searchArtists("Adele").size());
    }
}
