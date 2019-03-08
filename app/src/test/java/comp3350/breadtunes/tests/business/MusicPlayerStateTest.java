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
        //MusicPlayerState mps = new MusicPlayerState(songList);
        MusicPlayerState mps = new MusicPlayerState();


        mps.getInstance().setCurrentSong(a);
        assertTrue(a == mps.getInstance().getCurrentlyPlayingSong());
        assertFalse(b == mps.getInstance().getCurrentlyPlayingSong());
    }

    @Test
    public void setCurrentSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState();

        for(int i = 0; i < songList.size()-1; i++)
        {
            mps.getInstance().setCurrentSong(songList.get(i));
            assertTrue(mps.getInstance().getCurrentlyPlayingSong() == songList.get(i));
        }
        assertFalse(mps.getInstance().getCurrentlyPlayingSong() == songList.get(0));
    }

    @Test
    public void getCurrentSongList() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState();

        mps.getInstance().setCurrentSongList(songList);
        assertTrue(mps.getInstance().getCurrentSongList() == songList);
        assertFalse(mps.getInstance().getCurrentSongList() == null);
    }

    @Test
    public void setCurrentSongListTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        List<Song> testSongList = Arrays.asList(a,b,c);
        MusicPlayerState mps = new MusicPlayerState();
        mps.getInstance().setCurrentSongList(songList);

        assertTrue(songList == mps.getInstance().getCurrentSongList());
        assertFalse(testSongList == mps.getInstance().getCurrentSongList());

        mps.getInstance().setCurrentSongList(testSongList);

        assertTrue(testSongList == mps.getInstance().getCurrentSongList());
        assertFalse(songList == mps.getInstance().getCurrentSongList());
    }

    @Test
    public void getNextSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState();
        mps.getInstance().setCurrentSongList(songList);
        List<Song> testList = mps.getInstance().getCurrentSongList();

        mps.getInstance().setCurrentSong(testList.get(0)); // initial song is first item in the initial queue

        assertEquals(songList.get(0), mps.getInstance().getCurrentlyPlayingSong());
        mps.getInstance().setCurrentSong(mps.getInstance().getNextSong());
        assertEquals(songList.get(1), mps.getInstance().getCurrentlyPlayingSong());

    }

    @Test
    public void getPreviousSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState();
        mps.getInstance().setCurrentSongList(songList);
        List<Song> testList = mps.getInstance().getCurrentSongList();

        mps.getInstance().setCurrentSong(testList.get(3)); // initial song is last item in the initial queue

        assertEquals(songList.get(3), mps.getInstance().getCurrentlyPlayingSong());
        mps.getInstance().setCurrentSong(mps.getInstance().getPreviousSong());
        assertEquals(songList.get(2), mps.getInstance().getCurrentlyPlayingSong());

    }

    @Test
    public void getSetPausedPositionTest() {
        MusicPlayerState mps = new MusicPlayerState();

        mps.getInstance().setPausedPosition(30);
        assertEquals(mps.getInstance().getPausedPosition(),30);
        assertFalse(mps.getInstance().getPausedPosition() == 0);
    }

    @Test
    public void songPlayingTest() {
        // 4 commands will be functions will be used here:
        // setIsSongPlaying, isSongPlaying
        // setIsSongPaused, isSongPaused
        MusicPlayerState mps = new MusicPlayerState();

        mps.getInstance().setIsSongPlaying(true);
        assertTrue(mps.getInstance().isSongPlaying() == true);
        assertFalse(mps.getInstance().isSongPlaying() == false);

        mps.getInstance().setIsSongPaused(true);
        assertTrue(mps.getInstance().isSongPaused() == true);
        assertFalse(mps.getInstance().isSongPaused() == false);

        mps.getInstance().setIsSongPlaying(false);
        assertTrue(mps.getInstance().isSongPlaying() == false);
        assertFalse(mps.getInstance().isSongPlaying() == true);

        mps.getInstance().setIsSongPaused(false);
        assertTrue(mps.getInstance().isSongPaused() == false);
        assertFalse(mps.getInstance().isSongPaused() == true);
    }

    @Test
    public void updateSongTest() {
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState();
        mps.getInstance().setCurrentSongList(songList);

        mps.getInstance().setCurrentSong(mps.getInstance().getCurrentSongList().get(1));
        mps.getInstance().updateNextSong();
        mps.getInstance().updatePreviousSong();

        assertTrue(mps.getInstance().getNextSong() == songList.get(2));
        assertTrue(mps.getInstance().getPreviousSong() == songList.get(0));

        assertFalse(mps.getInstance().getNextSong() == songList.get(3));
        assertFalse(mps.getInstance().getPreviousSong() == songList.get(1));
    }

    @Test
    public void subscribeToSongChangeTest() {
        MusicPlayerState mps = new MusicPlayerState();
        Observer mockObserver = Mockito.mock(Observer.class);

        mps.getInstance().subscribeToSongChange(mockObserver);
    }

    @Test
    public void subscribeToPlayModeChangeTest()
    {
        MusicPlayerState mps = new MusicPlayerState();
        Observer mockObserver = Mockito.mock(Observer.class);

        mps.getInstance().subscribeToPlayModeChange(mockObserver);
    }

    @Test
    public void getMusicPlayerStateTest()
    {
        MusicPlayerState mps = new MusicPlayerState();

        mps.getInstance().setCurrentSong(a);

        System.out.println(mps.getInstance().getMusicPlayerState());
        assertEquals( "Current song not null song is null song paused: false song playing falsevariable currentSongname is null",

        mps.getInstance().getMusicPlayerState());
    }

    @Test
    public void getPlayModeTest()
    {
        MusicPlayerState mps = new MusicPlayerState();
        List<Song> songList = Arrays.asList(a, b, c, d);

        mps.getInstance().setCurrentSongList(songList);

        assertEquals("", mps.getInstance().getPlayMode());

        mps.getInstance().setShuffleMode(true);
        mps.getInstance().updateNextSong();
        mps.getInstance().setRepeatMode(true);

        assertEquals("Shuffle on - Repeat on", mps.getInstance().getPlayMode());

        mps.getInstance().setShuffleMode(false);
        mps.getInstance().setRepeatMode(false);

        assertEquals("", mps.getInstance().getPlayMode());

    }

    @Test
    public void testCurrentlyPlayingSongNameMutator()
    {
        MusicPlayerState mps = new MusicPlayerState();

        mps.getInstance().setCurrentSong(a);
        mps.getInstance().setCurrentSongPlayingName("test");

        assertEquals("test", mps.getInstance().getCurrentlyPlayingSongName());
    }


}
