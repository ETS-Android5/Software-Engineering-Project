package comp3350.breadtunes.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.hsql.SongPersistenceHSQL;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.testhelpers.mocks.MockSongs;
import comp3350.breadtunes.testhelpers.values.BreadTunesIntegrationTests;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class MusicPlayerStateIT extends TestLogger {
    MusicPlayerState testTarget;
    List<Song> testList;

    @Before
    public void setup(){
        File realDirectory = new File(BreadTunesIntegrationTests.realDatabasePath);
        File copyDirectory = new File(BreadTunesIntegrationTests.copyDatabasePath);
        ServiceGateway.getDatabaseManager().initializeDatabase(realDirectory, mock(Logger.class));
        ServiceGateway.getDatabaseManager().createAndUseDatabaseCopy(copyDirectory);

        // Create LookUpSongs class
        final SongPersistenceHSQL songPersistence = new SongPersistenceHSQL();
        testTarget = new MusicPlayerState(songPersistence, mock(Logger.class));
        testList = songPersistence.getAll();
    }

    @Test
    public void getCurrentlyPlayingSongTest() {
        Song testSong = testList.get(0);

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
        testTarget.setCurrentSongList(testList);
        assertEquals(testList, testTarget.getCurrentSongList());
    }

    @Test
    public void setCurrentSongListTest() {
        testTarget.setCurrentSongList(testList);
        assertEquals(testList, testTarget.getCurrentSongList());
    }

    @Test
    public void getNextSongTest() {
        List<Song> testSongList = testTarget.getCurrentSongList();

        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(0)); // set currently playing song as first item in songlist
        assertEquals(testSongList.get(0), testTarget.getCurrentlyPlayingSong());

        testTarget.setCurrentSong(testTarget.getNextSong()); // get next song in queue
        assertEquals(testSongList.get(1), testTarget.getCurrentlyPlayingSong());
    }

    @Test
    public void getPreviousSongTest() {
        List<Song> testSongList = testTarget.getCurrentSongList();

        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(2)); // set currently playing song as third item in songlist
        assertEquals(testSongList.get(2), testTarget.getCurrentlyPlayingSong());

        testTarget.setCurrentSong(testTarget.getPreviousSong()); // get previous song in the list
        assertEquals(testSongList.get(1), testTarget.getCurrentlyPlayingSong());
    }

    @Test
    public void pausedPositionTest() {
        testTarget.setPausedPosition(30);
        assertEquals(30, testTarget.getPausedPosition());
    }

    @Test
    public void currentSongPlayingNameTest(){
        Song testSong = testTarget.getCurrentSongList().get(0);
        testTarget.setCurrentSong(testSong);

        assertEquals("Bloch Prayer", testTarget.getCurrentlyPlayingSongName());
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
        Song testSong1 = testList.get(0);
        Song testSong2 = testList.get(1);
        Song testSong3 = testList.get(2);

        List<Song> testList = Arrays.asList(testSong1, testSong2, testSong3);

        testTarget.setCurrentSongList(testList);

        testTarget.setCurrentSong(testTarget.getCurrentSongList().get(1));
        testTarget.updateNextSong();
        testTarget.updatePreviousSong();

        assertEquals(testList.get(2), testTarget.getNextSong());
        assertEquals(testList.get(0), testTarget.getPreviousSong());
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
        Song testSong = testTarget.getCurrentSongList().get(2);

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
        Song testSong = testTarget.getCurrentSongList().get(1);

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

    @After
    public void tearDown(){
        ServiceGateway.getDatabaseManager().destroyTempDatabaseAndCloseConnection();
    }
}
