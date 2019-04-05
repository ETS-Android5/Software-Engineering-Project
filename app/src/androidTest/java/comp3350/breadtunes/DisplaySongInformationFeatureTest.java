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
import comp3350.breadtunes.presentation.HomeActivity;
import comp3350.breadtunes.services.ServiceGateway;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplaySongInformationFeatureTest {
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    private int FIRST_SONG = 0;
    private int SECOND_SONG = 1;
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
    public void verifyNowPlayingInformation() {
        // Click a song to play it
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        // Go to the now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        String songName = initialSong.getName();
        String albumName = initialSong.getAlbumName();
        String artistName = initialSong.getArtistName();
        String duration = ServiceGateway.getMediaManager().getDurationString();

        // Verify information in fragment
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(songName))));
        onView(withId(R.id.album_name)).check(matches(withText(containsString(albumName))));
        onView(withId(R.id.artist_name)).check(matches(withText(containsString(artistName))));
        onView(withId(R.id.song_duration)).check(matches(withText(containsString(duration))));
        onView(withId(R.id.song_art)).check(matches(isDisplayed()));

        // Pause the song
        onView(withId(R.id.play_pause_button)).perform(click());
    }

    @Test
    public void verifyNowPlayingInformationOnPlayNextSong() {
        // Click a song to play it
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(FIRST_SONG).perform(click());

        // Go to the now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        //play the next song
        onView(withId(R.id.play_next_button_nowplaying_fragment)).perform(click());

        String duration = ServiceGateway.getMediaManager().getDurationString();

        // Verify information in fragment
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(secondSong
        .getName()))));
        onView(withId(R.id.album_name)).check(matches(withText(containsString(secondSong.getAlbumName()))));
        onView(withId(R.id.artist_name)).check(matches(withText(containsString(secondSong.getArtistName()))));
        onView(withId(R.id.song_duration)).check(matches(withText(containsString(duration))));
        onView(withId(R.id.song_art)).check(matches(isDisplayed()));

        // Pause the song
        onView(withId(R.id.play_pause_button)).perform(click());
    }

    @Test
    public void verifyNowPlayingInformationOnPlayPreviousSong() {
        // Click a song to play it
        onData(anything()).inAdapterView(withId(R.id.songList)).atPosition(SECOND_SONG).perform(click());

        // Go to the now playing fragment
        onView(withId(R.id.song_name)).perform(click());

        //play the next song
        onView(withId(R.id.play_previous_button_nowplaying_fragment)).perform(click());

        String duration = ServiceGateway.getMediaManager().getDurationString();

        // Verify information in fragment
        onView(withId(R.id.song_name_nowplaying_fragment)).check(matches(withText(containsString(initialSong
                .getName()))));
        onView(withId(R.id.album_name)).check(matches(withText(containsString(initialSong.getAlbumName()))));
        onView(withId(R.id.artist_name)).check(matches(withText(containsString(initialSong.getArtistName()))));
        onView(withId(R.id.song_duration)).check(matches(withText(containsString(duration))));
        onView(withId(R.id.song_art)).check(matches(isDisplayed()));

        // Pause the song
        onView(withId(R.id.play_pause_button)).perform(click());
    }
}
