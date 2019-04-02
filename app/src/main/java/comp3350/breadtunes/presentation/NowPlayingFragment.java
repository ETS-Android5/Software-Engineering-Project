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
import comp3350.breadtunes.business.MusicPlayerState;
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
    public SeekBar seekBar;
    private Handler handler;
    private Runnable runnable;
    private String TAG = "Now Playing Fragment";

    public static ImageView songArt;
    public static TextView nowPlayingSongGui;
    public static TextView nowPlayingAlbumGui;
    public static TextView nowPlayingArtistGui;

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

    public void onViewCreated(View view, Bundle savedInstanceState){
        nowPlayingSongGui = (TextView) view.findViewById(R.id.song_name);
        nowPlayingArtistGui = (TextView) view.findViewById(R.id.artist_name);
        nowPlayingAlbumGui = (TextView) view.findViewById(R.id.album_name);
        songArt = (ImageView) view.findViewById(R.id.song_art);

        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        handler = new Handler();


        Song currentSong = ServiceGateway.getMusicPlayerState().getCurrentlyPlayingSong();

        //populate the fields in the fragment
        nowPlayingSongGui.setText(currentSong.getName());
        nowPlayingAlbumGui.setText(currentSong.getAlbumName());
        nowPlayingArtistGui.setText(currentSong.getArtistName());
        setAlbumArt(currentSong);


        if(ServiceGateway.getMusicPlayerState().isSongPlaying()){
            seekBar.setMax(ServiceGateway.getMediaManager().getDuration());
            changeSeekbar();
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    ServiceGateway.getMediaManager().seekTo(i);
                    seekBar.setMax(ServiceGateway.getMediaManager().getDuration());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Make sure the button images are all correct
        updatePlayPauseButtons();
        updateShuffleRepeatButtons();
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

            SongObservable songObservable = (SongObservable) observable;

            Song song = songObservable.getValue();

            seekBar.setMax(ServiceGateway.getMediaManager().getDuration());
            changeSeekbar();

            String songName = song.getName();
            String albumName = song.getAlbumName();
            String artistName = song.getArtistName();

            nowPlayingSongGui.setText(songName);
            nowPlayingAlbumGui.setText(albumName);
            nowPlayingArtistGui.setText(artistName);
            setAlbumArt(song);

            //try and catch is necessary, as the fragment will still receive updates when it is not on focus and when searching for
            //layout elements, it will get a null ptr
            try {
                updatePlayPauseButtons();
                updateShuffleRepeatButtons();
            }catch(Exception e){
                Log.i(TAG, "Avoided null ptr exception when updating UI");
            }
        }
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

    private void updatePlayPauseButtons() {
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
        seekBar.setProgress(ServiceGateway.getMediaManager().getCurrentPosition());

        runnable = new Runnable(){
            @Override
            public void run(){
                changeSeekbar();
            }
        };
        handler.postDelayed(runnable, 50);
    }


}
