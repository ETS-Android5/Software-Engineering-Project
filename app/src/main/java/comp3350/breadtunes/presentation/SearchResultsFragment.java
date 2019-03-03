package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
public class SearchResultsFragment extends Fragment implements Observer {

    public HomeActivity homeActivity;
    private final String TAG = "SearchResultsFragment: ";

    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeActivity = (HomeActivity) getActivity();
        homeActivity.musicPlayerState.subscribeToSongChange(this);
        return inflater.inflate(R.layout.fragment_search_results2, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        //populate the list of songs associated with the search input
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.songlist_element,homeActivity.result);
        final ListView activitySongList = (ListView) view.findViewById(R.id.resultDisplay);
        activitySongList.setAdapter(adapter);

        //set on item click listener to react to list clicks
        activitySongList.setOnItemClickListener((adapterView, view1, i, l) -> {
            String selectedSongName = (String) adapterView.getItemAtPosition(i);     //get the name of the song being played
            Log.i(TAG, "Clicked on " + selectedSongName);
            //get the song object associated with the song name that was clicked
            Song selectedSong = LookUpSongs.getSong(sList, selectedSongName);

            if (selectedSong != null) {
                int songId = getResources().getIdentifier(selectedSong.getRawName(), "raw", getContext().getPackageName());
                homeActivity.showNowPlayingFragment();
                String playStatus = homeActivity.mediaPlayerController.playSong(selectedSong, songId); //play the song!
                Log.e(TAG, playStatus);
            }
        });// on item click listener for listview
    }

    @Override
    public void update(Observable observable, Object o) {
        SongObservable songObservable = (SongObservable) observable;

        Song song = songObservable.getSong();

        String songName = song.getName();
        String artistName = song.getArtistName();
    }
}
