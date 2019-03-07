package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.presentation.MediaController.MediaPlayerController;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.base.BaseActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private FragmentTransaction fragmentTransaction;

    // fragments used in the main activity
    private NowPlayingFragment nowPlayingFragment;
    private SearchResultsFragment searchSongFragment;
    private SongListFragment songListFragment;

    // the list of songs acquired from Persistance layer
    List<Song> songList;

    //variables for getting search query and launching search results fragment
    List<Song> sResult;
    String[] result;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Toast.makeText(this, "on resume called in main activity...", Toast.LENGTH_LONG).show();

        if(savedInstanceState == null){
            nowPlayingFragment = new NowPlayingFragment();
            searchSongFragment = new SearchResultsFragment();
            songListFragment = new SongListFragment();
        }else{
            //retrieve the state of the fragment
           // Toast.makeText(this, "restoring song list fragment in on create of main activity", Toast.LENGTH_LONG).show();
            songListFragment = (SongListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "songlist_fragment");
        }


        getSongsFromPersistance();

        musicPlayerState = new MusicPlayerState(songList);
        mediaPlayerController = new MediaPlayerController(HomeActivity.this, musicPlayerState, ServiceGateway.getMediaManager());
        findSong = new LookUpSongs(songList);


        refreshSongList();
        showSongListFragment(); //put the song list fragment on top of the main activity
        handleIntent(getIntent());
    }//on create

    public void getSongsFromPersistance(){
        songList = new ArrayList<>();
        songList.addAll(ServiceGateway.getSongPersistence().getAll());
    }

    public void refreshSongList(){
        sList.addAll(songList);
        songNamesToDisplay = new String[songList.size()];
        for(int i=0;i<songNamesToDisplay.length;i++)
            songNamesToDisplay[i] = songList.get(i).getName();
    }

    protected void onResume(){
        super.onResume();
        getSongsFromPersistance();
        refreshSongList();
       // Toast.makeText(this, "on resume called in main activity...", Toast.LENGTH_LONG).show();
    }

    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "on start called in main activity...", Toast.LENGTH_LONG).show();
    }

    protected void onRestart(){
        super.onRestart();
       // Toast.makeText(this, "on restart...", Toast.LENGTH_LONG).show();
    }

    protected void onPause(){
        super.onPause();
        //Toast.makeText(this, "on pause called in main activity...", Toast.LENGTH_LONG).show();
    }

    protected void onStop(){
        super.onStop();
       // Toast.makeText(this, "on stop...", Toast.LENGTH_LONG).show();
    }


    protected void onDestroy(){
        super.onDestroy();
        //Toast.makeText(this, "On destroy...", Toast.LENGTH_LONG).show();
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        //Toast.makeText(this, "On restore...", Toast.LENGTH_LONG).show();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //Toast.makeText(this, "On save instance state...", Toast.LENGTH_LONG).show();
    }


    //method called by fragments to avoid context issues
    public void playSong(Song song){
        int songId = getResources().getIdentifier(song.getRawName(), "raw", this.getPackageName());
        String playStatus = mediaPlayerController.playSong(song, songId);
        Toast.makeText(this, musicPlayerState.getMusicPlayerState(),Toast.LENGTH_LONG).show();
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


    public String[] getSongNames(ArrayList<Song> songList){
        String[] songNames = new String[songList.size()];
        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
    }

    // PAUSE BUTTON
    public void onClickPause(View view){
        String response = mediaPlayerController.pauseSong();
        Toast.makeText(this, musicPlayerState.getMusicPlayerState(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, musicPlayerState.getMusicPlayerState(), Toast.LENGTH_LONG).show();
            Log.i(TAG, musicPlayerState.getMusicPlayerState());
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

    public MediaPlayerController getMediaPlayerController(){
        return mediaPlayerController;
    }



}
