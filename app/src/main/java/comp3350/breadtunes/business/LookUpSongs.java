package comp3350.breadtunes.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Song;

public class LookUpSongs {


    public List<Song> searchSongs(String input) {
        List<Song> matchingSongs = new ArrayList<>();

        for (int i = 0; i < MusicPlayerState.getInstance().getCurrentSongList().size(); i++) {
            Song ss = MusicPlayerState.getInstance().getCurrentSongList().get(i);

            if (input.length() != 0 && ss.getName().toUpperCase().contains(input.toUpperCase())) {
                matchingSongs.add(ss);
            }
        }

        return matchingSongs;
    }

    // DO NOT REMOVE, it is used; talk to mario if questions
    public static Song getSong(List<Song> songList, String songName){
        for (Song song: songList) {
            if (song.getName().equals(songName)) {
                return song;
            }
        }
        return null;
    }

    //DO NOT REMOVE, - talk to Mario if questions
    public static Song getSong(ArrayList<Song> songList, String songName){
        for (Song song: songList) {
            if (song.getName().equals(songName)) {
                return song;
            }
        }
        return null;
    }


}
