package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    private final String TAG = "Song list fragment: ";

    public static TextView nowPlayingSongGui;
    public static TextView nowPlayingArtistGui;


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
        nowPlayingSongGui = (TextView) view.findViewById(R.id.song_name);
        nowPlayingArtistGui = (TextView) view.findViewById(R.id.artist_name);

        //@// TODO: 28/02/19 //add on click listener for the above views to launch the now playing fragment

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


    @Override
    public void update(Observable observable, Object o) {
        SongObservable songObservable = (SongObservable) observable;

        Song song = songObservable.getSong();

        String songName = song.getName();

        String artistName = song.getArtist().getName();

        nowPlayingSongGui.setText(songName);
        nowPlayingArtistGui.setText(artistName);


    }



}
