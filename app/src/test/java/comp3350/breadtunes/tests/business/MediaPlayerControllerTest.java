package comp3350.breadtunes.tests.business;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.presentation.MediaController.MediaPlayerController;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.interfaces.MediaManager;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.tests.mocks.MockSongs;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MediaPlayerControllerTest extends TestLogger {
    MediaPlayerController testTarget;
    MusicPlayerState appState;
    List<Song> mockSongList = new ArrayList<>();
    Context context = mock(Context.class);
    MediaManager mockManager = mock(MediaManager.class);

    @Before
    public void setup()
    {
        mockSongList = MockSongs.getMockSongList();
        appState = new MusicPlayerState();
        testTarget = new MediaPlayerController();
    }

    @Test
    public void playExistingSongTest()
    {
        //testTarget.playSong(mockSongList.get(0), 1);
        testTarget.playSong(mockSongList.get(0), 1, any(Context.class));
        verify(mockManager, times(1)).startPlayingSong(any(Context.class), anyInt());
    }

    @Test
    public void playNonexistentSongTest() {
        //testTarget.playSong(new Song.Builder().build(), 0);
        testTarget.playSong(new Song.Builder().build(), 0, context);
        verify(mockManager, times(0)).startPlayingSong(any(Context.class), anyInt());
    }

    @Test
    public void pauseNoPlayingSongTest()
    {
        testTarget.pauseSong();
        verify(mockManager, times(0)).pausePlayingSong();
    }

    @Test
    public void pausePlayingSongTest()
    {
        //testTarget.playSong(mockSongList.get(0), 1);
        testTarget.playSong(new Song.Builder().build(), 1, context);
        testTarget.pauseSong();
        verify(mockManager, times(1)).pausePlayingSong();
    }


    @Test
    public void playNextSongTest()
    {
        //case we are not playing
        assertEquals("no song currently playing", testTarget.playNextSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);

        //case we are last on the list and no next song in the list
        //testTarget.playSong(mockSongList.get(3),1);
        testTarget.playSong(mockSongList.get(3),1, context);

        assertEquals("no next song", testTarget.playNextSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);
    }// playNextSongTest

    @Test
    public void playPreviousSongTest()
    {
        //case we are not playing
        assertEquals("no song currently playing", testTarget.playPreviousSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);

        //case we are first on the list and no previous song in the list
        //testTarget.playSong(mockSongList.get(0),1);
        testTarget.playSong(mockSongList.get(0),1,context);

        assertEquals("no previous song", testTarget.playPreviousSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);
    }// playPreviousSongTest


    @Test
    public void resumeSongTest() {
        //testTarget.playSong(mockSongList.get(0), 1);
        testTarget.playSong(mockSongList.get(0), 1, context);
        testTarget.pauseSong();
        testTarget.resumeSong(1);
        verify(mockManager, times(1)).resumePlayingSong();
    }

}
