package comp3350.breadtunes.tests.business;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.business.MediaPlayerController;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;
import static junit.framework.Assert.*;
import static junit.framework.TestCase.*;

public class MediaPlayerControllerTest
{
    MediaPlayerController testTarget;
    MusicPlayerState appState;
    List<Song> mockSongList = new ArrayList<>();
    Song song1, song2;


    @Before
    public void setup()
    {
        song1 = new Song("Bloch Prayer",1, new SongDuration(0, 0),
                new Artist("Artist 1", new ArrayList<Album>()),
                new Album("Album 1", new ArrayList<Song>()), "res/raw/prayer.mp3");
        song2 = new Song("Haydn Adagio", 2, new SongDuration(0, 0),
                new Artist("Artist 2", new ArrayList<Album>()),
                new Album("Album 2", new ArrayList<Song>()), "res/raw/adagio.mp3");

        mockSongList.add(new Song("Bloch Prayer",1, new SongDuration(0, 0),
                new Artist("Artist 1", new ArrayList<Album>()),
                new Album("Album 1", new ArrayList<Song>()), "res/raw/prayer.mp3"));
        mockSongList.add(new Song("Haydn Adagio", 1, new SongDuration(0, 0),
                new Artist("Artist 2", new ArrayList<Album>()),
                new Album("Album 2", new ArrayList<Song>()), "res/raw/adagio.mp3"));
        mockSongList.add(new Song("Tchaikovsky Nocturne", 1, new SongDuration(0, 0),
                new Artist("Artist 3", new ArrayList<Album>()),
                new Album("Album 3", new ArrayList<Song>()), "res/raw/nocturne.mp3"));

        appState = new MusicPlayerState(mockSongList,null);
        testTarget = new MediaPlayerController(new MockContext(), appState);
    }

    @Test
    public void playSongTest()
    {
        System.out.println("\nStarting playSongTest");

        assertEquals(testTarget.playSong(song1, 0), "Failed to find resource");
        assertEquals(testTarget.playSong(song1, 0), "Playing " + song1.getName());

        System.out.println("\nEnding playSongTest");
    }// playSongTest

    @Test
    public void pauseSongTest()
    {
        System.out.println("\nStarting pauseSongTest");

        testTarget.pauseSong(); // initialize pause

        //case we are currently paused
        assertEquals("Can not pause, no song playing", testTarget.pauseSong());

        //play something
        testTarget.playSong(song1, 0);
        assertEquals("paused song"+song1.getName(), testTarget.pauseSong());

        System.out.println("\nEnding pauseSongTest");
    }// pauseSongTest

    @Test
    public void playNextSongTest()
    {
        System.out.println("\nStarting playNextSongTest");
        //case we are not playing
        assertEquals("no song currently playing", testTarget.playNextSong(new MockContext()));

        //case we are playing first music in the list
        testTarget.playSong(mockSongList.get(0),0);
        assertEquals("playing " + mockSongList.get(1).getName(), testTarget.playNextSong(new MockContext()));

        //case we are last on the list and no next song in the list
        testTarget.playSong(mockSongList.get(2),0);
        assertEquals("no next song", testTarget.playNextSong(new MockContext()));

        System.out.println("\nEnding playNextSongTest");
    }// playNextSongTest

    @Test
    public void playPreviousSongTest()
    {
        System.out.println("\nStarting playPreviousSongTest");
        //case we are not playing
        assertEquals("no song currently playing", testTarget.playPreviousSong(new MockContext()));

        //case we are playing last music in the list
        testTarget.playSong(mockSongList.get(2),0);
        assertEquals("playing " + mockSongList.get(1).getName(), testTarget.playPreviousSong(new MockContext()));

        //case we are first on the list and no previous song in the list
        testTarget.playSong(mockSongList.get(0),0);
        assertEquals("no previous song", testTarget.playPreviousSong(new MockContext()));

        System.out.println("\nEnding playPreviousSongTest");
    }// playPreviousSongTest

    @Test
    public void resumeSongTest()
    {
        System.out.println("\nStarting resumeSongTest");

        testTarget.playSong(song1, 0);// play something
        assertEquals("Can not resume , no song is paused", testTarget.resumeSong(0));

        testTarget.pauseSong();// pause
        assertEquals("Response succesful", testTarget.resumeSong(0));

        System.out.println("\nEnding resumeSongTest");
    }// resumeSongTest



}// MediaPlayerControllerTest
