package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import comp3350.breadtunes.R;
import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.objects.Song;

import static comp3350.breadtunes.presentation.HomeActivity.sList;


public class SearchResultsFragment extends Fragment  {

    public HomeActivity homeActivity;

    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeActivity = (HomeActivity) getActivity();
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
            //get the song object associated with the song name that was clicked
            Song selectedSong = LookUpSongs.getSong(sList, selectedSongName);

            homeActivity.playSong(selectedSong);
        });// on item click listener for listview
    }
}
