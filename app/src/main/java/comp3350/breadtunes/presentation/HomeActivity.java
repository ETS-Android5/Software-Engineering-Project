package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.presentation.MediaController.MediaPlayerController;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.base.BaseActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

//==============================
// HELPFUL DOCUMENTATION
//    // Media Player class https://developer.android.com/reference/android/media/MediaPlayer
//    // To view info about the activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
//    // Populating lists with custom content https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
//    // PLAY SONGS https://developer.android.com/guide/topics/media/mediaplayer#java
//==============================


public class HomeActivity extends BaseActivity  {


    MediaPlayerController mediaPlayerController;  // controls playback operations
    MusicPlayerState musicPlayerState ; //business layer object that contains the current state of the music player
    public static ArrayList<Song> sList = new ArrayList<>();
    String[] songNamesToDisplay;
    private final String TAG = "HomeActivity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        final List<Song> songList = ServiceGateway.getSongPersistence().getAll();
        musicPlayerState = new MusicPlayerState(songList);
        mediaPlayerController = new MediaPlayerController(HomeActivity.this, musicPlayerState, ServiceGateway.getMediaManager());

        //initialize the songNamestoDisplay so that the fragment can populate its list
        sList.addAll(songList);
        songNamesToDisplay = getSongNames(sList);

        //put the song list fragment on top of the main activity
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, new SongListFragment());
        fragmentTransaction.commit();



    }//on create

    //replace the song list fragment with the now playing fragment
    public void showNowPlayingFragment(){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, new NowPlayingFragment());
        fragmentTransaction.commit();
    }


    protected void onDestroy() {
        mediaPlayerController.releaseMediaPlayer();
        super.onDestroy();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    private String[] getSongNames(ArrayList<Song> songList){
        String[] songNames = new String[songList.size()];
        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
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
        Log.i(TAG, response);
    }

    //PREVIOUS BUTTON
    public void onClickPlayPrevious(View view){
        String response = mediaPlayerController.playPreviousSong(HomeActivity.this);
        Log.i(TAG, response);
    }
}
