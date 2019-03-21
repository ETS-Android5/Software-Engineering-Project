package comp3350.breadtunes.presentation;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.hsqldb.lib.tar.TarFileOutputStream;

import java.util.Observable;
import java.util.Observer;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.observables.PlayModeObservable;
import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;

import static comp3350.breadtunes.presentation.HomeActivity.sList;


// REFERENCE : https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments

public class SongListFragment extends Fragment implements Observer {


    public HomeActivity homeActivity;
    ListView activitySongList;
    String[] songNameList;
    private final String TAG = "HomeActivity";
    public static Button nowPlayingSongGui;


    public SongListFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) getActivity();
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MusicPlayerState.getInstance().subscribeToSongChange(this);
        MusicPlayerState.getInstance().subscribeToPlayModeChange(this);
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }



    public void onViewCreated(View view, Bundle savedInstanceState){
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();
    }

    public void onResume(){
        super.onResume();
        MusicPlayerState.getInstance().subscribeToSongChange(this);
        getSongNames();
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();
        nowPlayingSongGui.setText(MusicPlayerState.getInstance().getCurrentlyPlayingSongName()+"\n"+MusicPlayerState.getInstance().getPlayMode());

    }

    public void onStop(){
        super.onStop();
    }

    public void onPause(){
        super.onPause();
    }

    public void onDestroyView(){
        super.onDestroyView();
    }


    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }


    public void onStart(){
        super.onStart();

    }

    @Override
    public void update(Observable observable, Object o) {

        if(observable instanceof SongObservable){
            SongObservable songObservable = (SongObservable) observable;
            Song song = songObservable.getSong();
            String songName = song.getName();
            nowPlayingSongGui.setText(songName+"\n"+MusicPlayerState.getInstance().getPlayMode());
        }else{
            PlayModeObservable playModeObservable = (PlayModeObservable) observable;
            String playMode = playModeObservable.getPlayMode();;
            String songName = MusicPlayerState.getInstance().getCurrentlyPlayingSong().getName();
            nowPlayingSongGui.setText(songName+"\n"+playMode);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) homeActivity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(homeActivity.getComponentName()));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.parental_lock_on){

            //logic to check in the database if credentials have been set up
            // if(!credentialManager.credentialsHaveBeenSet())
            //      homeActivity.showParentalControlSetupFragment();
            // else
            //      showLoginFragment
            //               inside login fragment have option for "forgot password"



            homeActivity.showParentalControlSetupFragment();
        }
        else if(id == R.id.parental_lock_off){
            //musicPlayerState.setParentalControl( false );
            showPINInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getSongNames(){
        songNameList = homeActivity.songNamesToDisplay.clone();
    }


    public void populateSongListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.songlist_element, homeActivity.songNamesToDisplay);
        activitySongList = (ListView) getView().findViewById(R.id.songList);
        activitySongList.setAdapter(adapter);
    }

    public void registerOnClickForSonglist(){
        activitySongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
                Log.i(TAG, "Clicked on "+selectedSongName);
                //get the song object associated with the song name that was clicked
                Song selectedSong = LookUpSongs.getSong(sList, selectedSongName);

                homeActivity.playSong(selectedSong);

            }
        });// on item click listener for listview
    }


    public void registerOnClickForNowPlayingButton(){
        //get reference to the now playing song gui
        nowPlayingSongGui = (Button) getView().findViewById(R.id.song_name);

        nowPlayingSongGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicPlayerState.getInstance().isSongPaused() || MusicPlayerState.getInstance().isSongPlaying()) {
                    homeActivity.showNowPlayingFragment();
                }else{
                    Log.e(TAG, "no song playing or paused");
                }
            }
        });
    }


    private void showPINInputDialog() {
        final EditText taskEditText = new EditText(homeActivity);
        AlertDialog dialog = new AlertDialog.Builder(homeActivity)
                .setTitle("Parental Lock")
                .setMessage("What is your PIN?")
                .setView(taskEditText)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        Toast.makeText(homeActivity, "PIN "+task,Toast.LENGTH_LONG ).show();
                    }
                })
                .setNeutralButton("Forgot Password?", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        homeActivity.showPINResetFragment();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }



}
