package comp3350.breadtunes.tests.business;

// imports
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import comp3350.breadtunes.business.HomeActivityHelper;
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
    HomeActivityHelper guiUpdater = Mockito.mock(HomeActivityHelper.class);

    @Test
    public void getCurrentlyPlayingSongTest()// done
    {
        System.out.println("Starting getCurrentlyPlayingSongTest\n");
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

        mps.setCurrentSong(a);
        assertTrue(a == mps.getCurrentlyPlayingSong());
        assertFalse(b == mps.getCurrentlyPlayingSong());

        System.out.println("Finished getCurrentPlayingSongTest\n");
    }// getCurrentlyPlayingSongTest

    @Test
    public void setCurrentSongTest()// done
    {
        System.out.println("Starting setCurrentSongTest\n");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

        for(int i = 0; i < songList.size()-1; i++)
        {
            mps.setCurrentSong(songList.get(i));
            assertTrue(mps.getCurrentlyPlayingSong() == songList.get(i));
        }
        assertFalse(mps.getCurrentlyPlayingSong() == songList.get(0));

        System.out.println("Finished setCurrentSongTest\n");
    }// setCurrentSongTest

    @Test
    public void getCurrentSongList()// done
    {
        System.out.println("Starting getCurrentSongList\n");
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

        assertTrue(mps.getCurrentSongList() == songList);
        assertFalse(mps.getCurrentSongList() == null);
        System.out.println("Finished getCurrentSongListTest\n");
    }

    @Test
    public void setCurrentSongListTest()
    {
        System.out.println("Starting setCurrentSongListTest\n");

        List<Song> songList = Arrays.asList(a, b, c, d);
        List<Song> testSongList = Arrays.asList(a,b,c);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

        assertTrue(songList == mps.getCurrentSongList());
        assertFalse(testSongList == mps.getCurrentSongList());

        mps.setCurrentSongList(testSongList);

        assertTrue(testSongList == mps.getCurrentSongList());
        assertFalse(songList == mps.getCurrentSongList());

        System.out.println("Finished setCurrentSongListTest\n");
    }// getCurrentSongListTest

    @Test
    public void getNextSongTest()// done
    {
        System.out.println("Starting getNextSongTest\n");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);
        List<Song> testList = mps.getCurrentSongList();

        mps.setCurrentSong(testList.get(0)); // initial song is first item in the initial queue
        for(int i = 0; i < songList.size(); i++)
        {
            assertEquals(songList.get(i), mps.getCurrentlyPlayingSong());
            mps.setCurrentSong(mps.getNextSong());
        }
        assertNull(mps.getCurrentlyPlayingSong());

        System.out.println("Finished getNextSongTest\n");
    }// getNextSongTest

    @Test
    public void getPreviousSongTest()// done
    {
        System.out.println("Starting getPreviousSongTest\n");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);
        List<Song> testList = mps.getCurrentSongList();

        mps.setCurrentSong(testList.get(3)); // initial song is last item in the initial queue
        for(int i = songList.size()-1; i > -1; i--)
        {
            assertEquals(songList.get(i), mps.getCurrentlyPlayingSong());
            mps.setCurrentSong(mps.getPreviousSong());
        }
        assertNull(mps.getCurrentlyPlayingSong());

        System.out.println("Finished getPreviousSongTest\n");
    }// getPreviousSongTest

    @Test
    public void getSetPausedPositionTest() // done
    {
        System.out.println("Starting getSetPausedPositionTest\n");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

        mps.setPausedPosition(30);
        assertEquals(mps.getPausedPosition(),30);
        assertFalse(mps.getPausedPosition() == 0);

        System.out.println("Finished getSetPausedPositionTest\n");
    }// getSetPausedPositionTest

    @Test
    public void songPlayingTest() // done
    {
        // 4 commands will be functions will be used here:
        // setIsSongPlaying, isSongPlaying
        // setIsSongPaused, isSongPaused

        System.out.println("Starting songPlayingTest\n");
        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

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

        System.out.println("Finished songPlayingTest\n");
    }// songPlayingTest

    @Test
    public void updateSongTest() // done
    {
        System.out.println("Starting updateSongTest\n");

        List<Song> songList = Arrays.asList(a, b, c, d);
        MusicPlayerState mps = new MusicPlayerState(songList, guiUpdater);

        mps.setCurrentSong(mps.getCurrentSongList().get(1));
        mps.updateNextSong();
        mps.updatePreviousSong();

        assertTrue(mps.getNextSong() == songList.get(2));
        assertTrue(mps.getPreviousSong() == songList.get(0));

        assertFalse(mps.getNextSong() == songList.get(3));
        assertFalse(mps.getPreviousSong() == songList.get(1));

        System.out.println("Finished updateSongTest\n");
    }


}// MusicPlayerStateTest
