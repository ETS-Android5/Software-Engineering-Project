package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import comp3350.breadtunes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsFragment extends Fragment {

    public HomeActivity homeActivity;
    public static TextView searchList;

    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeActivity = (HomeActivity) getActivity();
        //homeActivity..subscribeToSongChange(this);
        //homeActivity.findSong.searchSongs();
        return inflater.inflate(R.layout.fragment_search_results2, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        searchList = (TextView) view.findViewById(R.id.resultDisplay);
        //searchList.setText(homeActivity.findSong.searchSongs());

    }

}
