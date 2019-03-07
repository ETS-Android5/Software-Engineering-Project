package comp3350.breadtunes.business;
import java.util.List;
import java.util.Observer;

import comp3350.breadtunes.business.observables.SongObservable;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.services.ServiceGateway;


// Logic class that represents the state of the music player SINGLETON PATTERN
public class MusicPlayerState {


    private boolean songPlaying; //is a song currently being played?
    private boolean songPaused;     //is a song currently paused?
    private Song currentSong;       // current song (playing or pausing)
    private int pausedPosition;        //timestamp where a song is paused
    private List<Song> currentSongList; //the song list that the app is playing from at the moment
    private Song nextSong;
    private Song previousSong;

    private SongObservable songObservable;

    private static MusicPlayerState musicPlayerState;

    //march 5
    private String currentPlayingSongName; //the name of the current song, must be saved and restored in main activity

    public synchronized static MusicPlayerState getInstance(){

        if(musicPlayerState == null){
            musicPlayerState = new MusicPlayerState();
            musicPlayerState.songPlaying = false;
            musicPlayerState.songPaused = false;
            musicPlayerState.currentSong = null;
            musicPlayerState.pausedPosition = 0;
            musicPlayerState.currentSongList = ServiceGateway.getSongPersistence().getAll();
            musicPlayerState. nextSong = null;
            musicPlayerState.previousSong = null;
            musicPlayerState.songObservable = new SongObservable();
            musicPlayerState.currentPlayingSongName = "";

        }

        return musicPlayerState;
    }


    //state getters
    public String getCurrentlyPlayingSongName(){if(musicPlayerState.currentSong == null){return "";}else{return musicPlayerState.currentPlayingSongName;}}
    public boolean isSongPlaying() { return musicPlayerState.songPlaying; }
    public void setPausedPosition(int pausedPosition) { musicPlayerState.pausedPosition = pausedPosition; }
    public boolean isSongPaused() { return musicPlayerState.songPaused; }
    public int getPausedPosition() { return musicPlayerState.pausedPosition; }


    //getters that return song objects
    public Song getCurrentlyPlayingSong() { return musicPlayerState.currentSong;}
    public List<Song> getCurrentSongList(){ return musicPlayerState.currentSongList;}
    public Song getNextSong(){return musicPlayerState.nextSong;}
    public Song getPreviousSong(){return musicPlayerState.previousSong;}


    //state modifiers
    public void setIsSongPlaying(boolean songPlaying) { musicPlayerState.songPlaying = songPlaying; }
    public void setIsSongPaused(boolean songPaused) { musicPlayerState.songPaused = songPaused; }
    public void setCurrentSongList(List<Song> newSongList){musicPlayerState.currentSongList = newSongList;}
    public void setCurrentSongPlayingName(String name){musicPlayerState.currentPlayingSongName = name;}

    //update the song playing
    public void setCurrentSong(Song newCurrentSong) {
        musicPlayerState.currentSong = newCurrentSong; //when the song is changed, update the new next and previous
        musicPlayerState.currentPlayingSongName = currentSong.getName();
        updateNextSong();
        updatePreviousSong();

        musicPlayerState.songObservable.setSong(newCurrentSong); // Notify any listeners that the song has changed
    }

    //update the next song instance variable based on the current playing song
    public void updateNextSong(){

        if(musicPlayerState.currentSongList != null && musicPlayerState.currentSong != null) { //make sure that the song is being played

            int currentSongIndex = musicPlayerState.currentSongList.indexOf(currentSong);
            if (currentSongIndex + 1 < musicPlayerState.currentSongList.size()) {
                musicPlayerState.nextSong = musicPlayerState.currentSongList.get(++currentSongIndex);//make sure we do not go out of bounds
            }else{
                musicPlayerState.nextSong = null; //no next song to play, we are the end of the list
            }
        }
    }

    //update the previous song instance variable based on the current playing song
    public void updatePreviousSong(){

        if(musicPlayerState.currentSongList != null && musicPlayerState.currentSong != null) { //make sure that the song is being played

            int currentSongIndex = currentSongList.indexOf(musicPlayerState.currentSong);
            if (currentSongIndex -1 > -1) {
                musicPlayerState.previousSong = musicPlayerState.currentSongList.get(--currentSongIndex); //make sure we do not go out of bounds if the current song is the first song in the list
            }else{
                musicPlayerState.previousSong = null; //no previous song , we are the start of the list
            }
        }

    }

    public void subscribeToSongChange(Observer observer) {
        musicPlayerState.songObservable.addObserver(observer);
    }

    public String getMusicPlayerState(){
        String state;

        if(musicPlayerState.currentSong!=null){
            state = "Current song not null song is "+musicPlayerState.currentSong.getName()+" song paused: "+musicPlayerState.songPaused+" song playing "+musicPlayerState.songPlaying+"variable currentSongname is "+musicPlayerState.currentPlayingSongName;
        }else{
            state = "Current song is null, current song name variable: "+musicPlayerState.currentPlayingSongName+" song paused: "+musicPlayerState.songPaused+" song playing: "+musicPlayerState.songPlaying;
        }

        return state;
    }
}
