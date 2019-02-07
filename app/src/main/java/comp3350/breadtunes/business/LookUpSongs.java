package comp3350.breadtunes.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.application.Services;
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

    public ArrayList<Song> searchSongs(String input) {
        ArrayList<Song> matchingSongs = new ArrayList<>();

        for (int i = 0; i < allSongs.size() - 1; i++) {
            Song ss = allSongs.get(i);

            if (input.compareTo(ss.getName()) == 0) {
                matchingSongs.add(ss);
            }
        }
        return matchingSongs;
    }

    public ArrayList<Album> searchAlbums(String input){
        ArrayList<Album> matchingAlbums = new ArrayList<>();

        for (int i = 0; i < allAlbums.size() - 1; i++) {
            Album al = allAlbums.get(i);

            if (input.compareTo(al.getName()) == 0) {
                matchingAlbums.add(al);
            }
        }
        return matchingAlbums;
    }

    public ArrayList<Artist> searchArtists(String input){
        ArrayList<Artist> matchingArtists = new ArrayList<>();

        for(int i = 0; i < allArtists.size() - 1; i++) {
            Artist ar = allArtists.get(i);

            if (input.compareTo(ar.getName()) == 0){
                matchingArtists.add(ar);
            }
        }
        return matchingArtists;
    }
}
