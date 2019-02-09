package comp3350.breadtunes.tests.business;

import android.content.Context;
import android.provider.MediaStore;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.business.MediaPlayerController;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.interfaces.MediaManager;
import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MediaPlayerControllerTest
{
    MediaPlayerController testTarget;
    MusicPlayerState appState;
    List<Song> mockSongList = new ArrayList<>();
    Song song1, song2, song3;
    Context context = mock(Context.class);
    MediaManager mockManager = mock(MediaManager.class);

    @Before
    public void setup()
    {
        song1 = new Song("Bloch Prayer",1, new SongDuration(0, 0),
                new Artist("Artist 1", new ArrayList<Album>()),
                new Album("Album 1", new ArrayList<Song>()), "res/raw/prayer.mp3");
        song2 = new Song("Haydn Adagio", 2, new SongDuration(0, 0),
                new Artist("Artist 2", new ArrayList<Album>()),
                new Album("Album 2", new ArrayList<Song>()), "res/raw/adagio.mp3");
        song3 = new Song("Tchaikovsky Nocturne", 3, new SongDuration(0, 0),
                new Artist("Artist 3", new ArrayList<Album>()),
                new Album("Album 3", new ArrayList<Song>()), "res/raw/nocturne.mp3");

        mockSongList.add(new Song("Bloch Prayer",1, new SongDuration(0, 0),
                new Artist("Artist 1", new ArrayList<Album>()),
                new Album("Album 1", new ArrayList<Song>()), "res/raw/prayer.mp3"));
        mockSongList.add(new Song("Haydn Adagio", 2, new SongDuration(0, 0),
                new Artist("Artist 2", new ArrayList<Album>()),
                new Album("Album 2", new ArrayList<Song>()), "res/raw/adagio.mp3"));


        appState = new MusicPlayerState(mockSongList);
        testTarget = new MediaPlayerController(context, appState, mockManager);
    }

    @Test
    public void playSongTest()
    {
        System.out.print("\nStarting playSongTest");

        assertEquals(testTarget.playSong(song1, 1), "Playing " + song1.getName());
        verify(mockManager, times(1)).startPlayingSong(context, 1);

        assertEquals(testTarget.playSong(song3, 0), "Failed to find resource");
        verify(mockManager, times(1)).startPlayingSong(context, 1);// times(1) is 1 because we already ran it once above

        System.out.println("\nEnding playSongTest");
    }// playSongTest

    @Test
    public void pauseSongTest()
    {
        System.out.print("\nStarting pauseSongTest");

        //case we are currently paused
        testTarget.pauseSong(); // initialize pause
        assertEquals("Can not pause, no song playing", testTarget.pauseSong());
        verify(mockManager, times(0)).pausePlayingSong();

        //play something
        testTarget.playSong(song1, 1);
        assertEquals("paused song "+song1.getName(), testTarget.pauseSong());
        verify(mockManager, times(1)).pausePlayingSong();

        System.out.println("\nEnding pauseSongTest");
    }// pauseSongTest

    @Test
    public void playNextSongTest()
    {
        System.out.print("\nStarting playNextSongTest");

        //case we are not playing
        assertEquals("no song currently playing", testTarget.playNextSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);

        //case we are last on the list and no next song in the list
        testTarget.playSong(mockSongList.get(1),1);
        assertEquals("no next song", testTarget.playNextSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);

        System.out.println("\nEnding playNextSongTest");
    }// playNextSongTest

    @Test
    public void playPreviousSongTest()
    {
        System.out.print("\nStarting playPreviousSongTest");

        //case we are not playing
        assertEquals("no song currently playing", testTarget.playPreviousSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);

        //case we are first on the list and no previous song in the list
        testTarget.playSong(mockSongList.get(0),1);
        assertEquals("no previous song", testTarget.playPreviousSong(context));
        verify(mockManager,times(0)).startPlayingSong(context, 0);

        System.out.println("\nEnding playPreviousSongTest");
    }// playPreviousSongTest

    @Test
    public void resumeSongTest()
    {
        System.out.print("\nStarting resumeSongTest");

        testTarget.playSong(song1, 1);// play something
        assertEquals("Can not resume , no song is paused", testTarget.resumeSong(0));
        verify(mockManager, times(0)).resumePlayingSong();

        testTarget.pauseSong();// pause
        assertEquals("Response successful", testTarget.resumeSong(0));
        verify(mockManager, times(1)).resumePlayingSong();

        System.out.println("\nEnding resumeSongTest");
    }// resumeSongTest

    @Test
    public void releaseMediaPlayerTest()
    {
        System.out.print("\nreleaseMediaPlayerTest");

        testTarget.releaseMediaPlayer();
        verify(mockManager,times(1)).close();

        System.out.println("\nreleaseMediaPlayerTest");
    }// releaseMediaPlayerTest
}// MediaPlayerControllerTest
