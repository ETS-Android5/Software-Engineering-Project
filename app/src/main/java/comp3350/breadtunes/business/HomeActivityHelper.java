package comp3350.breadtunes.business;

import java.util.List;

import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.stubs.SongPersistenceStub;

                //class that populates the list in the home activity and helps with some misc tasks
public class HomeActivityHelper {


    private Services services;   //object to communicate with the song persistence
    private SongPersistenceStub songPersistenceStub;
    private List<Song> songList;


    public HomeActivityHelper(){
        services = new Services();
        songPersistenceStub = (SongPersistenceStub) services.getSongPersistence(); //get interface for getting songs from persistance
        songList = songPersistenceStub.getAll();
    }


    //return the song object associated with the string song name
    public Song getSong(String songName){
        Song song = songPersistenceStub.getSong(songName);
        return song;
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





}// home activity helper
