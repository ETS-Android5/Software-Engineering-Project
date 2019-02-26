package comp3350.breadtunes.tests.business;

// imports
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
import comp3350.breadtunes.objects.Song;

public class MusicPlayerStateTest
{
    Song a = new Song();
    Song b = new Song();
    Song c = new Song();
    Song d = new Song();

    MusicPlayerState testTarget;
    List<Song> mockSongList;

    @Before
    public void setup()
    {
        mockSongList = Arrays.asList(a, b, c);
        testTarget = new MusicPlayerState(mockSongList);
    }

    @Test
    public void getCurrentlyPlayingSongTest()// done
    {
        System.out.print("\nStarting getCurrentlyPlayingSongTest");

        testTarget.setCurrentSong(a);
        assertTrue(a == testTarget.getCurrentlyPlayingSong());
        assertFalse(b == testTarget.getCurrentlyPlayingSong());

        System.out.println("\nFinished getCurrentPlayingSongTest");
    }// getCurrentlyPlayingSongTest

    @Test
    public void setCurrentSongTest()// done
    {
        System.out.print("\nStarting setCurrentSongTest");

        for(int i = 0; i < mockSongList.size()-1; i++)
        {
            testTarget.setCurrentSong(mockSongList.get(i));
            assertTrue(testTarget.getCurrentlyPlayingSong() == mockSongList.get(i));
        }
        assertFalse(testTarget.getCurrentlyPlayingSong() == mockSongList.get(0));

        System.out.println("\nFinished setCurrentSongTest");
    }// setCurrentSongTest

    @Test
    public void getCurrentSongList()// done
    {
        System.out.print("\nStarting getCurrentSongList");

        assertTrue(testTarget.getCurrentSongList() == mockSongList);
        assertFalse(testTarget.getCurrentSongList() == null);

        System.out.println("\nFinished getCurrentSongListTest");
    }

    @Test
    public void setCurrentSongListTest()
    {
        System.out.print("\nStarting setCurrentSongListTest");
        List<Song> testSongList = Arrays.asList(a,b,c,d);

        assertTrue(mockSongList == testTarget.getCurrentSongList());
        assertFalse(testSongList == testTarget.getCurrentSongList());

        testTarget.setCurrentSongList(testSongList);

        assertTrue(testSongList == testTarget.getCurrentSongList());
        assertFalse(mockSongList == testTarget.getCurrentSongList());

        System.out.println("\nFinished setCurrentSongListTest");
    }// getCurrentSongListTest

    @Test
    public void getNextSongTest()// done
    {
        System.out.print("\nStarting getNextSongTest");

        List<Song> testList = testTarget.getCurrentSongList();

        testTarget.setCurrentSong(testList.get(0)); // initial song is first item in the initial queue
        for(int i = 0; i < mockSongList.size(); i++)
        {
            assertEquals(mockSongList.get(i), testTarget.getCurrentlyPlayingSong());
            testTarget.setCurrentSong(testTarget.getNextSong());
        }
        assertNull(testTarget.getCurrentlyPlayingSong());

        System.out.println("\nFinished getNextSongTest");
    }// getNextSongTest

    @Test
    public void getPreviousSongTest()// done
    {
        System.out.print("\nStarting getPreviousSongTest");

        List<Song> testList = testTarget.getCurrentSongList();

        testTarget.setCurrentSong(testList.get(2)); // initial song is last item in the initial queue
        for(int i = mockSongList.size()-1; i > -1; i--)
        {
            assertEquals(mockSongList.get(i), testTarget.getCurrentlyPlayingSong());
            testTarget.setCurrentSong(testTarget.getPreviousSong());
        }
        assertNull(testTarget.getCurrentlyPlayingSong());

        System.out.println("\nFinished getPreviousSongTest");
    }// getPreviousSongTest

    @Test
    public void getSetPausedPositionTest() // done
    {
        System.out.print("\nStarting getSetPausedPositionTest");

        testTarget.setPausedPosition(30);
        assertEquals(testTarget.getPausedPosition(),30);
        assertFalse(testTarget.getPausedPosition() == 0);

        System.out.println("\nFinished getSetPausedPositionTest");
    }// getSetPausedPositionTest

    @Test
    public void songPlayingTest() // done
    {
        // 4 commands will be functions will be used here:
        // setIsSongPlaying, isSongPlaying
        // setIsSongPaused, isSongPaused

        System.out.print("\nStarting songPlayingTest");

        testTarget.setIsSongPlaying(true);
        assertTrue(testTarget.isSongPlaying() == true);
        assertFalse(testTarget.isSongPlaying() == false);

        testTarget.setIsSongPaused(true);
        assertTrue(testTarget.isSongPaused() == true);
        assertFalse(testTarget.isSongPaused() == false);

        testTarget.setIsSongPlaying(false);
        assertTrue(testTarget.isSongPlaying() == false);
        assertFalse(testTarget.isSongPlaying() == true);

        testTarget.setIsSongPaused(false);
        assertTrue(testTarget.isSongPaused() == false);
        assertFalse(testTarget.isSongPaused() == true);

        System.out.println("\nFinished songPlayingTest");
    }// songPlayingTest

    @Test
    public void updateSongTest() // done
    {
        System.out.print("\nStarting updateSongTest");

        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(1));
        testTarget.updateNextSong();
        testTarget.updatePreviousSong();

        assertTrue(testTarget.getNextSong() == mockSongList.get(2));
        assertTrue(testTarget.getPreviousSong() == mockSongList.get(0));

        assertFalse(testTarget.getNextSong() == mockSongList.get(0));
        assertFalse(testTarget.getPreviousSong() == mockSongList.get(1));

        System.out.println("\nFinished updateSongTest");
    }// updateSongTest

    @Test
    public void subscribeToSongChangeTest()
    {
        System.out.print("\nStarting subscribeToSongChangeTest");

        Observer mockObserver = Mockito.mock(Observer.class);

        testTarget.subscribeToSongChange(mockObserver);

        System.out.println("\nFinished subscribeToSongChangeTest");
    }
}// MusicPlayerStateTest
