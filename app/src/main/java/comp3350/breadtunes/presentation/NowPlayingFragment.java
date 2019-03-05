package comp3350.breadtunes.presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import comp3350.breadtunes.R;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeActivity = (HomeActivity) getActivity();
        homeActivity.musicPlayerState.subscribeToSongChange(this);
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        nowPlayingSongGui = (TextView) view.findViewById(R.id.song_name);
        nowPlayingArtistGui = (TextView) view.findViewById(R.id.artist_name);
        nowPlayingAlbumGui = (TextView) view.findViewById(R.id.album_name);

        //populate the fields in the fragment
        nowPlayingSongGui.setText(homeActivity.musicPlayerState.getCurrentlyPlayingSong().getName());
        nowPlayingAlbumGui.setText(homeActivity.musicPlayerState.getCurrentlyPlayingSong().getAlbumName());
        nowPlayingArtistGui.setText(homeActivity.musicPlayerState.getCurrentlyPlayingSong().getArtistName());

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
