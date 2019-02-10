package comp3350.breadtunes.tests.business;

// imports
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import comp3350.breadtunes.business.MusicPlayerState;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import comp3350.breadtunes.objects.Song;

public class MusicPlayerStateTest
{
    Song a = new Song();
    Song b = new Song();
    Song c = new Song();
    Song d = new Song();

    @Test
    public void getCurrentlyPlayingSongTest()// done
    {
        System.out.print("\nStarting getCurrentlyPlayingSongTest");
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setCurrentSong(a);
        assertTrue(a == mps.getCurrentlyPlayingSong());
        assertFalse(b == mps.getCurrentlyPlayingSong());

        System.out.println("\nFinished getCurrentPlayingSongTest");
    }// getCurrentlyPlayingSongTest

    @Test
    public void setCurrentSongTest()// done
    {
        System.out.print("\nStarting setCurrentSongTest");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        for(int i = 0; i < songList.size()-1; i++)
        {
            mps.setCurrentSong(songList.get(i));
            assertTrue(mps.getCurrentlyPlayingSong() == songList.get(i));
        }
        assertFalse(mps.getCurrentlyPlayingSong() == songList.get(0));

        System.out.println("\nFinished setCurrentSongTest");
    }// setCurrentSongTest

    @Test
    public void getCurrentSongList()// done
    {
        System.out.print("\nStarting getCurrentSongList");
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        assertTrue(mps.getCurrentSongList() == songList);
        assertFalse(mps.getCurrentSongList() == null);
        System.out.println("\nFinished getCurrentSongListTest");
    }

    @Test
    public void setCurrentSongListTest()
    {
        System.out.print("\nStarting setCurrentSongListTest");

        List<Song> songList = Arrays.asList(a, b, c, d);
        List<Song> testSongList = Arrays.asList(a,b,c);
        MusicPlayerState mps = new MusicPlayerState(songList);

        assertTrue(songList == mps.getCurrentSongList());
        assertFalse(testSongList == mps.getCurrentSongList());

        mps.setCurrentSongList(testSongList);

        assertTrue(testSongList == mps.getCurrentSongList());
        assertFalse(songList == mps.getCurrentSongList());

        System.out.println("\nFinished setCurrentSongListTest");
    }// getCurrentSongListTest

    @Test
    public void getNextSongTest()// done
    {
        System.out.print("\nStarting getNextSongTest");

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

        System.out.println("\nFinished getNextSongTest");
    }// getNextSongTest

    @Test
    public void getPreviousSongTest()// done
    {
        System.out.print("\nStarting getPreviousSongTest");

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

        System.out.println("\nFinished getPreviousSongTest");
    }// getPreviousSongTest

    @Test
    public void getSetPausedPositionTest() // done
    {
        System.out.print("\nStarting getSetPausedPositionTest");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setPausedPosition(30);
        assertEquals(mps.getPausedPosition(),30);
        assertFalse(mps.getPausedPosition() == 0);

        System.out.println("\nFinished getSetPausedPositionTest");
    }// getSetPausedPositionTest

    @Test
    public void songPlayingTest() // done
    {
        // 4 commands will be functions will be used here:
        // setIsSongPlaying, isSongPlaying
        // setIsSongPaused, isSongPaused

        System.out.print("\nStarting songPlayingTest");
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

        System.out.println("\nFinished songPlayingTest");
    }// songPlayingTest

    @Test
    public void updateSongTest() // done
    {
        System.out.print("\nStarting updateSongTest");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList);

        mps.setCurrentSong(mps.getCurrentSongList().get(1));
        mps.updateNextSong();
        mps.updatePreviousSong();

        assertTrue(mps.getNextSong() == songList.get(2));
        assertTrue(mps.getPreviousSong() == songList.get(0));

        assertFalse(mps.getNextSong() == songList.get(3));
        assertFalse(mps.getPreviousSong() == songList.get(1));

        System.out.println("\nFinished updateSongTest");
    }
}// MusicPlayerStateTest
