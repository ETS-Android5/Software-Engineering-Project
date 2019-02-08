package comp3350.breadtunes.business;

import android.app.Activity;

import java.util.List;

import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.stubs.SongPersistenceStub;
import comp3350.breadtunes.presentation.HomeActivity;

//class that populates the list in the home activity and helps with other tasks such as updating the gui
public class HomeActivityHelper implements Runnable {

    private List<Song> songList;
    private MusicPlayerState appState;
    private Activity homeActivity; //reference to the home activity, necessary to update its gui

    public HomeActivityHelper(Activity homeActivity, List<Song> songList){
        this.songList = songList;
        this.appState = null;
        this.homeActivity = homeActivity;
    }

    //return the song object associated with the string song name
    public Song getSong(String songName){
        for (Song song: songList) {
           if (song.getName().equals(songName)) {
               return song;
           }
        }
        return null;
    }

    //return a String array with all song names
    public String[] getSongNames(){
        String[] songNames = new String[songList.size()];
        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
    }

    public MusicPlayerState getAppState() {
        return appState;
    }

    public void setAppState(MusicPlayerState appState) {
        this.appState = appState;
    }

    //update the now playing part of the gui: more info https://developer.android.com/training/multiple-threads/communicate-ui
    //more info : https://stackoverflow.com/questions/11140285/how-do-we-use-runonuithread-in-android
    //update the gui
    public void run() {
        homeActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Song newSong = appState.getCurrentlyPlayingSong();

                if (newSong != null) {
                    String songTitle = newSong.getName();
                    String albumName = newSong.getAlbum().getName();
                    String artistName = newSong.getArtist().getName();

                    String message = String.format("Song: %s\nAlbum: %s\nArtist: %s", songTitle, albumName, artistName);

                    HomeActivity.nowPlayingGUI.setText(message);
                }
            }
        });
    }
}
