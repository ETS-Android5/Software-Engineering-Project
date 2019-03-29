package comp3350.breadtunes;

import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import comp3350.breadtunes.presentation.HomeActivity;
import comp3350.breadtunes.services.ServiceGateway;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest


// https://github.com/codepath/android_guides/wiki/UI-Testing-with-Espresso


public class PlayMusicFeatureTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);
    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void playSong(){


        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(0).perform(click());

         // TODO: 29/03/19 remove thread sleep, only there to see tests unfold in tablet physically
        try {
            Thread.sleep(5000);
        }catch(Exception e){

        }

        assertTrue(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong().getName().equals("Bloch Prayer"));
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

    }


    //click on imageview button onView(withId(R.id.parent_task_button)).perform(click()); @ todo click on pause button, next, previous etc etc

    /*
    1. Play/Pause music	As a user I want to be able to play/pause music
2. Play Next/Previous song	As a user I want to be able to play next/previous song
3. Shuffle songs	As a user I want to be able to shuffle songs
4. Repeat songs	As a user I want to be able to play a song in repeat
     */
}
