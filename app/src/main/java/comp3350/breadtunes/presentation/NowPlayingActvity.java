package comp3350.breadtunes.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import comp3350.breadtunes.R;
import comp3350.breadtunes.objects.Song;


// REFERENCE :
// GUIA https://google-developer-training.gitbooks.io/android-developer-advanced-course-practicals/content/unit-1-expand-the-user-experience/lesson-1-fragments/1-1-p-creating-a-fragment-with-a-ui/1-1-p-creating-a-fragment-with-a-ui.html
// https://developer.android.com/guide/components/fragments


public class NowPlayingActvity extends AppCompatActivity {

    public static TextView songNameTextView;
    public static TextView albumNameTextView;
    public static TextView artistNameTextView;

    private final String UNKNOWN_SONG = "unknown song";
    private final String UNKNWON_ALBUM = "unknown album";
    private final String UNKNOWN_ARTIST = "unkown artist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_actvity);

        Bundle extras = getIntent().getExtras();
        String songName = null;
        String albumName = null;
        String artistName = null;

        if(extras != null){
            songName = extras.getString("Song");
            albumName = extras.getString("Album");
            artistName = extras.getString("Artist");
        }

        //get references to text views in order to populate them
        songNameTextView = (TextView) findViewById(R.id.song_name);
        albumNameTextView = (TextView) findViewById(R.id.album_name);
        artistNameTextView = (TextView) findViewById(R.id.artist_name);

        if(songName!=null){
            songNameTextView.setText(songName);
        }else{
            songNameTextView.setText(UNKNOWN_SONG);
        }

        if(albumName!=null){
            albumNameTextView.setText(albumName);
        }else{
            albumNameTextView.setText(UNKNWON_ALBUM);
        }

        if(artistName!=null){
            artistNameTextView.setText(artistName);
        }else{
            artistNameTextView.setText(UNKNOWN_ARTIST);
        }

    }//on create


    // PAUSE BUTTON
    public void onClickPause(View view) {
//        String response = mediaPlayerController.pauseSong();
//        Log.i(TAG, response);
    }

    //RESUME BUTTON
    public void onClickResume(View view) {

//        //make sure a song is actually paused
//        if (musicPlayerState.isSongPaused()) {
//            Song pausedSong = musicPlayerState.getCurrentlyPlayingSong();   //get the current playing song from the app state
//            int resourceId = getResources().getIdentifier(pausedSong.getRawName(), "raw", getPackageName());    //get the resource pointer
//            String response = mediaPlayerController.resumeSong(resourceId);                 // ask  media controller to resume
//            Log.i(TAG, response); //display result of operation to log
//        }else{
//            Log.i(TAG, "Cannot resume, no song is paused");
//        }
    }

    //NEXT BUTTON
    public void onClickPlayNext(View view) {
//        String response = mediaPlayerController.playNextSong(HomeActivity.this);
//        Log.i(TAG, response);
    }

    //PREVIOUS BUTTON
    public void onClickPlayPrevious(View view) {
//        String response = mediaPlayerController.playPreviousSong(HomeActivity.this);
//        Log.i(TAG, response);
    }

}