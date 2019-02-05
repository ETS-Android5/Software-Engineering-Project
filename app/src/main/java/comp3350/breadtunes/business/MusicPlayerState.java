package comp3350.breadtunes.business;
import comp3350.breadtunes.objects.Song;


// Logic class that represents the state of the music player
public class MusicPlayerState {

    private boolean songPlaying; //is a song currently being played?
    private boolean songPaused;     //is a song currently paused?
    private Song currentSong;       // current song (playing or pausing)
    private int pausedPosition;        //timestamp where a song is paused



    public MusicPlayerState(){

        songPlaying = false;
        songPaused = false;
        currentSong = null;

    }


    //state getters
    public boolean isSongPlaying() { return songPlaying; }
    public void setPausedPosition(int pausedPosition) { this.pausedPosition = pausedPosition; }
    public boolean isSongPaused() { return songPaused; }
    public int getPausedPosition() { return pausedPosition; }
    public Song getCurrentlyPlayingSong() { return currentSong;}

    //state modifiers
    public void setIsSongPlaying(boolean songPlaying) { this.songPlaying = songPlaying; }
    public void setIsSongPaused(boolean songPaused) { this.songPaused = songPaused; }
    public void setCurrentSong(Song currentSong) { this.currentSong = currentSong; }




}//Music Player State
