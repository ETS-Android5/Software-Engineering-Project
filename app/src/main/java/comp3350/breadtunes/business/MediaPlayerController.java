package comp3350.breadtunes.business;
import android.content.Context;
import android.media.MediaPlayer;
import comp3350.breadtunes.objects.Song;

// Class that controls the playing, pausing, and playing next/previous
public class MediaPlayerController {


    private MediaPlayer mediaPlayer;       // media player object to actually play the files
    private Context context;            //the context for the activity from which the song is being played
    private MusicPlayerState appState; //the state of the player, before playing a file, this class
                                       // will check information such as "is a song currently playing?"
                                        // "is a song paused?" etc.

    public MediaPlayerController(Context context, MusicPlayerState appState){
        mediaPlayer = null;
        this.context = context;
        this.appState = appState;
    }

    //plays a song, returns a string "succesful" or "failed to find resource" so that activity that calls this metjod can display toast message
    public String playSong(Song song, int resourceId){

        String response = "";
        if(resourceId == 0){
            response = "Failed to find resource";
        }else{
            if(mediaPlayer != null && mediaPlayer.isPlaying()) //pause a song, if there is one currently playing
                mediaPlayer.stop();

            mediaPlayer = MediaPlayer.create(context, resourceId); //song found, play!
            mediaPlayer.start();

            appState.setCurrentSong(song);  //update the state of the music player!
            appState.setIsSongPlaying(true);
            appState.setIsSongPaused(false);
            response = "Playing "+song.getName();
        }
        return response;
    }



    //pause a song, and return a string so that main activity can display toast message with information
    public String pauseSong(){
        String response = "";
        //check first if a song is playing
        if(appState.isSongPlaying()){
            if(mediaPlayer != null){ //make sure the object is not in idle state
                mediaPlayer.pause();
                response = "paused song "+appState.getCurrentlyPlayingSong().getName();
                appState.setIsSongPaused(true); //update the state of the music player
                appState.setIsSongPlaying(false);


                //SAVE TIMESTAMP WHERE SONG WAS PAUSED
                //appState.setPausedPosition()
            }else{
                response = "Can not pause, no song playing";
            }
        }else{
            response = "Can not pause, no song playing";
        }
        return response;
    }


    public String resumeSong(){
        String response = "";
        return response;
    }


    //called in OnDestroy() to free up the media player
    public void releaseMediaPlayer(){
        mediaPlayer.release();
    }

}//media player controller class
