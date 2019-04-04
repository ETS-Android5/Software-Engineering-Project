package comp3350.breadtunes.tests.business;

import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

public class MusicPlayerStateTest extends TestLogger {

    SongPersistence mockSongPersistence = mock(SongPersistence.class);
    Logger mockLogger = mock(Logger.class);
    MusicPlayerState testTarget;

    @Before
    public void setup() {
        when(mockSongPersistence.getAll())
                .thenReturn(MockSongs.getMockSongList());

        testTarget = new MusicPlayerState(mockSongPersistence, mockLogger);
    }

    @Test
    public void getCurrentlyPlayingSongTest() {
        Song testSong = mock(Song.class);

        testTarget.setCurrentSong(testSong);
        assertEquals(testSong, testTarget.getCurrentlyPlayingSong());
    }

    @Test
    public void setCurrentSongTest() {
        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(0));
        assertEquals(testTarget.getCurrentSongList().get(0), testTarget.getCurrentlyPlayingSong());
    }

    @Test
    public void getCurrentSongList() {
        assertEquals(MockSongs.getMockSongList(), testTarget.getCurrentSongList());
    }

    @Test
    public void setCurrentSongListTest() {
        List<Song> testList1 = mock(List.class);

        assertEquals(MockSongs.getMockSongList(), testTarget.getCurrentSongList());
        testTarget.setCurrentSongList(testList1);
        assertEquals(testList1, testTarget.getCurrentSongList());
    }

    @Test
    public void getNextSongTest() {
        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(0)); // set currently playing song as first item in songlist
        assertEquals(MockSongs.getMockSongList().get(0), testTarget.getCurrentlyPlayingSong());

        testTarget.setCurrentSong(testTarget.getNextSong()); // get next song in queue
        assertEquals(MockSongs.getMockSongList().get(1), testTarget.getCurrentlyPlayingSong());

        testTarget.setShuffleMode(true);
        testTarget.setCurrentSong(testTarget.getNextSong());
    }

    @Test
    public void getPreviousSongTest() {
        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(2)); // set currently playing song as third item in songlist
        assertEquals(MockSongs.getMockSongList().get(2), testTarget.getCurrentlyPlayingSong());

        testTarget.setCurrentSong(testTarget.getPreviousSong()); // get previous song in the list
        assertEquals(MockSongs.getMockSongList().get(1), testTarget.getCurrentlyPlayingSong());
    }

    @Test
    public void pausedPositionTest() {
        testTarget.setPausedPosition(30);
        assertEquals(30, testTarget.getPausedPosition());
    }

    @Test
    public void currentSongPlayingNameTest(){
        Song testSong = mock(Song.class);
        testTarget.setCurrentSong(testSong);

        testTarget.setCurrentSongPlayingName("test");
        assertEquals("test", testTarget.getCurrentlyPlayingSongName());
    }

    @Test
    public void songPlayingPausedTest() {
        // song is playing
        testTarget.setIsSongPlaying(true);
        testTarget.setIsSongPaused(false);
        assertTrue(testTarget.isSongPlaying());
        assertFalse(testTarget.isSongPaused());

        // song is paused
        testTarget.setIsSongPlaying(false);
        testTarget.setIsSongPaused(true);
        assertFalse(testTarget.isSongPlaying());
        assertTrue(testTarget.isSongPaused());
    }

    @Test
    public void updateSongTest() {
        Song testSong1 = mock(Song.class);
        Song testSong2 = mock(Song.class);
        Song testSong3 = mock(Song.class);

        List<Song> testList = Arrays.asList(testSong1, testSong2, testSong3);

        testTarget.setCurrentSongList(testList);

        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(1));
        testTarget.updateNextSong();
        testTarget.updatePreviousSong();

        assertEquals(testList.get(2), testTarget.getNextSong());
        assertEquals(testList.get(0), testTarget.getPreviousSong());

        //shuffle mode
    }

    @Test
    public void getPlayModeTest(){
        // initial state, repeat & shuffle off
        assertEquals("", testTarget.getPlayMode());

        testTarget.setShuffleMode(true);
        testTarget.updateNextSong();
        testTarget.setRepeatMode(true);

        assertEquals("Shuffle on - Repeat on", testTarget.getPlayMode());

        testTarget.setShuffleMode(false);
        testTarget.setRepeatMode(false);

        assertEquals("", testTarget.getPlayMode());
    }

    @Test
    public void getParentalControlStatusTest(){
        // this will test both getParentalControlStatus and getParentalControlModeOn

        testTarget.turnParentalControlOn(false);
        assertEquals("Parental Control Mode Off", testTarget.getParentalControlStatus());
        assertEquals(false, testTarget.getParentalControlModeOn());

        testTarget.turnParentalControlOn(true);
        assertEquals("Parental Control Mode On", testTarget.getParentalControlStatus());
        assertEquals(true, testTarget.getParentalControlModeOn());
    }

    @Test
    public void addSongToPlayNextTest(){
        Song testSong = mock(Song.class);

        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(0)); // make sure we are first in the queue
        testTarget.addSongToPlayNext(testSong);

        assertEquals(testSong, testTarget.getNextSong());
    }

    @Test
    public void clearQueueTest(){
        testTarget.clearQueue();
        assertEquals(0, testTarget.getQueueSize());
    }

    @Test
    public void addToQueueTest(){
        Song testSong = mock(Song.class);

        testTarget.addToQueue(testSong);
        // set current song to last song in the current list
        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(testTarget.getCurrentSongList().size()-1));
        // check if the next song has been added to the end of the list
        assertEquals(testSong, testTarget.getNextSong());
    }

    @Test
    public void getQueueSongNamesTest(){
        String[] queueSongNames = testTarget.getQueueSongNames();

        for(int i = 0; i < queueSongNames.length; i++)
        {assertEquals(testTarget.getCurrentSongList().get(i).getName(), queueSongNames[i]);}

    }
}
