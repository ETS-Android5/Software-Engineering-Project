package comp3350.breadtunes;
import android.app.Service;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.After;
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
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
@LargeTest

public class ParentalControlFeatureTest {


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

    private final String PIN = "1111";
    private final String SECRET_QUESTION = "What is the name of the app?";
    private final String SECRET_QUESTION_ANSWER = "Breadtunes";
    private final String RESET_PASSWORD = "FORGOT PASSWORD?";
    private final String SUBMIT = "SUBMIT";
    private final String FLAG = "Flag song";
    private final String REMOVE_FLAG = "Remove song flag";

    @Before
    public void setup(){

        ServiceGateway.getCredentialManager().clearCredentials();
        songList = ServiceGateway.getMusicPlayerState().getCurrentSongList();
        assertFalse(ServiceGateway.getCredentialManager().credentialsHaveBeenSet());
        assertTrue(songList.size() > 2);
        initialSong = songList.get(FIRST_SONG);
        secondSong = songList.get(SECOND_SONG);
        thirdSong = songList.get(THIRD_SONG);

        ServiceGateway.getSongFlagger().flagSong(initialSong, false);
        ServiceGateway.getSongFlagger().flagSong(secondSong, false);
        ServiceGateway.getSongFlagger().flagSong(thirdSong, false);

        //check that songs for testing have no flags set
        assertFalse(ServiceGateway.getSongFlagger().songIsFlagged(initialSong));
        assertFalse(ServiceGateway.getSongFlagger().songIsFlagged(secondSong));
        assertFalse(ServiceGateway.getSongFlagger().songIsFlagged(thirdSong));

        //create credentials for a single test
        onView(withId(R.id.parental_lock_on)).perform(click());
        onView(withId(R.id.pin_field)).perform(typeText(PIN));
        onView(withId(R.id.secret_question)).perform(typeText(SECRET_QUESTION));
        Espresso.pressBack(); //press back to hide keyboard and typ ein the secret questin answer
        onView(withId(R.id.secret_question_answer)).perform(typeText(SECRET_QUESTION_ANSWER));
        Espresso.pressBack();
        onView(withId(R.id.submit_button)).perform(click());
    }


    @Test
    public void turnParentalControlOn(){

        onView(withId(R.id.parental_lock_on)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(PIN)); //type text in
        onView(withText(SUBMIT)).perform(click());
        assertTrue(ServiceGateway.getCredentialManager().credentialsHaveBeenSet());
    }

    @Test
    public void turnParentalControlOff(){
        onView(withId(R.id.parental_lock_on)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(PIN)); //type text in
        onView(withText(SUBMIT)).perform(click());
        assertTrue(ServiceGateway.getCredentialManager().credentialsHaveBeenSet());
        assertTrue(ServiceGateway.getMusicPlayerState().getParentalControlModeOn());

        onView(withId(R.id.parental_lock_off)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(PIN)); //type text in
        onView(withText(SUBMIT)).perform(click());
        assertFalse(ServiceGateway.getMusicPlayerState().getParentalControlModeOn());
    }

