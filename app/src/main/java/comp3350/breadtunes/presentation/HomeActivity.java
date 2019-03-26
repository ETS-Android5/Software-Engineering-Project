package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.observables.DatabaseUpdatedObservable;
import comp3350.breadtunes.business.observables.ParentalControlStatusObservable;
import comp3350.breadtunes.presentation.MediaController.MediaPlayerController;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.AbstractActivities.BaseActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


//==============================
// HELPFUL DOCUMENTATION
//    // Media Player class https://developer.android.com/reference/android/media/MediaPlayer
//    // To view info about the activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
//    // Populating lists with custom content https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
//    // PLAY SONGS https://developer.android.com/guide/topics/media/mediaplayer#java
//==============================

//@// TODO: 20/03/19 agregar accion para forgot pin, agregar cambios en music player state que represente si esta activado, agregar checksa credentials have been set in parental lock activation, agregar ui element que diga "PARENTAL LOCK ON/OFF"


public class HomeActivity extends BaseActivity implements Observer {
    MediaPlayerController mediaPlayerController;  // controls playback operations
    public static ArrayList<Song> sList = new ArrayList<>();
    String[] songNamesToDisplay;
    private final String TAG = "HomeActivity";
    LookUpSongs findSong;
    private FragmentTransaction fragmentTransaction;

    // fragments used in the main activity
    private NowPlayingFragment nowPlayingFragment;
    private SearchResultsFragment searchSongFragment;
    private SongListFragment songListFragment;
    private ParentalControlSetupFragment parentalControlSetupFragment;
    private ResetPINFragment resetPINFragment;

    // the list of songs acquired from Persistance layer
    List<Song> songList;

    //variables for getting search query and launching search results fragment
    List<Song> sResult;
    String[] result;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set subscriptions
        ServiceGateway.subscribeToDatabaseStateChanges(this);
        MusicPlayerState.getInstance().subscribeToParentalControlStatusChange(this);

        // Prepare fragments
        if (savedInstanceState == null) {
            resetPINFragment = new ResetPINFragment();
            nowPlayingFragment = new NowPlayingFragment();
            searchSongFragment = new SearchResultsFragment();
            songListFragment = new SongListFragment();
            parentalControlSetupFragment = new ParentalControlSetupFragment();
        } else {
            //retrieve the state of the fragment
            songListFragment = (SongListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "songlist_fragment");
        }

        // Retrieve songs from database
        getSongsFromPersistance();

        // Create media controller
        mediaPlayerController = new MediaPlayerController();

        // Create song search helper
        findSong = new LookUpSongs(songList);

