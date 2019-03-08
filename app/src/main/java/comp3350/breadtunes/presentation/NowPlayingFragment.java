package comp3350.breadtunes.presentation;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import comp3350.breadtunes.R;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements Observer {

    public HomeActivity homeActivity;

    public static TextView nowPlayingSongGui;
    public static TextView nowPlayingAlbumGui;
    public static TextView nowPlayingArtistGui;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeActivity = (HomeActivity) getActivity();
        MusicPlayerState.getInstance().subscribeToSongChange(this);
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        nowPlayingSongGui = (TextView) view.findViewById(R.id.song_name);
        nowPlayingArtistGui = (TextView) view.findViewById(R.id.artist_name);
        nowPlayingAlbumGui = (TextView) view.findViewById(R.id.album_name);

        //populate the fields in the fragment
        nowPlayingSongGui.setText(MusicPlayerState.getInstance().getCurrentlyPlayingSong().getName());
        nowPlayingAlbumGui.setText(MusicPlayerState.getInstance().getCurrentlyPlayingSong().getAlbumName());
        nowPlayingArtistGui.setText(MusicPlayerState.getInstance().getCurrentlyPlayingSong().getArtistName());

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
          inflater.inflate(R.menu.now_playing_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object o) {

        SongObservable songObservable = (SongObservable) observable;

        Song song = songObservable.getSong();

        String songName = song.getName();
        String albumName = song.getAlbumName();
        String artistName = song.getArtistName();

        nowPlayingSongGui.setText(songName);
        nowPlayingAlbumGui.setText(albumName);
        nowPlayingArtistGui.setText(artistName);

    }


}
