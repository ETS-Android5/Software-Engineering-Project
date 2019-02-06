package comp3350.breadtunes.tests.business;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.objects.*;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class LookUpSongsTest {
    @Mock
    private Services mockServiceProvider = mock(Services.class);

    private LookUpSongs testTarget;

    @Before
    public void setup() {
        ArrayList<Song> mockSongList = new ArrayList<>();
        ArrayList<Album> mockAlbumList = new ArrayList<>();
        ArrayList<Artist> mockArtistList = new ArrayList<>();

        when(mockServiceProvider.getSongPersistence().getAll())
                .thenReturn(mockSongList);
        when(mockServiceProvider.getAlbumPersistence().getAll())
                .thenReturn(mockAlbumList);
        when(mockServiceProvider.getArtistPersistence().getAll())
                .thenReturn(mockArtistList);

        testTarget = new LookUpSongs(mockServiceProvider);
    }

    @Test
    public void searchSongsTest() {
    }
}
