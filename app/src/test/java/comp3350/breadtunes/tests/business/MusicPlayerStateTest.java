package comp3350.breadtunes.tests.business;

// imports
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import comp3350.breadtunes.business.MusicPlayerState;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.tests.watchers.TestLogger;

public class MusicPlayerStateTest extends TestLogger {
    Song a = new Song();
    Song b = new Song();
    Song c = new Song();
    Song d = new Song();

    @Test
    public void getCurrentlyPlayingSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setCurrentSong(a);
        assertTrue(a == mps.getCurrentlyPlayingSong());
        assertFalse(b == mps.getCurrentlyPlayingSong());
    }

    @Test
    public void setCurrentSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        for(int i = 0; i < songList.size()-1; i++)
        {
            mps.setCurrentSong(songList.get(i));
            assertTrue(mps.getCurrentlyPlayingSong() == songList.get(i));
        }
        assertFalse(mps.getCurrentlyPlayingSong() == songList.get(0));
    }

    @Test
    public void getCurrentSongList() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        assertTrue(mps.getCurrentSongList() == songList);
        assertFalse(mps.getCurrentSongList() == null);
    }

    @Test
    public void setCurrentSongListTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        List<Song> testSongList = Arrays.asList(a,b,c);
        MusicPlayerState mps = new MusicPlayerState(songList);

        assertTrue(songList == mps.getCurrentSongList());
        assertFalse(testSongList == mps.getCurrentSongList());

        mps.setCurrentSongList(testSongList);

        assertTrue(testSongList == mps.getCurrentSongList());
        assertFalse(songList == mps.getCurrentSongList());
    }

    @Test
    public void getNextSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        List<Song> testList = mps.getCurrentSongList();

        mps.setCurrentSong(testList.get(0)); // initial song is first item in the initial queue
        for(int i = 0; i < songList.size(); i++)
        {
            assertEquals(songList.get(i), mps.getCurrentlyPlayingSong());
            mps.setCurrentSong(mps.getNextSong());
        }
        assertNull(mps.getCurrentlyPlayingSong());
    }

    @Test
    public void getPreviousSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        List<Song> testList = mps.getCurrentSongList();

        mps.setCurrentSong(testList.get(3)); // initial song is last item in the initial queue
        for(int i = songList.size()-1; i > -1; i--)
        {
            assertEquals(songList.get(i), mps.getCurrentlyPlayingSong());
            mps.setCurrentSong(mps.getPreviousSong());
        }
        assertNull(mps.getCurrentlyPlayingSong());
    }

    @Test
    public void getSetPausedPositionTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setPausedPosition(30);
        assertEquals(mps.getPausedPosition(),30);
        assertFalse(mps.getPausedPosition() == 0);
    }

    @Test
    public void songPlayingTest() {
        // 4 commands will be functions will be used here:
        // setIsSongPlaying, isSongPlaying
        // setIsSongPaused, isSongPaused
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setIsSongPlaying(true);
        assertTrue(mps.isSongPlaying() == true);
        assertFalse(mps.isSongPlaying() == false);

        mps.setIsSongPaused(true);
        assertTrue(mps.isSongPaused() == true);
        assertFalse(mps.isSongPaused() == false);

        mps.setIsSongPlaying(false);
        assertTrue(mps.isSongPlaying() == false);
        assertFalse(mps.isSongPlaying() == true);

        mps.setIsSongPaused(false);
        assertTrue(mps.isSongPaused() == false);
        assertFalse(mps.isSongPaused() == true);
    }

    @Test
    public void updateSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setCurrentSong(mps.getCurrentSongList().get(1));
        mps.updateNextSong();
        mps.updatePreviousSong();

        assertTrue(mps.getNextSong() == songList.get(2));
        assertTrue(mps.getPreviousSong() == songList.get(0));

        assertFalse(mps.getNextSong() == songList.get(3));
        assertFalse(mps.getPreviousSong() == songList.get(1));
    }

    @Test
    public void subscribeToSongChangeTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        Observer mockObserver = Mockito.mock(Observer.class);

        mps.subscribeToSongChange(mockObserver);
    }
}
