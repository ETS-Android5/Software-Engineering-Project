package comp3350.breadtunes.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.objects.Song;

import static comp3350.breadtunes.application.Services.getAlbumPersistence;
import static comp3350.breadtunes.application.Services.getArtistPersistence;
import static comp3350.breadtunes.application.Services.getSongPersistence;

public class LookUpSongs {

    List allSongs = getSongPersistence().getAll();
    List allAlbums = getAlbumPersistence().getAll();
    List allArtists = getArtistPersistence().getAll();

    public ArrayList<Object> searchSongs(String input) {
        ArrayList<Object>all = new ArrayList<>();
        for (int i = 0; i < allSongs.size() - 1; i++) {
            Song ss = (Song) allSongs.get(i);

            if (input.compareTo(ss.getName()) == 0) {
                all.add(ss);
            }
            }//for loop
        return all;
        }//searchSongs

        public ArrayList<Object> searchAlbums(String input){
            ArrayList<Object>all = new ArrayList<>();

            for (int i = 0; i < allAlbums.size() - 1; i++) {
                Album al = (Album) allAlbums.get(i);

                if (input.compareTo(al.getName()) == 0) {
                    all.add(al);

                }
            }//for loop
            return all;
        }//searchAlbums

        public ArrayList<Object> searchArtists(String input){
            ArrayList<Object>all = new ArrayList<>();

            for(int i = 0; i < allArtists.size() - 1; i++) {
                Artist ar = (Artist) allArtists.get(i);

                if (input.compareTo(ar.getName()) == 0){
                    all.add(ar);
                }
            }//for loop
            return all;
        }//searchArtists
    }// lookUpSongs

