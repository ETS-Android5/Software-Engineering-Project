package comp3350.breadtunes.business;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import comp3350.breadtunes.exception.InvalidSongIndex;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.services.ObservableService;


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

    private final String TAG = "State: ";
    private String currentPlayingSongName; //the name of the current song, must be saved and restored in main activity

    //queue
    private Deque<Song> queue;

    //to get random song
    Random randomNumberGen;

    public MusicPlayerState(SongPersistence songPersistence) {
        this.songPlaying = false;
        this.songPaused = false;
        this.currentSong = null;
        this.pausedPosition = 0;
        this.currentSongList = songPersistence.getAll();
        this.nextSong = null;
        this.previousSong = null;
        this.currentPlayingSongName = "";
        this.shuffleModeOn = false;
        this.repeatModeOn = false;
        this.randomNumberGen = new Random();
        this.parentalControlModeOn = false;
        this.queue = new ArrayDeque<>(100);
    }

    //state getters
    public String getCurrentlyPlayingSongName(){if(currentSong == null){return "";}else{return currentPlayingSongName;}}
    public boolean isSongPlaying() { return songPlaying; }
    public void setPausedPosition(int pausedPosition) { this.pausedPosition = pausedPosition; }
    public boolean isSongPaused() { return songPaused; }
    public int getPausedPosition() { return pausedPosition; }
    public boolean getShuffleMode(){return shuffleModeOn;}
    public boolean getRepeatMode(){return repeatModeOn;}
    public boolean getParentalControlModeOn(){return parentalControlModeOn;}
    public int getQueueSize(){return  queue.size();}


    //getters that return song objects
    public Song getCurrentlyPlayingSong() { return currentSong;}
    public List<Song> getCurrentSongList(){ return currentSongList;}
    public Song getNextSong(){return nextSong;}
    public Song getPreviousSong(){return previousSong;}


    //state modifiers
    public void setIsSongPlaying(boolean songPlaying) { this.songPlaying = songPlaying; }
    public void setIsSongPaused(boolean songPaused) { this.songPaused = songPaused; }
    public void setCurrentSongList(List<Song> newSongList){currentSongList = newSongList;}
    public void setCurrentSongPlayingName(String name){currentPlayingSongName = name;}

    public void turnParentalControlOn(boolean On){
        parentalControlModeOn = On;
        ObservableService.updateParentalModeStatus(On);
    }

    public void setRepeatMode(boolean mode){
        repeatModeOn = mode;
        ObservableService.updatePlayMode(getPlayMode());
    }

    public void setShuffleMode(boolean mode){
        shuffleModeOn = mode;
        updateNextSong();
        ObservableService.updatePlayMode(getPlayMode());
    }


    //update the song playing
    public void setCurrentSong(Song newCurrentSong) {

        if(queue!= null && queue.size() > 0 && newCurrentSong.getName().equals(queue.peek().getName())){
            Log.i(TAG, "song playing equals top of queue");
            queue.remove(); //remove the top of the queue
            Log.i(TAG, "removed top of queue");
        }

        currentSong = newCurrentSong; //when the song is changed, update the new next and previous
        currentPlayingSongName = currentSong.getName();

        updateNextSong();
        updatePreviousSong();

        ObservableService.updateCurrentSong(newCurrentSong); // Notify any listeners that the song has changed
    }

    //update the next song instance variable based on the current playing song
    public void updateNextSong() {

        if(queue != null && queue.size() > 0){

            Song queueTop = queue.peek();
            if(queueTop != null) {
                nextSong = queueTop;
                Log.i(TAG, "Next song is "+nextSong.getName());
            }
        }else{
            if (getShuffleMode()) {
                if (currentSongList != null && currentSong != null) {
                    int randomNextSongIndex = getRandomSongIndex();
                    nextSong = getCurrentSongList().get(randomNextSongIndex);
                }
            } else {

                //shuffle not on
                if (currentSongList != null && currentSong != null) { //make sure that the song is being played

                    int currentSongIndex = currentSongList.indexOf(currentSong);
                    if (currentSongIndex + 1 < currentSongList.size()) {
                        nextSong = currentSongList.get(++currentSongIndex);//make sure we do not go out of bounds
                        Log.i(TAG, "Next song is "+nextSong.getName());
                    } else {
                        nextSong = null; //no next song to play, we are the end of the list
                    }

                }
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

    //called to set next and previous when random mode is on
    private int getRandomSongIndex(){
        boolean differentFromCurrent = false;
        int randomIndex = -1;

        //generate random numbers until we get an index different from the song we are curently playing
        while(!differentFromCurrent){
           randomIndex = randomNumberGen.nextInt((getCurrentSongList().size()));
            //randomNum = rand.nextInt((max - min) + 1) + min;
            if(randomIndex != getCurrentSongList().indexOf(currentSong))
                differentFromCurrent = true;
        }

        try {

            if (randomIndex < 0 || randomIndex >= getCurrentSongList().size()){
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

    private String getShuffleStatus(){
        String status ="";
        if(getShuffleMode()){
            status = "Shuffle on ";
        }
        return status;
    }

    private String getRepeatStatus(){
        String status = "";
        if(getRepeatMode()){
            status = "- Repeat on";
        }
        return status;
    }

    //add a song to the top of the queue
    public void addSongToPlayNext(Song s){
        queue.addFirst(s);
        Log.i(TAG, "Added song "+s.getName()+" to play next");
        Log.i(TAG, "song at top of queue is "+queue.peek().getName());
        updateNextSong();
    }

    public void clearQueue(){
        queue.clear();
    }

    public void addToQueue(Song s){
        queue.add(s);
        Log.i(TAG, "Added song "+s.getName()+" to queue");
        Log.i(TAG, "song at top of queue is "+queue.peek().getName());
        updateNextSong();
    }


    //get the song names in the queue to populate the queue fragment
    public String[] getQueueSongNames(){
        String[] queueSongNames = new String[queue.size()];

        int i=0;
        for(Song song: queue){
            queueSongNames[i++] = song.getName();
        }

        return queueSongNames;
    }

}
