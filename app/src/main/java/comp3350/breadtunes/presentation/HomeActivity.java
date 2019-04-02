package comp3350.breadtunes.presentation;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.observables.DatabaseUpdatedObservable;
import comp3350.breadtunes.business.observables.ParentalControlStatusObservable;
import comp3350.breadtunes.presentation.MediaController.MediaPlayerController;
import comp3350.breadtunes.services.ObservableService;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.AbstractActivities.BaseActivity;

import android.app.SearchManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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



public class HomeActivity extends BaseActivity implements Observer {
    private MediaPlayerController mediaPlayerController;  // controls playback operations
    public static ArrayList<Song> sList = new ArrayList<>();
    String[] songNamesToDisplay; //song names displayed in the songlist fragment
    private final String TAG = "HomeActivity"; // tag for logs
    String[] queueFragSongsDisplay; //songs displayed in the queue fragment

    private FragmentTransaction fragmentTransaction;

    // fragments used in the main activity
    private NowPlayingFragment nowPlayingFragment;
    private SearchResultsFragment searchSongFragment;
    private SongListFragment songListFragment;
    private QueueFragment queueSongFragment;
    private ParentalControlSetupFragment parentalControlSetupFragment;
    private ResetPINFragment resetPINFragment;

    // the list of songs acquired from Persistance layer
    List<Song> persistanceSongList;

    //variables for getting search query and launching search results fragment
    List<Song> sResult;
    public static String[] result;
    LookUpSongs findSong; //loook up service to search for songs on the search bar


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set subscriptions
        ObservableService.subscribeToDatabaseStateChanges(this);
        ObservableService.subscribeToParentalModeStatus(this);

        // Prepare fragments
        if (savedInstanceState == null) {
            resetPINFragment = new ResetPINFragment();
            nowPlayingFragment = new NowPlayingFragment();
            searchSongFragment = new SearchResultsFragment();
            songListFragment = new SongListFragment();
            queueSongFragment = new QueueFragment();
            parentalControlSetupFragment = new ParentalControlSetupFragment();
        } else {
            //retrieve the state of the fragment
            songListFragment = (SongListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "songlist_fragment");
        }

        // Retrieve songs from database
        getSongsFromPersistance();

        // Create media controller
        mediaPlayerController = new MediaPlayerController(ServiceGateway.getMusicPlayerState());

        // Create song search helper
        findSong = new LookUpSongs(ServiceGateway.getMusicPlayerState());

        // Show the list of songs
        getSongNameList();
        showSongListFragment();

        handleIntent(getIntent());
    }

    public void getSongsFromPersistance() {
        persistanceSongList = new ArrayList<>();
        persistanceSongList.addAll(ServiceGateway.getSongPersistence().getAll());
    }

    public void getUnflaggedSongsFromPersistence() {
        persistanceSongList = new ArrayList<>();
        persistanceSongList.addAll(ServiceGateway.getSongPersistence().getAllNotFlagged());
    }

    //update the song names in the list that gets displayed
    public void getSongNameList() {
        sList = new ArrayList<>();
        sList.addAll(persistanceSongList);
        songNamesToDisplay = new String[persistanceSongList.size()];
        for (int i = 0; i < songNamesToDisplay.length; i++)
            songNamesToDisplay[i] = persistanceSongList.get(i).getName();
    }

    public void refreshSongList() {
        getSongNameList();
        ServiceGateway.getMusicPlayerState().setCurrentSongList(sList);
        songListFragment = new SongListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, songListFragment).commitAllowingStateLoss();
    }

    protected void onResume() {
        super.onResume();

        boolean parentalModeOn = ServiceGateway.getMusicPlayerState().getParentalControlModeOn();

        if (parentalModeOn) {
            getUnflaggedSongsFromPersistence();
        } else {
            getSongsFromPersistance();
        }

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
    }


    //method called by fragments to avoid context issues
    public void playSong(Song song) {
        String playStatus = mediaPlayerController.playSong(song, this);
        if(playStatus.equals("Parental control does not allow this song to be played")) { //only show this message to user
            Toast.makeText(this, playStatus, Toast.LENGTH_SHORT).show();
        }else{
        }
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
        if(resetPINFragment.isAdded()){
            fragmentTransaction.hide(resetPINFragment);
        }

        fragmentTransaction.commit();
    }

    public void showQueueFragment() {
        if(ServiceGateway.getMusicPlayerState().getQueueSize() > 0) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            queueFragSongsDisplay = ServiceGateway.getMusicPlayerState().getQueueSongNames();
            //if the fragment is already in the container, show it

            queueSongFragment = new QueueFragment();
            fragmentTransaction.remove(songListFragment);
            fragmentTransaction.add(R.id.fragment_placeholder, queueSongFragment);

            if (nowPlayingFragment.isAdded()) {
                fragmentTransaction.hide(nowPlayingFragment);
            }
            if (parentalControlSetupFragment.isAdded()) {
                fragmentTransaction.hide(parentalControlSetupFragment);
            }
            if (searchSongFragment.isAdded()) {
                fragmentTransaction.hide(searchSongFragment);
            }
            if(resetPINFragment.isAdded()){
                fragmentTransaction.hide(resetPINFragment);
            }

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(this, "Queue is empty", Toast.LENGTH_LONG).show();
        }
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


    public void onClickPlayPause(View view){

        ImageButton button = (ImageButton)view;

        if(ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong()  != null) {
            //if song is playing then we want to pause
            if (ServiceGateway.getMusicPlayerState().isSongPlaying()) {

                String response = mediaPlayerController.pauseSong();
                Log.i(TAG, response);
                button.setImageResource(R.drawable.play);

            } else {
                //if song not playing then we want resume playing
                String response = mediaPlayerController.resumeSong();
                Log.i(TAG, response);
                button.setImageResource(R.drawable.pause);
            }
        }
    }

    public void updateSongListFragmentButtons(){
        songListFragment.updateButtons();
    }


    //QUEUE BUTTON
    public void onClickViewQueue(View view){
        showQueueFragment();
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
        nowPlayingFragment.updateShuffleRepeatButtons();
        Log.i(TAG, response);
    }

    //REPEAT button
    public void onClickRepeat(View view) {
        String response = mediaPlayerController.setRepeat();
        nowPlayingFragment.updateShuffleRepeatButtons();
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
            switch (((DatabaseUpdatedObservable) observable).getValue()) {
                case DatabaseUpdated:
                    getSongsFromPersistance();
                    refreshSongList();
                    break;
                case DatabaseEmpty:
                    break;
            }
        }
        else if (observable instanceof ParentalControlStatusObservable) {
            boolean parentalModeOn = ((ParentalControlStatusObservable) observable).getValue();

            ServiceGateway.getMusicPlayerState().clearQueue(); // clear the queue on any mode change
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
