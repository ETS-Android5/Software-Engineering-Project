package comp3350.breadtunes;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.presentation.HomeActivity;
import comp3350.breadtunes.services.ServiceGateway;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest


// https://github.com/codepath/android_guides/wiki/UI-Testing-with-Espresso
// https://code.cs.umanitoba.ca/comp3350-summer2018/sample/blob/master/app/src/androidTest/java/comp3350/srsys/CoursesTest.java


public class PlayMusicFeatureTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);
    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    private final int FIRST_SONG = 0;
    private final int SECOND_SONG = 1;

    private Song initialSong;
    private Song secondSong;
    private List<Song> songList;

    @Before
    public void setup(){
        songList = ServiceGateway.getMusicPlayerState().getCurrentSongList();
        assertTrue(songList.size() > 1);
        initialSong = songList.get(FIRST_SONG);
        secondSong = songList.get(SECOND_SONG);

    }

    @Test
    public void playPauseSong(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //click on first song

        //test playing
        assertEquals(initialSong.getName(), ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName());  //playing first song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //check the gui shows the song playing
        String songName = initialSong.getName();
        onView(withId(R.id.song_name)).check(matches(withText(containsString(songName))));

        //test the pause
        onView(withId(R.id.play_pause)).perform(click());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPaused());
        assertTrue(ServiceGateway.getMusicPlayerState().getPausedPosition() > 0);
        onView(withId(R.id.song_name)).check(matches(withText(containsString(songName))));


        //test resume
        onView(withId(R.id.play_pause)).perform(click());
        assertEquals(initialSong, ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong());  //playing first song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
        onView(withId(R.id.song_name)).check(matches(withText(containsString(songName))));
    }

    @Test
    public void playNextSong(){


        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //click first song

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying()); //check music player state reacting correctly
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        onView(withId(R.id.play_next_button)).perform(click()); //play next song
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), secondSong);
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);

        //check the gui shows the next song
        String songName = secondSong.getName();
        onView(withId(R.id.song_name)).check(matches(withText(containsString(songName))));

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

    }

    @Test
    public void playPreviousSong(){

        //song is now playing
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(SECOND_SONG).perform(click()); //playing the second song!!

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());//check music player state reacting correctly
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        onView(withId(R.id.play_previous_button)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), secondSong);

        //check the gui shows the next song
        String songName = initialSong.getName();
        onView(withId(R.id.song_name)).check(matches(withText(containsString(songName))));

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

    }

    @Test
    public void playShuffle(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //go to the now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        String songName = initialSong.getName();
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));

        onView(withId(R.id.shuffle_button)).perform(click()); //activate shuffle
        onView(withId(R.id.play_next_button_nowplaying_fragment)).perform(click());
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());


        onView(withId(R.id.song_name_nowplaying_fragment)).check
                (matches(withText(containsString(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName()))));

    }

    @Test
    public void playRepeat(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //go to the now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        onView(withId(R.id.repeat_button)).perform(click());

        //calculate how much time we must wait to see if the repeat works
        assertEquals(initialSong, ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong());

        SongDuration duration = ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getDuration();

        int waitTime = ((duration.getHours() * 60 * 60) + (duration.getMinutes()*60) + (duration.getSeconds())) * 1000;

        try {
            Thread.sleep(waitTime + 10000); //wait for song to finish
        }catch(Exception e){

        }

        //verify that the song repeats
        assertEquals(initialSong, ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
        String songName = initialSong.getName();
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));

        if(ServiceGateway.getMusicPlayerState().getShuffleMode())
            onView(withId(R.id.shuffle_button)).perform(click());

        //verify expected behaviour, when pressing next and shuffle is on, it should play the next song
        onView(withId(R.id.play_next_button_nowplaying_fragment)).perform(click());
        assertEquals(secondSong, ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
    }

    //now test play pause, play next and play previous in the now playing fragment
    @Test
    public void playPauseSongNowPlayingFragment(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //click on first song

        //test playing
        assertEquals(initialSong.getName(), ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName());  //playing first song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //go to now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        String songName = initialSong.getName();
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));

        //test the pause in now playing fragment
        onView(withId(R.id.play_pause_button)).perform(click());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPaused());
        assertTrue(ServiceGateway.getMusicPlayerState().getPausedPosition() > 0);
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));


        //test resume in now playing fragment
        onView(withId(R.id.play_pause_button)).perform(click());
        assertEquals(initialSong, ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong());  //playing first song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));
    }

    @Test
    public void playNextSongNowPlayingFragment(){


        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //click first song

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying()); //check music player state reacting correctly
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //go to now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        onView(withId(R.id.play_next_button_nowplaying_fragment)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), secondSong);
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);

        String songName = secondSong.getName();
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

    }

    @Test
    public void playPreviousSongNowPlayingFragment(){

        //song is now playing
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(SECOND_SONG).perform(click()); //playing the second song!!

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());//check music player state reacting correctly
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //go to now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        onView(withId(R.id.play_previous_button_nowplaying_fragment)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), secondSong);

        String songName = initialSong.getName();
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));

        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

    }

}
