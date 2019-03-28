package comp3350.breadtunes.business;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import comp3350.breadtunes.exception.InvalidSongIndex;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.services.ObservableService;
import comp3350.breadtunes.services.ServiceGateway;


// Logic class that represents the state of the music player SINGLETON PATTERN
public class MusicPlayerState {

    private boolean songPlaying; //is a song currently being played?
    private boolean songPaused;     //is a song currently paused?
    private Song currentSong;       // current song (playing or pausing)
    private int pausedPosition;        //timestamp where a song is paused
    private List<Song> currentSongList; //the song list that the app is playing from at the moment
    private Song nextSong;                 //whats the next song after this one?
    private Song previousSong;              //whats the previous song?
    private boolean shuffleModeOn;         //is the player playing songs randomly?
    private boolean repeatModeOn;           //is the repeat mode on
    private boolean parentalControlModeOn;   // is the parental control mode on?

    private static MusicPlayerState musicPlayerState;
    private final String TAG = "State: ";
    private String currentPlayingSongName; //the name of the current song, must be saved and restored in main activity

    //queue
    private Deque<Song> queue;

    //to get random song
    Random randomNumberGen;

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
            musicPlayerState.currentPlayingSongName = "";
            musicPlayerState.shuffleModeOn = false;
            musicPlayerState.repeatModeOn = false;
            musicPlayerState.randomNumberGen = new Random();
            musicPlayerState.parentalControlModeOn = false;
            musicPlayerState.queue = new ArrayDeque<>(100);
        }

        return musicPlayerState;
    }


    //state getters
    public String getCurrentlyPlayingSongName(){if(musicPlayerState.currentSong == null){return "";}else{return musicPlayerState.currentPlayingSongName;}}
    public boolean isSongPlaying() { return musicPlayerState.songPlaying; }
    public void setPausedPosition(int pausedPosition) { musicPlayerState.pausedPosition = pausedPosition; }
    public boolean isSongPaused() { return musicPlayerState.songPaused; }
    public int getPausedPosition() { return musicPlayerState.pausedPosition; }
    public boolean getShuffleMode(){return musicPlayerState.shuffleModeOn;}
    public boolean getRepeatMode(){return musicPlayerState.repeatModeOn;}
    public boolean getParentalControlModeOn(){return musicPlayerState.parentalControlModeOn;}
    public int getQueueSize(){return  musicPlayerState.queue.size();}


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

    public void turnParentalControlOn(boolean On){
        musicPlayerState.parentalControlModeOn = On;
        ObservableService.updateParentalModeStatus(On);
    }

    public void setRepeatMode(boolean mode){
        musicPlayerState.repeatModeOn = mode;
        ObservableService.updatePlayMode(musicPlayerState.getPlayMode());
    }

    public void setShuffleMode(boolean mode){
        musicPlayerState.shuffleModeOn = mode;
        musicPlayerState.updateNextSong();
        ObservableService.updatePlayMode(musicPlayerState.getPlayMode());
    }


    //update the song playing
    public void setCurrentSong(Song newCurrentSong) {

        if(musicPlayerState.queue!= null && musicPlayerState.queue.size() > 0 && newCurrentSong.getName().equals(musicPlayerState.queue.peek().getName())){
            Log.i(TAG, "song playing equals top of queue");
            musicPlayerState.queue.remove(); //remove the top of the queue
            Log.i(TAG, "removed top of queue");
        }

        musicPlayerState.currentSong = newCurrentSong; //when the song is changed, update the new next and previous
        musicPlayerState.currentPlayingSongName = musicPlayerState.currentSong.getName();

        updateNextSong();
        updatePreviousSong();

        ObservableService.updateCurrentSong(newCurrentSong); // Notify any listeners that the song has changed
    }

    //update the next song instance variable based on the current playing song
    public void updateNextSong() {

        if(musicPlayerState.queue != null && musicPlayerState.queue.size() > 0){

            Song queueTop = musicPlayerState.queue.peek();
            if(queueTop != null) {
                musicPlayerState.nextSong = queueTop;
                Log.i(TAG, "Next song is "+musicPlayerState.nextSong.getName());
            }
        }else{
            if (musicPlayerState.getShuffleMode()) {
                if (musicPlayerState.currentSongList != null && musicPlayerState.currentSong != null) {
                    int randomNextSongIndex = getRandomSongIndex();
                    musicPlayerState.nextSong = musicPlayerState.getCurrentSongList().get(randomNextSongIndex);
                }
            } else {

                //shuffle not on
                if (musicPlayerState.currentSongList != null && musicPlayerState.currentSong != null) { //make sure that the song is being played

                    int currentSongIndex = musicPlayerState.currentSongList.indexOf(currentSong);
                    if (currentSongIndex + 1 < musicPlayerState.currentSongList.size()) {
                        musicPlayerState.nextSong = musicPlayerState.currentSongList.get(++currentSongIndex);//make sure we do not go out of bounds
                        Log.i(TAG, "Next song is "+musicPlayerState.nextSong.getName());
                    } else {
                        musicPlayerState.nextSong = null; //no next song to play, we are the end of the list
                    }

                }
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

    //called to set next and previous when random mode is on
    private int getRandomSongIndex(){
        boolean differentFromCurrent = false;
        int randomIndex = -1;

        //generate random numbers until we get an index different from the song we are curently playing
        while(!differentFromCurrent){
           randomIndex = musicPlayerState.randomNumberGen.nextInt((musicPlayerState.getCurrentSongList().size()));
            //randomNum = rand.nextInt((max - min) + 1) + min;
            if(randomIndex != musicPlayerState.getCurrentSongList().indexOf(musicPlayerState.currentSong))
                differentFromCurrent = true;
        }

        try {

            if (randomIndex < 0 || randomIndex >= musicPlayerState.getCurrentSongList().size()){
                randomIndex = 0;
                throw new InvalidSongIndex("Invalid song index in getRandomSongIndex( )  in Music Player State");
            }

        }catch(InvalidSongIndex e){
            e.printStackTrace();
        }

        return randomIndex;
    }

    public String getPlayMode(){
        String playMode = getShuffleStatus()+getRepeatStatus();
        String shuffleStatus = getShuffleStatus();
        String repeatStatus = getRepeatStatus();

        if(shuffleStatus.equals("") && repeatStatus.equals(""))
            playMode = "";
        return playMode;
    }

    public String getParentalControlStatus(){
        if(parentalControlModeOn){
            return "Parental Control Mode On";
        }else{
            return "Parental Control Mode Off";
        }
    }
    public MusicPlayerState getMusicPlayerStateInstance(){return this.musicPlayerState;}

    public MusicPlayerState(){}

    public MusicPlayerState(List<Song> songList){

        musicPlayerState = new MusicPlayerState();
        musicPlayerState.songPlaying = false;
        musicPlayerState.songPaused = false;
        musicPlayerState.currentSong = null;
        musicPlayerState.pausedPosition = 0;
        musicPlayerState.currentSongList = songList;
        musicPlayerState. nextSong = null;
        musicPlayerState.previousSong = null;
        musicPlayerState.currentPlayingSongName = "";
        musicPlayerState.shuffleModeOn = false;
        musicPlayerState.repeatModeOn = false;
        musicPlayerState.randomNumberGen = new Random();
    }

    private String getShuffleStatus(){
        String status ="";
        if(musicPlayerState.getShuffleMode()){
            status = "Shuffle on ";
        }
        return status;
    }

    private String getRepeatStatus(){
        String status = "";
        if(musicPlayerState.getRepeatMode()){
            status = "- Repeat on";
        }
        return status;
    }

    //add a song to the top of the queue
    public void addSongToPlayNext(Song s){
        musicPlayerState.queue.addFirst(s);
        Log.i(TAG, "Added song "+s.getName()+" to play next");
        Log.i(TAG, "song at top of queue is "+musicPlayerState.queue.peek().getName());
        musicPlayerState.updateNextSong();
    }

    public void clearQueue(){
        musicPlayerState.queue.clear();
    }

    public void addToQueue(Song s){
        queue.add(s);
        Log.i(TAG, "Added song "+s.getName()+" to queue");
        Log.i(TAG, "song at top of queue is "+musicPlayerState.queue.peek().getName());
        musicPlayerState.updateNextSong();
    }


    //get the song names in the queue to populate the queue fragment
    public String[] getQueueSongNames(){
        String[] queueSongNames = new String[musicPlayerState.queue.size()];

        int i=0;
        for(Song song: musicPlayerState.queue){
            queueSongNames[i++] = song.getName();
        }

        return queueSongNames;
    }

}
