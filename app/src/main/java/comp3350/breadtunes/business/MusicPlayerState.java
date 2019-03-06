package comp3350.breadtunes.business;
import java.util.List;
import java.util.Observer;

import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;


// Logic class that represents the state of the music player
public class MusicPlayerState {

    private boolean songPlaying; //is a song currently being played?
    private boolean songPaused;     //is a song currently paused?
    private Song currentSong;       // current song (playing or pausing)
    private int pausedPosition;        //timestamp where a song is paused
    private List<Song> currentSongList; //the song list that the app is playing from at the moment
    private Song nextSong;
    private Song previousSong;

    private SongObservable songObservable;

    //march 5
    private String currentPlayingSongName; //the name of the current song, must be saved and restored in main activity

    public MusicPlayerState(List<Song> initialSongList){

        songPlaying = false;
        songPaused = false;
        currentSong = null;
        pausedPosition = 0;
        currentSongList = initialSongList;
        nextSong = null;
        previousSong = null;
        songObservable = new SongObservable();
        currentPlayingSongName = "";
    }


    //state getters
    public String getCurrentlyPlayingSongName(){if(currentSong == null){return "";}else{return currentPlayingSongName;}}
    public boolean isSongPlaying() { return songPlaying; }
    public void setPausedPosition(int pausedPosition) { this.pausedPosition = pausedPosition; }
    public boolean isSongPaused() { return songPaused; }
    public int getPausedPosition() { return pausedPosition; }


    //getters that return song objects
    public Song getCurrentlyPlayingSong() { return currentSong;}
    public List<Song> getCurrentSongList(){ return currentSongList;}
    public Song getNextSong(){return nextSong;}
    public Song getPreviousSong(){return previousSong;}


    //state modifiers
    public void setIsSongPlaying(boolean songPlaying) { this.songPlaying = songPlaying; }
    public void setIsSongPaused(boolean songPaused) { this.songPaused = songPaused; }
    public void setCurrentSongList(List<Song> newSongList){currentSongList = newSongList;}
    public void setCurrentSongPlayingName(String name){this.currentPlayingSongName = name;}

    //update the song playing
    public void setCurrentSong(Song newCurrentSong) {
        this.currentSong = newCurrentSong; //when the song is changed, update the new next and previous
        currentPlayingSongName = currentSong.getName();
        updateNextSong();
        updatePreviousSong();

        songObservable.setSong(newCurrentSong); // Notify any listeners that the song has changed
    }

    //update the next song instance variable based on the current playing song
    public void updateNextSong(){

        if(currentSongList != null && currentSong != null) { //make sure that the song is being played

            int currentSongIndex = currentSongList.indexOf(currentSong);
            if (currentSongIndex + 1 < currentSongList.size()) {
                nextSong = currentSongList.get(++currentSongIndex);//make sure we do not go out of bounds
            }else{
                nextSong = null; //no next song to play, we are the end of the list
            }
        }
    }

    //update the previous song instance variable based on the current playing song
    public void updatePreviousSong(){

        if(currentSongList != null && currentSong != null) { //make sure that the song is being played

            int currentSongIndex = currentSongList.indexOf(currentSong);
            if (currentSongIndex -1 > -1) {
                previousSong = currentSongList.get(--currentSongIndex); //make sure we do not go out of bounds if the current song is the first song in the list
            }else{
                previousSong = null; //no previous song , we are the start of the list
            }
        }

    }

    public void subscribeToSongChange(Observer observer) {
        songObservable.addObserver(observer);
    }
}
