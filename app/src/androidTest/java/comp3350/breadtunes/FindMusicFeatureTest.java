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

@RunWith(AndroidJUnit4.class)
@LargeTest

public class FindMusicFeatureTest {


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
    public void searchSongAndPlay(){
        onView(withId(R.id.search)).perform(click());
        String search = initialSong.getName().substring(0, 2);  //type only the first two words of the name of the song
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER)); //type text in

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(initialSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
    }

    //verify that the song list fragment displays the correct information when playing a song in the search results fragment
    @Test
    public void searchSongAndReturn(){
        onView(withId(R.id.search)).perform(click());
        String search = secondSong.getName().substring(0, 2);  //type only the first two words of the name of the song
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER)); //type text in

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(secondSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song

        Espresso.pressBack();
        Espresso.pressBack();

        //check the gui shows the song playing
        String songName = secondSong.getName();
        onView(withId(R.id.song_name)).check(matches(withText(containsString(songName))));
    }

    //search a song, go back to the song list fragment, go to the now playing fragment and verify the gui is correct
    @Test
    public void searchSongAndNowPlaying(){
        onView(withId(R.id.search)).perform(click());
        String search = secondSong.getName().substring(0, 2);  //type only the first two words of the name of the song
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER)); //type text in

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(secondSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.song_name)).perform(click());

        //check the gui shows the song playing
        String songName = secondSong.getName();
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));
    }

    //play a song first, then search for another song and play it
    @Test
    public void playAndSearch(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //play first song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        //search
        onView(withId(R.id.search)).perform(click());
        String search = secondSong.getName().substring(0, 2);  //type only the first two words of the name of the song
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER)); //type text in

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(secondSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(),secondSong);
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

    }

    @Test
    public void pauseAndSearch(){

        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click()); //play first song
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());

        onView(withId(R.id.play_pause)).perform(click()); //pause
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPaused());

        //search for the second song and play it
        onView(withId(R.id.search)).perform(click());
        String search = secondSong.getName().substring(0, 2);  //type only the first two words of the name of the song
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER)); //type text in

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(secondSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(),secondSong);
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong);
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
    }

    //search for the first song, play it, go back to home , and play the next song
    @Test
    public void searchAndPlayNext(){

        onView(withId(R.id.search)).perform(click());
        String search = initialSong.getName().substring(0, 2);
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER));

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(initialSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.play_next_button)).perform(click()); //play next song
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), null);
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong); //verify we are not playing the same song
    }

    @Test
    public void searchAndPlayPrevious(){

        onView(withId(R.id.search)).perform(click());
        String search = secondSong.getName().substring(0, 2);
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressKey(KeyEvent.KEYCODE_ENTER));

        int numberOfResults = HomeActivity.result.length;
        assertTrue(numberOfResults > 0);
        boolean foundSong = false;

        //play the results of the search to see if our desired song is there
        for(int i = 0; i < numberOfResults; i++){
            onData(anything()).inAdapterView(withId(R.id.resultDisplay)).atPosition(i).perform(click()); //click ith song
            if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSongName().equals(secondSong.getName())){
                foundSong = true;
                break;
            }
        }
        assertTrue(foundSong); //make sure we played the song
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.play_previous_button)).perform(click()); //play next song
        assertNotEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), null);
        assertTrue(ServiceGateway.getMusicPlayerState().isSongPlaying());
        assertFalse(ServiceGateway.getMusicPlayerState().isSongPaused());
        assertEquals(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong(), initialSong); //should equal first song, since we looked for
                                                                                                    //second song, played it , and then played previous
    }

}
