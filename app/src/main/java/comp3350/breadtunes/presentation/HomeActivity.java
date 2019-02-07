package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.HomeActivityHelper;
import comp3350.breadtunes.business.MediaPlayerController;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.base.BaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


//==============================
// HELPFUL DOCUMENTATION
//    // Media Player class https://developer.android.com/reference/android/media/MediaPlayer
//    // To view info about the activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
//    // Populating lists with custom content https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
//    // PLAY SONGS https://developer.android.com/guide/topics/media/mediaplayer#java
//==============================



public class HomeActivity extends BaseActivity {


     MediaPlayerController mediaPlayerController;  //business layer objects that help presentation layer carry out operations
     MusicPlayerState musicPlayerState ; //business layer object that contains the current state of the music player
     HomeActivityHelper homeActivityHelper; //populates this activity and provides small utilities such as
     private static final String TAG = "Home Activity"; //tag used in messages to the log

    public static TextView nowPlayingGUI;  //UI element that indicates which song is being played

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeActivityHelper = new HomeActivityHelper(HomeActivity.this);
        musicPlayerState = new MusicPlayerState(homeActivityHelper.getHomeActivitySongList()); //crate the object and pass it the list of songs in the home activity
        homeActivityHelper.setAppState(musicPlayerState); //pass the app state so that home activity helper can update the gui when a song changes
        mediaPlayerController = new MediaPlayerController(HomeActivity.this, musicPlayerState); //init logic layer

        nowPlayingGUI = (TextView) findViewById(R.id.song_playing_text);
        nowPlayingGUI.setKeyListener(null);

        String[] songNames = homeActivityHelper.getSongNames();  //get the names of all songs to be displayed in the ListView

        //create adapter to populate list items in the listView in the main activity
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.songlist_element, songNames);
        final ListView activitySongList = (ListView) findViewById(R.id.songList);
        activitySongList.setAdapter(adapter); //populate the items!

        //set on item click listener to react to list clicks
        activitySongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
               Log.i(TAG, "Clicked on "+selectedSongName);
               //get the song object associated with the song name that was clicked
               Song selectedSong = homeActivityHelper.getSong(selectedSongName);

               if(selectedSong != null) {
                   int songId = getResources().getIdentifier(selectedSong.getRawName(), "raw", getPackageName());
                   String playStatus = mediaPlayerController.playSong(selectedSong, songId); //                             play the song!
                   updateNowPlaying(); //update the gui with the new song playing
                    Log.e(TAG, playStatus);
               }
           }
       });// on item click listener for listview

    }//on create



    protected void onDestroy() {
        mediaPlayerController.releaseMediaPlayer();
        super.onDestroy();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    // PAUSE BUTTON
    public void onClickPause(View view){
        String response = mediaPlayerController.pauseSong();
        Log.i(TAG, response);
    }

    //RESUME BUTTON
    public void onClickResume(View view) {

        //make sure a song is actually paused
        if (musicPlayerState.isSongPaused()) {
            Song pausedSong = musicPlayerState.getCurrentlyPlayingSong();   //get the current playing song from the app state
            int resourceId = getResources().getIdentifier(pausedSong.getRawName(), "raw", getPackageName());    //get the resource pointer
            String response = mediaPlayerController.resumeSong(resourceId);                 // ask  media controller to resume
            Log.i(TAG, response); //display result of operation to log
        }else{
            Log.i(TAG, "Cannot resume, no song is paused");
        }
    }

    //NEXT BUTTON
    public void onClickPlayNext(View view){
        String response = mediaPlayerController.playNextSong(HomeActivity.this);
        updateNowPlaying();
        Log.i(TAG, response);
    }

    //PREVIOUS BUTTON
    public void onClickPlayPrevious(View view){
        String response = mediaPlayerController.playPreviousSong(HomeActivity.this);
        updateNowPlaying();
        Log.i(TAG, response);
    }

    //update the now playing part of the gui: more info https://developer.android.com/training/multiple-threads/communicate-ui
    //more info : https://stackoverflow.com/questions/11140285/how-do-we-use-runonuithread-in-android
    private void updateNowPlaying(){
        homeActivityHelper.run();

    }

}