        // Show the list of songs
        getSongNameList();
        showSongListFragment();
        handleIntent(getIntent());
    }

    public void getSongsFromPersistance() {
        songList = new ArrayList<>();
        songList.addAll(ServiceGateway.getSongPersistence().getAll());
    }

    public void getUnflaggedSongsFromPersistence() {
        songList = new ArrayList<>();
        songList.addAll(ServiceGateway.getSongPersistence().getAllNotFlagged());
    }

    public void getSongNameList() {
        sList.addAll(songList);
        songNamesToDisplay = new String[songList.size()];
        for (int i = 0; i < songNamesToDisplay.length; i++)
            songNamesToDisplay[i] = songList.get(i).getName();
    }

    public void refreshSongList() {
        getSongNameList();
        MusicPlayerState.getInstance().setCurrentSongList(sList);
        songListFragment = new SongListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, songListFragment).commit();
    }

    protected void onResume() {
        super.onResume();
        getSongsFromPersistance();
        getSongNameList();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }


    protected void onDestroy() {
        super.onDestroy();
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    //method called by fragments to avoid context issues
    public void playSong(Song song) {
        String playStatus = mediaPlayerController.playSong(song, this);
        Log.i(TAG, playStatus);
    }

    public void showSongListFragment() {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it
        if (songListFragment.isAdded()) {
            fragmentTransaction.show(songListFragment);
        } else {
            //inflate it if it has not been added
            fragmentTransaction.add(R.id.fragment_placeholder, songListFragment);
        }

        //hide the other fragments if they are showing
        if (nowPlayingFragment.isAdded()) {
            fragmentTransaction.hide(nowPlayingFragment);
        }
        if (searchSongFragment.isAdded()) {
            fragmentTransaction.hide(searchSongFragment);
        }
        if(parentalControlSetupFragment.isAdded()){
            fragmentTransaction.hide(parentalControlSetupFragment);
        }
        if(resetPINFragment.isAdded()){
            fragmentTransaction.hide(resetPINFragment);
        }
        
        fragmentTransaction.commit();

    }


    //replace the song list fragment with the now playing fragment
    public void showNowPlayingFragment() {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it
        if (nowPlayingFragment.isAdded()) {
            fragmentTransaction.show(nowPlayingFragment);
        } else {
            //inflate it if it has not been added
            fragmentTransaction.add(R.id.fragment_placeholder, nowPlayingFragment);
        }

        //hide the other fragments if they are showing
        if (songListFragment.isAdded()) {
            fragmentTransaction.hide(songListFragment);
        }
        if (searchSongFragment.isAdded()) {
            fragmentTransaction.hide(searchSongFragment);
        }
        if(parentalControlSetupFragment.isAdded()){
            fragmentTransaction.hide(parentalControlSetupFragment);
        }
        if(resetPINFragment.isAdded()){
            fragmentTransaction.hide(resetPINFragment);
        }
        fragmentTransaction.addToBackStack(null); //add to back stack so we can return to this fragment
        fragmentTransaction.commit();

    }

    public void showPINResetFragment(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it
        if (resetPINFragment.isAdded()) {
            fragmentTransaction.show(resetPINFragment);
        } else {
            //inflate it if it has not been added
            fragmentTransaction.add(R.id.fragment_placeholder, resetPINFragment);
        }

        //hide the other fragments if they are showing
        if (songListFragment.isAdded()) {
            fragmentTransaction.hide(songListFragment);
        }
        if (searchSongFragment.isAdded()) {
            fragmentTransaction.hide(searchSongFragment);
        }
        if (nowPlayingFragment.isAdded()){
            fragmentTransaction.hide(nowPlayingFragment);
        }
        if(parentalControlSetupFragment.isAdded()){
            fragmentTransaction.hide(parentalControlSetupFragment);
        }

        fragmentTransaction.addToBackStack(null); //add to back stack so we can return to this fragment
        fragmentTransaction.commit();

    }


    public void showParentalControlSetupFragment(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it
        if (parentalControlSetupFragment.isAdded()) {
            fragmentTransaction.show(parentalControlSetupFragment);
        } else {
            //inflate it if it has not been added
            fragmentTransaction.add(R.id.fragment_placeholder, parentalControlSetupFragment);
        }

        //hide the other fragments if they are showing
        if (songListFragment.isAdded()) {
            fragmentTransaction.hide(songListFragment);
        }
        if (searchSongFragment.isAdded()) {
            fragmentTransaction.hide(searchSongFragment);
        }
        if (nowPlayingFragment.isAdded()){
            fragmentTransaction.hide(nowPlayingFragment);
        }
        if(resetPINFragment.isAdded()){
            fragmentTransaction.hide(resetPINFragment);
        }
        fragmentTransaction.addToBackStack(null); //add to back stack so we can return to this fragment
        fragmentTransaction.commit();
    }


    public void showSearchResultsFragment() {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //if the fragment is already in the container, show it

        searchSongFragment = new SearchResultsFragment();
        fragmentTransaction.remove(songListFragment);
        fragmentTransaction.add(R.id.fragment_placeholder, searchSongFragment);

        if (nowPlayingFragment.isAdded()) {
            fragmentTransaction.hide(nowPlayingFragment);
        }
        if(parentalControlSetupFragment.isAdded()){
            fragmentTransaction.hide(parentalControlSetupFragment);
        }
        if(searchSongFragment.isAdded()){
            fragmentTransaction.hide(searchSongFragment);
        }
        if(parentalControlSetupFragment.isAdded()){
            fragmentTransaction.hide(parentalControlSetupFragment);
        }
        if(resetPINFragment.isAdded()){
            fragmentTransaction.hide(resetPINFragment);
        }

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

    private String[] getSongNames(ArrayList<Song> songList) {
        String[] songNames = new String[songList.size()];
        for (int i = 0; i < songList.size(); i++) {
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
    }

    // PAUSE BUTTON
    public void onClickPause(View view) {
        String response = mediaPlayerController.pauseSong();
        Log.i(TAG, response);

    }

    //RESUME BUTTON
    public void onClickResume(View view) {
        //make sure a song is actually paused
        if (MusicPlayerState.getInstance().isSongPaused()) {
            Song pausedSong = MusicPlayerState.getInstance().getCurrentlyPlayingSong();   //get the current playing song from the app state
            String response = mediaPlayerController.resumeSong();
            Log.i(TAG, response); //display result of operation to log
        } else {
            Log.i(TAG, "Song is not paused");
        }

    }

    //NEXT BUTTON
    public void onClickPlayNext(View view) {
        String response = mediaPlayerController.playNextSong(this);
        Log.i(TAG, response);
    }

    //PREVIOUS BUTTON
    public void onClickPlayPrevious(View view) {
        mediaPlayerController.pauseSong();
        String response = mediaPlayerController.playPreviousSong(this);
        Log.i(TAG, response);
    }

    //SHUFFLE Button
    public void onClickShuffle(View view) {
        String response = mediaPlayerController.setShuffle();
        Log.i(TAG, response);
    }

    //REPEAT button
    public void onClickRepeat(View view) {
        String response = mediaPlayerController.setRepeat();
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
            for (int i = 0; i < sResult.size(); i++)
                ss.add(sResult.get(i));
            result = getSongNames(ss); //get the names of the songs in order to populate the listview in the results fragment
            showSearchResultsFragment(); //show search fragment
        }
    }

    public void update(Observable observable, Object o) {
        if (observable instanceof DatabaseUpdatedObservable) {
            switch (((DatabaseUpdatedObservable) observable).getState()) {
                case DatabaseUpdated:
                    getSongsFromPersistance();
                    refreshSongList();
                    break;
                case DatabaseEmpty:
                    break;
            }
        }
        else if (observable instanceof ParentalControlStatusObservable) {
            boolean parentalModeOn = ((ParentalControlStatusObservable) observable).getParentalControlStatusBoolean();

            if (parentalModeOn) {
                getUnflaggedSongsFromPersistence();
                refreshSongList();
            } else {
                getSongsFromPersistance();
                refreshSongList();
            }
        }
    }
}
