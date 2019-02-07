package comp3350.breadtunes.business;

import android.app.Activity;
import android.view.View;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.HomeActivity;

//class that gets called every time the now playing gui part of an activity needs to be updated
public class NowPlayingUpdater implements  Runnable {



    private Activity callingAcitivity;
    private MusicPlayerState appState;


    public NowPlayingUpdater(Activity callingActivity, MusicPlayerState appState){
        this.callingAcitivity = callingActivity;
        this.appState = appState;
    }


    @Override
    public void run() {

        callingAcitivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Song newSong = appState.getCurrentlyPlayingSong();
                if(newSong != null) {
                    String songTitle = newSong.getName();
                    HomeActivity.nowPlayingGUI.setText(songTitle);
                }
            }
        });

    }
}//now playing updater
