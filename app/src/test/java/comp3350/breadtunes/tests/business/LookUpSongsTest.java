package comp3350.breadtunes.tests.business;

import org.junit.Test;

import comp3350.breadtunes.business.LookUpSongs;

import static junit.framework.TestCase.assertEquals;

public class LookUpSongsTest {

    @Test
    public void searchSongsTest() {
        System.out.println("\nStarting searchSongsTest");
        LookUpSongs search = new LookUpSongs();
        assertEquals(0, search.searchSongs("Someone Like You").size());
        System.out.println("Finished searchSongsTest");
    }

    @Test
    public void searchAlbumsTest() {
        System.out.println("\nStarting searchAlbumsTest");
        LookUpSongs search = new LookUpSongs();
        assertEquals(0, search.searchAlbums("21").size());
        System.out.println("\nFinishing searchAlbumsTest");
    }

    @Test
    public void searchArtistsTest() {
        System.out.println("\nStarting searchArtistsTest");
        LookUpSongs search = new LookUpSongs();
        assertEquals(0, search.searchArtists("Adele").size());
        System.out.println("\nFinishing searchArtistsTest");
    }
}
