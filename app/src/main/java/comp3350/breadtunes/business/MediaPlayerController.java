package comp3350.breadtunes.business;
import android.content.Context;
import android.media.MediaPlayer;
import comp3350.breadtunes.objects.Song;

// Class that controls the playing, pausing, and playing next/previous
public class MediaPlayerController implements MediaPlayer.OnCompletionListener {


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

        String response;
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
        String response;
        //check first if a song is playing
        if(appState.isSongPlaying()){
            if(mediaPlayer != null){ //make sure the media player object is not in idle state
                appState.setPausedPosition(mediaPlayer.getCurrentPosition()); // save the timestamp in the state so we can resume where we left off
                mediaPlayer.pause();
                response = "paused song "+appState.getCurrentlyPlayingSong().getName();
                appState.setIsSongPaused(true); //update the state of the music player
                appState.setIsSongPlaying(false);
            }else{
                response = "Can not pause, no song playing";
            }
        }else{
            response = "Can not pause, no song playing";
        }
        return response;
    }


    // WHAT TO DO WHEN THE CURRENT SONG PLAYING FINISHES
    //https://developer.android.com/reference/android/media/MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        //get reference to next from app state
    }


    // play next song button
    public String playNextSong(Context context){
        String response;
        if(appState.getCurrentlyPlayingSong() != null && appState.getCurrentSongList()!= null){ //make sure there is a song playing
            Song nextSong = appState.getNextSong();

            if(nextSong != null) {
                int nextSongId = context.getResources().getIdentifier(nextSong.getRawName(), "raw", context.getPackageName());    //get the resource pointer
                playSong(nextSong, nextSongId); //play the new song
                response = "playing " + nextSong.getName();
            }else{
                response = "no next song";
            }
        }else{
            response = "no song currently playing";
        }
        return response;
    }

    //play previous song button
    public String playPreviousSong(Context context){
        String response;
        if(appState.getCurrentlyPlayingSong() != null && appState.getCurrentSongList()!= null){ //make sure there is a song playing
            Song previousSong = appState.getPreviousSong();

            if(previousSong != null) {
                int nextSongId = context.getResources().getIdentifier(previousSong.getRawName(), "raw", context.getPackageName());    //get the resource pointer
                playSong(previousSong, nextSongId); //play the new song
                response = "playing " + previousSong.getName();
            }else{
                response = "no previous song";
            }
        }else{
            response = "no song currently playing";
        }
        return response;

    }



    // resume the playing of a song
    public String resumeSong(int resourceId){
     String response;
        try{
                if(appState.isSongPaused()) {
                    mediaPlayer = MediaPlayer.create(context, resourceId);
                    mediaPlayer.seekTo(appState.getPausedPosition());   //go to the saved position in which the song was paused
                    mediaPlayer.start();    //resume

                    //update app state!
                    appState.setIsSongPaused(false);
                    appState.setIsSongPlaying(true);

                    response = "Response succesful";
                }else{
                    response = "Can not resume , no song is paused";
                }
        }catch(Exception e){
            response = e.toString();
        }
        return response;
    }


    //called in OnDestroy() to free up the media player, just housekeeping
    public void releaseMediaPlayer(){
        mediaPlayer.release();
    }


}//media player controller class
