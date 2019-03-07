package comp3350.breadtunes.presentation;


import android.app.SearchManager;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.Observable;
import java.util.Observer;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
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
        setHasOptionsMenu(true);Toast.makeText(homeActivity, "on create called in song list..", Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeActivity.musicPlayerState.subscribeToSongChange(this);
        Toast.makeText(homeActivity, "on create view called in song list", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_song_list, container, false);

    }



    public void onViewCreated(View view, Bundle savedInstanceState){

        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();

    }

    public void onResume(){
        super.onResume();
        homeActivity.musicPlayerState.subscribeToSongChange(this);
        getSongNames();
        populateSongListView();
        registerOnClickForSonglist();
        registerOnClickForNowPlayingButton();
        Toast.makeText(homeActivity, "on resume in song list fragment...", Toast.LENGTH_SHORT).show();

        Toast.makeText(homeActivity, homeActivity.musicPlayerState.getMusicPlayerState(), Toast.LENGTH_SHORT).show();
        nowPlayingSongGui.setText(homeActivity.musicPlayerState.getCurrentlyPlayingSongName());

    }

    public void onStop(){
        super.onStop();
       /// Toast.makeText(homeActivity, "on stop called in song list fragment...", Toast.LENGTH_SHORT).show();
    }

    public void onPause(){
        super.onPause();
        //Toast.makeText(homeActivity, "on pause in song list fragment...", Toast.LENGTH_SHORT).show();
    }

    public void onDestroyView(){
        super.onDestroyView();
       // Toast.makeText(homeActivity, "on destroy view called song list fragment...", Toast.LENGTH_SHORT).show();
    }



    //save the fragment state so it can be restored
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //Toast.makeText(homeActivity, "on saved instance state called in song list fragment...", Toast.LENGTH_SHORT).show();

    }
    //only called when you press home button, but not back button

    //restore the fragments state
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
       // Toast.makeText(homeActivity, "on activity created called in song list fragment...", Toast.LENGTH_SHORT).show();
    }

    //when pressing back button and launching app again, the onactivity created



    public void onStart(){
        super.onStart();
        Toast.makeText(homeActivity, "onStart in song list fragment...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void update(Observable observable, Object o) {
        SongObservable songObservable = (SongObservable) observable;
        Song song = songObservable.getSong();
        String songName = song.getName();
        nowPlayingSongGui.setText(songName);

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
                if(homeActivity.musicPlayerState.isSongPaused() || homeActivity.musicPlayerState.isSongPlaying()) {
                    homeActivity.showNowPlayingFragment();
                }else{
                    Log.e(TAG, "no song playing or paused");
                }
            }
        });
    }


}
