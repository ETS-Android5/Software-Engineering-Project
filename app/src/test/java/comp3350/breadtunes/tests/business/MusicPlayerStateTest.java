package comp3350.breadtunes.tests.business;

import org.junit.Before;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSong(a);

        //mps.getMusicPlayerStateInstance() = new MusicPlayerState(songList);

        assertTrue(a == mps2.getCurrentlyPlayingSong());
        assertFalse(b == mps2.getCurrentlyPlayingSong());
    }

    @Test
    public void setCurrentSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        for(int i = 0; i < songList.size()-1; i++)
        {
            mps2.setCurrentSong(songList.get(i));
            assertTrue(mps2.getCurrentlyPlayingSong() == songList.get(i));
        }
        assertFalse(mps2.getCurrentlyPlayingSong() == songList.get(0));
    }

    @Test
    public void getCurrentSongList() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSongList(songList);
        assertTrue(mps2.getCurrentSongList() == songList);
        assertFalse(mps2.getCurrentSongList() == null);
    }

    @Test
    public void setCurrentSongListTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        List<Song> testSongList = Arrays.asList(a,b,c);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSongList(songList);

        assertTrue(songList == mps2.getCurrentSongList());
        assertFalse(testSongList == mps2.getCurrentSongList());

        mps2.setCurrentSongList(testSongList);

        assertTrue(testSongList == mps2.getCurrentSongList());
        assertFalse(songList == mps2.getCurrentSongList());
    }

    @Test
    public void getNextSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSongList(songList);

        List<Song> testList = mps2.getCurrentSongList();

        mps2.setCurrentSong(testList.get(0)); // initial song is first item in the initial queue

        assertEquals(songList.get(0), mps2.getCurrentlyPlayingSong());
        mps2.setCurrentSong(mps2.getNextSong());
        assertEquals(songList.get(1), mps2.getCurrentlyPlayingSong());

    }

    @Test
    public void getPreviousSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();


        mps2.setCurrentSongList(songList);
        List<Song> testList = mps2.getCurrentSongList();

        mps2.setCurrentSong(testList.get(3)); // initial song is last item in the initial queue

        assertEquals(songList.get(3), mps2.getCurrentlyPlayingSong());
        mps2.setCurrentSong(mps2.getPreviousSong());
        assertEquals(songList.get(2), mps2.getCurrentlyPlayingSong());

    }

    @Test
    public void getSetPausedPositionTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setPausedPosition(30);
        assertEquals(mps2.getPausedPosition(),30);
        assertFalse(mps2.getPausedPosition() == 0);
    }

    @Test
    public void songPlayingTest() {
        // 4 commands will be functions will be used here:
        // setIsSongPlaying, isSongPlaying
        // setIsSongPaused, isSongPaused
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setIsSongPlaying(true);
        assertTrue(mps2.isSongPlaying() == true);
        assertFalse(mps2.isSongPlaying() == false);

        mps.getInstance().setIsSongPaused(true);
        assertTrue(mps2.isSongPaused() == true);
        assertFalse(mps2.isSongPaused() == false);

        mps.getInstance().setIsSongPlaying(false);
        assertTrue(mps2.isSongPlaying() == false);
        assertFalse(mps2.isSongPlaying() == true);

        mps.getInstance().setIsSongPaused(false);
        assertTrue(mps2.isSongPaused() == false);
        assertFalse(mps2.isSongPaused() == true);
    }

    @Test
    public void updateSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSongList(songList);

        mps2.setCurrentSong(mps2.getCurrentSongList().get(1));
        mps2.updateNextSong();
        mps2.updatePreviousSong();

        assertTrue(mps2.getNextSong() == songList.get(2));
        assertTrue(mps2.getPreviousSong() == songList.get(0));

        assertFalse(mps2.getNextSong() == songList.get(3));
        assertFalse(mps2.getPreviousSong() == songList.get(1));
    }

    @Test
    public void subscribeToSongChangeTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        Observer mockObserver = Mockito.mock(Observer.class);

        mps2.subscribeToSongChange(mockObserver);
    }

    @Test
    public void subscribeToPlayModeChangeTest()
    {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        Observer mockObserver = Mockito.mock(Observer.class);

        mps2.subscribeToPlayModeChange(mockObserver);
    }

    @Test
    public void getMusicPlayerStateTest()
    {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSong(a);

        System.out.println(mps2.getMusicPlayerStateInstance());
        assertEquals( "Current song not null song is null song paused: false song playing falsevariable currentSongname is null",

        mps2.getMusicPlayerStateInstance());
    }

    @Test
    public void getPlayModeTest()
    {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSongList(songList);

        assertEquals("", mps2.getPlayMode());

        mps2.setShuffleMode(true);
        mps2.updateNextSong();
        mps2.setRepeatMode(true);

        assertEquals("Shuffle on - Repeat on", mps2.getPlayMode());

        mps2.setShuffleMode(false);
        mps2.setRepeatMode(false);

        assertEquals("", mps2.getPlayMode());
    }

    @Test
    public void testCurrentlyPlayingSongNameMutator()
    {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps2 = mps.getMusicPlayerStateInstance();

        mps2.setCurrentSong(a);
        mps2.setCurrentSongPlayingName("test");

        assertEquals("test", mps2.getCurrentlyPlayingSongName());
    }


}
