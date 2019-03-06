package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.presentation.MediaController.MediaPlayerController;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.base.BaseActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

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
    LookUpSongs findSong;
    List<Song> sResult;
    String [] result;
    private FragmentTransaction fragmentTransaction;

    // fragments used in the main activity
    private NowPlayingFragment nowPlayingFragment;
    private SearchResultsFragment searchSongFragment;
    private SongListFragment songListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if(savedInstanceState == null){
            nowPlayingFragment = new NowPlayingFragment();
            searchSongFragment = new SearchResultsFragment();
            songListFragment = new SongListFragment();
        }else{
            //retrieve the state of the fragment
            //Toast.makeText(this, "restoring song list fragment in on create of main activity", Toast.LENGTH_LONG).show();
            songListFragment = (SongListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "songlist_fragment");
        }


        final List<Song> songList = ServiceGateway.getSongPersistence().getAll();
        musicPlayerState = new MusicPlayerState(songList);
        mediaPlayerController = new MediaPlayerController(HomeActivity.this, musicPlayerState, ServiceGateway.getMediaManager());
        findSong = new LookUpSongs(songList);
        //initialize the songNamestoDisplay so that the fragment can populate its list
        sList.addAll(songList);
        songNamesToDisplay = getSongNames(sList);

        //put the song list fragment on top of the main activity
        showSongListFragment();

        handleIntent(getIntent());

    }//on create

    //restore the activitie state
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        musicPlayerState.setIsSongPlaying(savedInstanceState.getBoolean("songPlaying"));
        musicPlayerState.setIsSongPaused(savedInstanceState.getBoolean("songPaused"));
        musicPlayerState.setCurrentSongPlayingName(savedInstanceState.getString("currentSong"));
        musicPlayerState.setPausedPosition(savedInstanceState.getInt("pausedPosition"));
        songNamesToDisplay = savedInstanceState.getStringArray("currentSongList");
        //Toast.makeText(this, "Restoring music player state...", Toast.LENGTH_SHORT).show();
    }

    //save the activities state
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        //save all of the music player state
        outState.putBoolean("songPlaying", musicPlayerState.isSongPlaying());
        outState.putBoolean("songPaused", musicPlayerState.isSongPaused());
//        outState.putString("currentSong", musicPlayerState.getCurrentlyPlayingSong().getName());
        outState.putInt("pausedPosition", musicPlayerState.getPausedPosition());
        outState.putStringArray("currentSongList", songNamesToDisplay);
       // Toast.makeText(this, "saving music player state...", Toast.LENGTH_SHORT).show();

    }

    public void showSongListFragment(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it
        if(songListFragment.isAdded()){
            fragmentTransaction.show(songListFragment);
        }else{
            //inflate it if it has not been added
            fragmentTransaction.add(R.id.fragment_placeholder, songListFragment);
        }

        //hide the other fragments if they are showing
        if(nowPlayingFragment.isAdded()){fragmentTransaction.hide(nowPlayingFragment);}
        if(searchSongFragment.isAdded()){fragmentTransaction.hide(searchSongFragment);}
        fragmentTransaction.commit();

        //fragmentTransaction.addToBackStack("nowPlaying");
    }


    //replace the song list fragment with the now playing fragment
    public void showNowPlayingFragment(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it
        if(nowPlayingFragment.isAdded()){
            fragmentTransaction.show(nowPlayingFragment);
        }else{
            //inflate it if it has not been added
            fragmentTransaction.add(R.id.fragment_placeholder, nowPlayingFragment);
        }

        //hide the other fragments if they are showing
        if(songListFragment.isAdded()){fragmentTransaction.hide(songListFragment);}
        if(searchSongFragment.isAdded()){fragmentTransaction.hide(searchSongFragment);}
        fragmentTransaction.addToBackStack(null); //add to back stack so we can return to this fragment
        fragmentTransaction.commit();


    }


    public void showSearchResultsFragment(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it

        searchSongFragment = new SearchResultsFragment();
        fragmentTransaction.remove(songListFragment);
        fragmentTransaction.add(R.id.fragment_placeholder,searchSongFragment);

        if(nowPlayingFragment.isAdded()){fragmentTransaction.hide(nowPlayingFragment);}

        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
        mediaPlayerController.pauseSong();
        String response = mediaPlayerController.playPreviousSong(HomeActivity.this);
        Log.i(TAG, response);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    //handle user input and launch search fragment
    public void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            sResult = findSong.searchSongs(query);
            ArrayList ss = new ArrayList();
            for(int i=0; i<sResult.size();i++)
                ss.add(sResult.get(i));
            result = getSongNames(ss); //get the names of the songs in order to populate the listview in the results fragment
            showSearchResultsFragment(); //show search fragment
        }
    }


}
