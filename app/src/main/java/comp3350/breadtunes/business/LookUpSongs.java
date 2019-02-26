package comp3350.breadtunes.business;

import java.util.ArrayList;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.objects.Song;

public class LookUpSongs {
    Song[] allSongs;
    Album[] allAlbums;
    Artist[] allArtists;

    public LookUpSongs(Song[] songs, Album[] albums, Artist[] artists) {
        allSongs = songs;
        allAlbums = albums;
        allArtists = artists;
    }

    public Song[] searchSongs(String input) {
        ArrayList<Song> matchingSongs = new ArrayList<>();
        Song[] songList;
        for (int i = 0; i < allSongs.length; i++) {
            Song ss = allSongs[i];

            if (input.length() != 0 && ss.getName().toUpperCase().contains(input.toUpperCase())) {
                matchingSongs.add(ss);
            }
        }
        songList = new Song[matchingSongs.size()];
        for (int i = 0; i< songList.length; i++){
            songList[i] = matchingSongs.get(i);
        }
        return songList;
    }

    public Album[] searchAlbums(String input){
        ArrayList<Album> matchingAlbums = new ArrayList<>();
        Album[] AlbumList;
        for (int i = 0; i < allAlbums.length; i++) {
            Album al = allAlbums[i];

            if (input.length() != 0 && al.getName().toUpperCase().contains(input.toUpperCase())) {
                matchingAlbums.add(al);
            }
        }
        AlbumList = new Album[matchingAlbums.size()];
        for (int i = 0; i< AlbumList.length; i++){
            AlbumList[i] = matchingAlbums.get(i);
        }
        return AlbumList;
    }

    public Artist[] searchArtists(String input){
        ArrayList<Artist> matchingArtists = new ArrayList<>();
        Artist[] ArtistList;
        for(int i = 0; i < allArtists.length; i++) {
            Artist ar = allArtists[i];

            if (input.length() != 0 && ar.getName().toUpperCase().contains(input.toUpperCase())){
                matchingArtists.add(ar);
            }
        }
        ArtistList = new Artist[matchingArtists.size()];
        for (int i = 0; i< ArtistList.length; i++){
            ArtistList[i] = matchingArtists.get(i);
        }
        return ArtistList;
    }

    public ArrayList<Object> toList(Object[] input){
        ArrayList<Object> out = new ArrayList<>();
        for(int i=0; i<input.length;i++){
            out.add(input[i]);
        }
        return out;
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
