package comp3350.breadtunes.presentation;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import comp3350.breadtunes.R;
import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.loaders.AlbumArtLoader;
import comp3350.breadtunes.services.ObservableService;
import comp3350.breadtunes.services.ServiceGateway;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements Observer {

    public HomeActivity homeActivity;
    private String TAG = "Now Playing Fragment";

    public ImageView songArt;
    public TextView nowPlayingSongGui;
    public TextView nowPlayingAlbumGui;
    public TextView nowPlayingArtistGui;
    public TextView currentPositionGui;
    public TextView songDurationGui;
    public SeekBar seekBar;
    private Handler handler;

    private Uri defaultAlbumArt;

    public NowPlayingFragment() {
        defaultAlbumArt = Uri.parse("android.resource://comp3350.breadtunes/drawable/default_album_art");
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public void onPause(){
        super.onPause();
        homeActivity.updateSongListFragmentButtons();
        Log.i(TAG, "user pressed back button!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeActivity = (HomeActivity) getActivity();
        ObservableService.subscribeToSongChanges(this);
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get reference to UI elements
        nowPlayingSongGui = (TextView) view.findViewById(R.id.song_name_nowplaying_fragment);
        nowPlayingArtistGui = (TextView) view.findViewById(R.id.artist_name);
        nowPlayingAlbumGui = (TextView) view.findViewById(R.id.album_name);
        currentPositionGui = (TextView) view.findViewById(R.id.current_position);
        songDurationGui = (TextView) view.findViewById(R.id.song_duration);
        songArt = (ImageView) view.findViewById(R.id.song_art);
        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);

        handler = new Handler();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    ServiceGateway.getMediaManager().seekTo(i);
                    seekBar.setMax(ServiceGateway.getMediaManager().getDuration());
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Song currentSong = ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong();
        updateGuiElementsForSong(currentSong);
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
        if (observable instanceof  SongObservable) {
            // update is called even when Fragment is not in focus, so catch null pointer exceptions
            // in this case.
            try {
                SongObservable songObservable = (SongObservable) observable;
                updateGuiElementsForSong(songObservable.getValue());
            }catch(NullPointerException e){
                Log.i(TAG, "Avoided null ptr exception when updating UI");
            }
        }
    }

    private void updateGuiElementsForSong(Song currentSong) {
        // Song information
        nowPlayingSongGui.setText(currentSong.getName());
        nowPlayingAlbumGui.setText(currentSong.getAlbumName());
        nowPlayingArtistGui.setText(currentSong.getArtistName());
        setAlbumArt(currentSong);

        // Seek bar
        songDurationGui.setText(ServiceGateway.getMediaManager().getDurationString());
        seekBar.setMax(ServiceGateway.getMediaManager().getDuration());
        changeSeekbar();

        // Buttons
        updatePlayPauseButtons();
        updateShuffleRepeatButtons();
    }

    public void updateShuffleRepeatButtons(){

        ImageButton shuffleButton = (ImageButton) Objects.requireNonNull(getView().findViewById(R.id.shuffle_button));
        if(ServiceGateway.getMusicPlayerState().getShuffleMode()){
            shuffleButton.setImageResource(R.drawable.shuffle_on);
        }else{
            shuffleButton.setImageResource(R.drawable.shuffle);
        }

        ImageButton repeatButton = (ImageButton) Objects.requireNonNull(getView().findViewById(R.id.repeat_button));
        if(ServiceGateway.getMusicPlayerState().getRepeatMode()){
            repeatButton.setImageResource(R.drawable.repeat_on);
        }else{
            repeatButton.setImageResource(R.drawable.repeat);
        }
    }

    public void updatePlayPauseButtons() {
        if(ServiceGateway.getMusicPlayerState().isSongPlaying()){
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause_button);
            button.setImageResource(R.drawable.pause);
        }else{
            ImageButton button = (ImageButton) getView().findViewById(R.id.play_pause_button);
            button.setImageResource(R.drawable.play);
        }
    }

    private void setAlbumArt(Song song) {
        AlbumArtLoader artLoader = ServiceGateway.getAlbumArtLoader();
        Uri albumArt = artLoader.getAlbumArt(song);

        if (albumArt != null) {
            songArt.setImageURI(null);
            songArt.setImageURI(albumArt);
        } else {
            songArt.setImageURI(null);
            songArt.setImageURI(defaultAlbumArt);
        }
    }

    public void changeSeekbar(){
        //update seekBar and currentDurationGui
        seekBar.setProgress(ServiceGateway.getMediaManager().getCurrentPosition());
        currentPositionGui.setText(ServiceGateway.getMediaManager().getCurrentPositionString());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                changeSeekbar();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
