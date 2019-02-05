package comp3350.breadtunes.business;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.List;

import comp3350.breadtunes.application.Services;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.stubs.SongPersistenceStub;

// Logic class that represents the state of the music player
public class MusicPlayerState {

    private boolean songPlaying; //is a song currently being played?
    private boolean songPaused;     //is a song currently paused?
    private MediaPlayer mediaPlayer; //media player object to play the songs
    private int pausedPosition;        //timestamp where a song is paused
    private Song currentSong;  //song object for the currently playing song (even if its paused or playing)
    private Services services;   //object to communicate with the song persistence
    private  SongPersistenceStub songPersistenceStub;

    public MusicPlayerState(){

        songPlaying = false;
        songPaused = false;
        currentSong = null;
        services = new Services();
        songPersistenceStub = (SongPersistenceStub) services.getSongPersistence(); //get interface for getting songs from persistanc
        mediaPlayer = null;
    }


    //plays a song, returns a string "succesful" or "failed to find resource" so that activity that calls can display toast message
    public String playSong(Song song, Context context, int resourceId){

        String response = "";

        if(resourceId == 0){
            response = "Failed to find resource";
        }else{
            mediaPlayer = MediaPlayer.create(context, resourceId); //song found, play!
            mediaPlayer.start();
            currentSong = song;     //update the logics STATE
            songPlaying = true;
            songPaused = true;
            response = "Playing "+song.getName();
        }
        return response;
    }

//
//    public String pauseSong(){
//
//
//    }

    //return the id for a song, the id allows the media player to play a song
    public String getRawSongName(Song song){
        String songRawName = song.getRawName();
        return songRawName;
    }

    //return the song object associated with the string song name
    public Song getSong(String songName){
        Song song = songPersistenceStub.getSong(songName);
        return song;
    }

    //return a list containing all songs
    public List<Song> getSongList(){
        return  songPersistenceStub.getAll();
    }

    //return a String array with all song names
    public String[] getSongNames(){
        List<Song> songList = songPersistenceStub.getAll();
        String[] songNames = new String[songList.size()];
        for(int i= 0; i<songList.size(); i++){
            songNames[i] = songList.get(i).getName();
        }
        return songNames;
    }


    //getters
    public boolean isSongPlaying() { return songPlaying; }
    public boolean isSongPaused() { return songPaused; }
    public MediaPlayer getMediaPlayer() { return mediaPlayer; }
    public int getPausedPosition() { return pausedPosition; }
    public Song getCurrentlyPlayingSong() { return currentSong;}



}//Music Player State
