package comp3350.breadtunes;
import android.app.Service;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.widget.EditText;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Deque;
import java.util.List;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.HomeActivity;
import comp3350.breadtunes.services.ServiceGateway;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotEquals;

import android.test.suitebuilder.annotation.LargeTest;

import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MusicQueueFeatureTest {


    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);
    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    private final int FIRST_SONG = 0;
    private final int SECOND_SONG = 1;
    private final int THIRD_SONG = 2;

    private Song initialSong;
    private Song secondSong;
    private Song thirdSong;
    private List<Song> songList;
    private final String ADD_TO_QUEUE = "Add to Queue";
    private final String ADD_TO_PLAY_NEXT = "Play Next";

    @Before
    public void setup(){
        songList = ServiceGateway.getMusicPlayerState().getCurrentSongList();
        assertTrue(songList.size() > 2);
        initialSong = songList.get(FIRST_SONG);
        secondSong = songList.get(SECOND_SONG);
        thirdSong = songList.get(THIRD_SONG);
        ServiceGateway.getMusicPlayerState().clearQueue();
    }

    @Test
    public void addToQueue(){
        //play the first song
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //click first song

        //add the third song to the queue
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(THIRD_SONG).perform(longClick());
        onView(withText(ADD_TO_QUEUE)).perform(click());

        assertEquals(ServiceGateway.getMusicPlayerState().getQueueSize(),1);
        assertEquals(thirdSong.getName(), ServiceGateway.getMusicPlayerState().getQueue().peek().getName());

        //play the next song - should play the song in the queue
        onView(withId(R.id.play_next_button)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName(), thirdSong.getName());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
        assertEquals(ServiceGateway.getMusicPlayerState().getQueueSize(), 0); //verify queue is empty, since we only added one song to q
    }

    //add songs to the queue and make sure the fragment displays the correct information
    @Test
    public void verifyQueueFragmentAddToQueue(){

        //add two songs to the queue
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(THIRD_SONG).perform(longClick());
        onView(withText(ADD_TO_QUEUE)).perform(click());


        int elementsAddedToQueue = 1;
        assertEquals(ServiceGateway.getMusicPlayerState().getQueueSize(), elementsAddedToQueue);

        //go to queue fragment
        onView(withId(R.id.queueList)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.queueResult)).check(matches(withText(thirdSong.getName())));

    }

    //make sure songs can be played from the fragment displaying the queued songs
    @Test
    public void verifyPlayFromQueueFragment(){
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(THIRD_SONG).perform(longClick());
        onView(withText(ADD_TO_QUEUE)).perform(click());
        //go to queue fragment
        onView(withId(R.id.queueList)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.queueResult)).atPosition(0).check(matches(withText(thirdSong.getName()))); //check displays correct song
        onData(anything()).inAdapterView(withId(R.id.queueResult)).atPosition(0).perform(click());

        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName(), thirdSong.getName());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
    }

    @Test
    public void addMultipleSongsToQueue(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        //add songs to queue
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(THIRD_SONG).perform(longClick());
        onView(withText(ADD_TO_QUEUE)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(SECOND_SONG).perform(longClick());
        onView(withText(ADD_TO_QUEUE)).perform(click());

        onView(withId(R.id.play_next_button)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName(), thirdSong.getName());

        onView(withId(R.id.play_next_button)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName(), secondSong.getName());

    }


    @Test
    public void addToPlayNextOnEmptyQueue(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        assertEquals(ServiceGateway.getMusicPlayerState().getQueueSize(), 0);
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(THIRD_SONG).perform(longClick());
        onView(withText(ADD_TO_PLAY_NEXT)).perform(click());

        onView(withId(R.id.play_next_button)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName(), thirdSong.getName());

    }

    @Test
    public void addToPlayNextOnNonEmptyQueue(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(SECOND_SONG).perform(longClick()); //add second song to queue
        onView(withText(ADD_TO_QUEUE)).perform(click());
        assertEquals(ServiceGateway.getMusicPlayerState().getQueueSize(), 1);

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(THIRD_SONG).perform(longClick()); //add third song to play next
        onView(withText(ADD_TO_PLAY_NEXT)).perform(click());

        assertEquals(ServiceGateway.getMusicPlayerState().getQueueSize(), 2);
        assertEquals(ServiceGateway.getMusicPlayerState().getQueue().peek().getName(), thirdSong.getName());
    }

    //add song to play next and verify the fragment displays it correctly
    @Test
    public void verifyQueueFragmentPlayNext(){
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(SECOND_SONG).perform(longClick());
        onView(withText(ADD_TO_PLAY_NEXT)).perform(click());
        //go to queue fragment
        onView(withId(R.id.queueList)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.queueResult)).atPosition(0).check(matches(withText(secondSong.getName()))); //check displays correct song
        onData(anything()).inAdapterView(withId(R.id.queueResult)).atPosition(0).perform(click());

        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName(), secondSong.getName());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
    }


}
