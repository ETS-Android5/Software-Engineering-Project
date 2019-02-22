package comp3350.breadtunes.business;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3350.breadtunes.objects.Song;

public class Utilities {
    //return the song object associated with the string song name
    public static Song getSong(ArrayList<Song> songList, String songName){

        for (Song song: songList) {
           if (song.getName().equals(songName)) {
               return song;
           }
        }
        return null;
    }

    //return a String array with all song names
    public static String[] getSongNames(ArrayList<Song> songList){
        String[] songNames = new String[songList.size()];
        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
    }
}
