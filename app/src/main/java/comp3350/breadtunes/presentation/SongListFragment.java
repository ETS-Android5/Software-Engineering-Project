package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;

import static comp3350.breadtunes.presentation.HomeActivity.sList;

/**
 * A simple {@link Fragment} subclass.
 */

// REFERENCE : https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments

public class SongListFragment extends Fragment implements Observer {


    public HomeActivity homeActivity;
    private final String TAG = "HomeActivity";

    public static Button nowPlayingSongGui;




    public SongListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();
        homeActivity.musicPlayerState.subscribeToSongChange(this);
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        //populate the list of songs
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.songlist_element, homeActivity.songNamesToDisplay);
        final ListView activitySongList = (ListView) view.findViewById(R.id.songList);
        activitySongList.setAdapter(adapter);

        //get reference to the now playing song gui
        nowPlayingSongGui = (Button) view.findViewById(R.id.song_name);

        nowPlayingSongGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homeActivity.musicPlayerState.isSongPaused() || homeActivity.musicPlayerState.isSongPlaying())
                    homeActivity.showNowPlayingFragment();
            }
        });

        //set on item click listener to react to list clicks
        activitySongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
                Log.i(TAG, "Clicked on "+selectedSongName);
                //get the song object associated with the song name that was clicked
                Song selectedSong = LookUpSongs.getSong(sList, selectedSongName);

                if(selectedSong != null) {
                    int songId = getResources().getIdentifier(selectedSong.getRawName(), "raw", getContext().getPackageName());
                    String playStatus = homeActivity.mediaPlayerController.playSong(selectedSong, songId); //                             play the song!
                    Log.e(TAG, playStatus);
                }
            }
        });// on item click listener for listview

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
        //Toast.makeText(homeActivity, "on activity created called in song list fragment...", Toast.LENGTH_SHORT).show();
    }

    //when pressing back button and launching app again, the onactivity created

    @Override
    public void update(Observable observable, Object o) {
        SongObservable songObservable = (SongObservable) observable;
        Song song = songObservable.getSong();
        String songName = song.getName();
        nowPlayingSongGui.setText(songName);

    }

    public void onStart(){
        super.onStart();
        nowPlayingSongGui.setText(homeActivity.musicPlayerState.getCurrentlyPlayingSongName());
    }





}
