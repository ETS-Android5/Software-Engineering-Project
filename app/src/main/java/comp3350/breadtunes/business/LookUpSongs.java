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

    public LookUpSongs(Services serviceProvider) {
        allSongs = serviceProvider.getSongPersistence().getAll();
        allAlbums = serviceProvider.getAlbumPersistence().getAll();
        allArtists = serviceProvider.getArtistPersistence().getAll();
    }

    public ArrayList<Object> searchSongs(String input) {
        ArrayList<Object>all = new ArrayList<>();

        for (int i = 0; i < allSongs.size() - 1; i++) {
            Song ss = allSongs.get(i);

            if (input.compareTo(ss.getName()) == 0) {
                all.add(ss);
            }
        }
        return all;
    }

    public ArrayList<Object> searchAlbums(String input){
        ArrayList<Object>all = new ArrayList<>();

        for (int i = 0; i < allAlbums.size() - 1; i++) {
            Album al = allAlbums.get(i);

            if (input.compareTo(al.getName()) == 0) {
                all.add(al);
            }
        }
        return all;
    }

    public ArrayList<Object> searchArtists(String input){
        ArrayList<Object>all = new ArrayList<>();

        for(int i = 0; i < allArtists.size() - 1; i++) {
            Artist ar = allArtists.get(i);

            if (input.compareTo(ar.getName()) == 0){
                all.add(ar);
            }
        }
        return all;
    }
}