    //flag a song, turn parental control and verify it is no longer findable/ playable
    @Test
    public void flagSong(){

        if(ServiceGateway.getMusicPlayerState().getParentalControlModeOn()){
            onView(withId(R.id.parental_lock_off)).perform(click());
            onView(isAssignableFrom(EditText.class)).perform(typeText(PIN)); //type text in
            onView(withText(SUBMIT)).perform(click());
        }


        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(longClick()); //flag song
        onView(withText(FLAG)).perform(click());

        assertTrue(ServiceGateway.getSongFlagger().songIsFlagged(initialSong));

        onView(withId(R.id.parental_lock_on)).perform(click()); //activate parental control
        onView(isAssignableFrom(EditText.class)).perform(typeText(PIN));
        onView(withText(SUBMIT)).perform(click());

        //check that the flagged song is not being displayed anymore
        for(int i = 0; i < ServiceGateway.getMusicPlayerState().getCurrentSongList().size(); i++){
            onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(i).check(matches(not(withText(initialSong.getName()))));

        }

        //try to search for the flagged song and verify it does not show up in serch results
        onView(withId(R.id.search)).perform(click());
        String search = initialSong.getName().substring(0, 2);  //type only the first two words of the name of the song
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER));
        int numberOfResults = HomeActivity.result.length;
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).check(matches(not(withText(initialSong.getName()))));
        }

        Espresso.pressBack();
        Espresso.pressBack();
    }

    //flag a song, verify it is not playable, then unflag it and verify it returns to the its position in the UI
    @Test
    public void removeFlagFromSong(){

        if(ServiceGateway.getMusicPlayerState().getParentalControlModeOn()){
            onView(withId(R.id.parental_lock_off)).perform(click());
            onView(isAssignableFrom(EditText.class)).perform(typeText(PIN)); //type text in
            onView(withText(SUBMIT)).perform(click());
        }

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(longClick());
        onView(withText(FLAG)).perform(click());

        assertTrue(ServiceGateway.getSongFlagger().songIsFlagged(initialSong));

        onView(withId(R.id.parental_lock_on)).perform(click()); //activate parental control
        onView(isAssignableFrom(EditText.class)).perform(typeText(PIN));
        onView(withText(SUBMIT)).perform(click());

        //check that the flagged song is not being displayed anymore
        for(int i = 0; i < ServiceGateway.getMusicPlayerState().getCurrentSongList().size(); i++){
            onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(i).check(matches(not(withText(initialSong.getName()))));

        }

        //turn parental control off
        onView(withId(R.id.parental_lock_off)).perform(click()); //activate parental control
        onView(isAssignableFrom(EditText.class)).perform(typeText(PIN));
        onView(withText(SUBMIT)).perform(click());

        //unflag the song
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).check(matches((withText(initialSong.getName())))); //verify the song is back after turning parental control off
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(longClick());
        onView(withText(REMOVE_FLAG)).perform(click());
        assertFalse(ServiceGateway.getSongFlagger().songIsFlagged(initialSong)); //check flaag was removed succesfully
    }

    @Test
    public void resetCredentials(){

        final String NEW_PIN = "2222";

        onView(withId(R.id.parental_lock_on)).perform(click());
        onView(withText(RESET_PASSWORD)).perform(click());

        onView(withId(R.id.secret_question_resetfragment)).check(matches(withText(SECRET_QUESTION))); //check the fragment displays the secret question we set up originally
        onView(withId(R.id.secret_question_answer_resetfragment)).perform(typeText(SECRET_QUESTION_ANSWER)); //type answer to secret question
        onView(withId(R.id.pin_field_resetfragment)).perform(typeText(NEW_PIN));
        onView(withId(R.id.submit_button_resetfragment)).perform(click());

        assertFalse(ServiceGateway.getMusicPlayerState().getParentalControlModeOn()); //make sure parental control is off before we try the new pin

        //try out the new pin
        onView(withId(R.id.parental_lock_on)).perform(click()); //activate parental control
        onView(isAssignableFrom(EditText.class)).perform(typeText(NEW_PIN));
        onView(withText(SUBMIT)).perform(click());

        assertTrue(ServiceGateway.getMusicPlayerState().getParentalControlModeOn()); //verify we were succesful

        //turn off the mode
        onView(withId(R.id.parental_lock_off)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(NEW_PIN)); //type text in
        onView(withText(SUBMIT)).perform(click());
    }


    @After
    public void cleanUp(){
        //remove flags from the set of three songs used for testing
        ServiceGateway.getSongFlagger().flagSong(initialSong, false);
        ServiceGateway.getSongFlagger().flagSong(secondSong, false);
        ServiceGateway.getSongFlagger().flagSong(thirdSong, false);

        ServiceGateway.getCredentialManager().clearCredentials(); //delete the credentials set in this test

    }
}
