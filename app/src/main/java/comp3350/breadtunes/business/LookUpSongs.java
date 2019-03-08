package comp3350.breadtunes.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.objects.Song;

public class LookUpSongs {
    List<Song> allSongs;
    List<Album> allAlbums;
    List<Artist> allArtists;

    public LookUpSongs(List<Song> songs, List<Album> albums, List<Artist> artists) {
        allSongs = songs;
        allAlbums = albums;
        allArtists = artists;
    }

    public LookUpSongs(List<Song> songs){
        allSongs = songs;
    }

    public List<Song> searchSongs(String input) {
        List<Song> matchingSongs = new ArrayList<>();

        for (int i = 0; i < allSongs.size(); i++) {
            Song ss = allSongs.get(i);

            if (input.length() != 0 && ss.getName().toUpperCase().contains(input.toUpperCase())) {
                matchingSongs.add(ss);
            }
        }

        return matchingSongs;
    }

    public List<Album> searchAlbums(String input){
        List<Album> matchingAlbums = new ArrayList<>();

        for (int i = 0; i < allAlbums.size(); i++) {
            Album al = allAlbums.get(i);

            if (input.length() != 0 && al.getName().toUpperCase().contains(input.toUpperCase())) {
                matchingAlbums.add(al);
            }
        }
        return matchingAlbums;
    }

    public List<Artist> searchArtists(String input){
        List<Artist> matchingArtists = new ArrayList<>();

        for(int i = 0; i < allArtists.size(); i++) {
            Artist ar = allArtists.get(i);

            if (input.length() != 0 && ar.getName().toUpperCase().contains(input.toUpperCase())){
                matchingArtists.add(ar);
            }
        }
        return matchingArtists;
    }

    //method added by Mario, put in here as told by a code smell
    public static Song getSong(ArrayList<Song> songList, String songName){
        for (Song song: songList) {
            if (song.getName().equals(songName)) {
                return song;
            }
        }
        return null;
    }


}
