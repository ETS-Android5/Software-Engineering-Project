package comp3350.breadtunes.presentation.MediaController;

import android.content.Context;
import android.media.MediaPlayer;

import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.services.ServiceGateway;

// Class that controls the playing, pausing, and playing next/previous
public class MediaPlayerController{

    MusicPlayerState musicPlayerState;

    public MediaPlayerController(MusicPlayerState musicPlayerState){
        this.musicPlayerState = musicPlayerState;
    }

    //plays a song, returns a string "succesful" or "failed to find resource" so that activity that calls this metjod can display toast message
    public String playSong(Song song, Context context){
        String response;

        if (song.getSongUri() == null){
            response = "Could not find location of song";
        }
        else {
            if (musicPlayerState.getParentalControlModeOn() && ServiceGateway.getSongFlagger().songIsFlagged(song)) {
                response = "Parental control does not allow this song to be played";
            } else {

                if (ServiceGateway.getMediaManager() != null && ServiceGateway.getMediaManager().isPlaying()) {
                    ServiceGateway.getMediaManager().stopPlayingSong();
                }

                ServiceGateway.getMediaManager().startPlayingSong(context, song.getSongUri());
                musicPlayerState.setCurrentSong(song);  //update the state of the music player!
                musicPlayerState.setIsSongPlaying(true);
                musicPlayerState.setIsSongPaused(false);
                ServiceGateway.getMediaManager().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        //get reference to next from app state
                        Song nextSong;

                        if (musicPlayerState.getRepeatMode()) { //if repeat mode is on, the next song is the same song
                            nextSong = song;
                        } else {
                            nextSong = musicPlayerState.getNextSong(); //if repeat mode not on, next song is as usual
                        }

                        if (nextSong != null) {
                            playSong(nextSong, context);
                        }
                    }
                });

                response = "Playing " + song.getName();
            }
        }
        return response;
    }



    //pause a song, and return a string so that main activity can display toast message with information
    public String pauseSong(){
        String response;
        //check first if a song is playing
        if(musicPlayerState.isSongPlaying()){
            if(ServiceGateway.getMediaManager() != null){ //make sure the media player object is not in idle state
                musicPlayerState.setPausedPosition(ServiceGateway.getMediaManager().getCurrentPosition()); // save the timestamp in the state so we can resume where we left off
                ServiceGateway.getMediaManager().pausePlayingSong();

                response = "paused song " + musicPlayerState.getCurrentlyPlayingSong().getName();
                musicPlayerState.setIsSongPaused(true); //update the state of the music player
                musicPlayerState.setIsSongPlaying(false);
            }else{
                response = "Can not pause, app state is null";
            }
        }else{
            response = "Can not pause, the song is already paused";
        }
        return response;
    }


    // WHAT TO DO WHEN THE CURRENT SONG PLAYING FINISHES
    //https://developer.android.com/reference/android/media/MediaPlayer.OnCompletionListener

    // play next song button
    public String playNextSong(Context context) {
        String response;
        if(musicPlayerState.getCurrentlyPlayingSong() != null && musicPlayerState.getCurrentSongList()!= null){ //make sure there is a song playing


            Song nextSong = musicPlayerState.getNextSong();

            if(nextSong != null) {
                playSong(nextSong, context);
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
        if(musicPlayerState.getCurrentlyPlayingSong() != null && musicPlayerState.getCurrentSongList()!= null){ //make sure there is a song playing
            Song previousSong = musicPlayerState.getPreviousSong();

            if(previousSong != null) {
                playSong(previousSong, context); //play the new song
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
    public String resumeSong(){
     String response;
        try{
            if(musicPlayerState.isSongPaused()) {
                ServiceGateway.getMediaManager().resumePlayingSong();

                //update app state!
                musicPlayerState.setIsSongPaused(false);
                musicPlayerState.setIsSongPlaying(true);

                response = "resuming song";
            }else{
                response = "Can not resume , no song is paused";
            }
        }catch(Exception e){
            response = e.toString();
        }
        return response;
    }

    public String setShuffle(){
        String response;
        if(musicPlayerState.isSongPlaying() || musicPlayerState.isSongPaused()){

            boolean shuffleOn = musicPlayerState.getShuffleMode();
            if(shuffleOn){
                musicPlayerState.setShuffleMode(false);
                response = "set shuffle mode to false";
            }else{
                musicPlayerState.setShuffleMode(true);
                response = "set shuffle mode to true";
            }
        }else{
            response = "No song playing or paused";
        }

        return response;
    }

    public String setRepeat(){
        String response;
        if(musicPlayerState.isSongPlaying() || musicPlayerState.isSongPaused()){

            boolean repeatOn = musicPlayerState.getRepeatMode();
            if(repeatOn){
                musicPlayerState.setRepeatMode(false);
                response = "set repeat mode to false";
            }else{
                musicPlayerState.setRepeatMode(true);
                response = "set repeat mode to true";
            }
        }else{
            response = "No song playing or paused";
        }
        return response;
    }


}//media player controller class
