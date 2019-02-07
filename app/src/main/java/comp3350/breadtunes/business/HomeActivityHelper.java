package comp3350.breadtunes.business;

import android.app.Activity;

import java.util.List;

import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.stubs.SongPersistenceStub;
import comp3350.breadtunes.presentation.HomeActivity;

//class that populates the list in the home activity and helps with other tasks such as updating the gui
public class HomeActivityHelper implements Runnable{


    private Services services;   //object to communicate with the song persistence
    private SongPersistenceStub songPersistenceStub;
    private List<Song> songList;
    private MusicPlayerState appState;
    private Activity homeActivity; //reference to the home activity, necessary to update its gui

    public HomeActivityHelper(Activity homeActivity){
        services = new Services();
        songPersistenceStub = (SongPersistenceStub) services.getSongPersistence(); //get interface for getting songs from persistance
        songList = songPersistenceStub.getAll();
        appState = null;
        this.homeActivity = homeActivity;
    }


    //return the song object associated with the string song name
    public Song getSong(String songName){
        Song song = songPersistenceStub.getSong(songName);
        return song;
    }

    public void setAppState(MusicPlayerState state){
        this.appState = state;
    }


    //return a String array with all song names
    public String[] getSongNames(){
        String[] songNames = new String[songList.size()];
        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
    }

    // return the list of songs for the HomeActivity!
    public List<Song> getHomeActivitySongList(){
        return songList;
    }


    //update the gui
    public void run() {
        homeActivity.runOnUiThread(new Runnable()
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



}// home activity helper
