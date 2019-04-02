package comp3350.breadtunes;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.widget.EditText;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
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

    private Song initialSong;
    private Song secondSong;
    private List<Song> songList;
    private final String ADD_TO_QUEUE = "Add to Queue";
    private final String ADD_TO_PLAY_NEXT = "Play Next";

    @Before
    public void setup(){
        songList = ServiceGateway.getMusicPlayerState().getCurrentSongList();
        assertTrue(songList.size() > 1);
        initialSong = songList.get(FIRST_SONG);
        secondSong = songList.get(SECOND_SONG);

    }

    @Test
    public void addToQueue(){
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(longClick());

        onView(withText(ADD_TO_QUEUE))
                .perform(click());
    }


    @Test
    public void addToPlayNext(){

    }
    /*

    1. Add/remove from queue	As a user I want to be able to add and remove songs from the queue
2. Play next	As a user I want to be able to choose a song to play next, overwriting the next song in the queue

     */
}
