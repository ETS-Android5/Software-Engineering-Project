package comp3350.breadtunes.business;

import java.util.List;
import java.util.Observable;
import comp3350.breadtunes.objects.Song;

//class that populates the list in the home activity and helps with other tasks such as updating the gui
public class Utilities extends Observable {

    private List<Song> songList;

    public Utilities(List<Song> songList){
        this.songList = songList;
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
}
