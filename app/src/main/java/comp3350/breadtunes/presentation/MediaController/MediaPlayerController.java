package comp3350.breadtunes.presentation.MediaController;
import android.content.Context;
import android.media.MediaPlayer;

import comp3350.breadtunes.business.LookUpSongs;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.HomeActivity;
import comp3350.breadtunes.services.ServiceGateway;

// Class that controls the playing, pausing, and playing next/previous
public class MediaPlayerController{


    public MediaPlayerController(){

    }

    //plays a song, returns a string "succesful" or "failed to find resource" so that activity that calls this metjod can display toast message
    public String playSong(Song song, Context context){
        String response;

        if (song.getSongUri() == null){
            response = "Could not find location of song";
        }
        else{
            if(ServiceGateway.getMediaManager() != null && ServiceGateway.getMediaManager().isPlaying()) {
                ServiceGateway.getMediaManager().stopPlayingSong();
            }

            ServiceGateway.getMediaManager().startPlayingSong(context, song.getSongUri());
            MusicPlayerState.getInstance().setCurrentSong(song);  //update the state of the music player!
            MusicPlayerState.getInstance().setIsSongPlaying(true);
            MusicPlayerState.getInstance().setIsSongPaused(false);
            ServiceGateway.getMediaManager().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {

                    //get reference to next from app state
                    Song nextSong;

                    if(MusicPlayerState.getInstance().getRepeatMode()){ //if repeat mode is on, the next song is the same song
                        nextSong = song;
                    }else{
                        nextSong = MusicPlayerState.getInstance().getNextSong(); //if repeat mode not on, next song is as usual
                    }

                    if(nextSong != null){
                        playSong(nextSong, context);
                    }
                }
            });

            response = "Playing "+song.getName();
        }

        return response;
    }



    //pause a song, and return a string so that main activity can display toast message with information
    public String pauseSong(){
        String response;
        //check first if a song is playing
        if(MusicPlayerState.getInstance().isSongPlaying()){
            if(ServiceGateway.getMediaManager() != null){ //make sure the media player object is not in idle state
                MusicPlayerState.getInstance().setPausedPosition(ServiceGateway.getMediaManager().getCurrentPosition()); // save the timestamp in the state so we can resume where we left off
                ServiceGateway.getMediaManager().pausePlayingSong();

                response = "paused song " + MusicPlayerState.getInstance().getCurrentlyPlayingSong().getName();
                MusicPlayerState.getInstance().setIsSongPaused(true); //update the state of the music player
                MusicPlayerState.getInstance().setIsSongPlaying(false);
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
        if(MusicPlayerState.getInstance().getCurrentlyPlayingSong() != null && MusicPlayerState.getInstance().getCurrentSongList()!= null){ //make sure there is a song playing


            Song nextSong = MusicPlayerState.getInstance().getNextSong();

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
        if(MusicPlayerState.getInstance().getCurrentlyPlayingSong() != null && MusicPlayerState.getInstance().getCurrentSongList()!= null){ //make sure there is a song playing
            Song previousSong = MusicPlayerState.getInstance().getPreviousSong();

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
            if(MusicPlayerState.getInstance().isSongPaused()) {
                ServiceGateway.getMediaManager().resumePlayingSong();

                //update app state!
                MusicPlayerState.getInstance().setIsSongPaused(false);
                MusicPlayerState.getInstance().setIsSongPlaying(true);

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
        if(MusicPlayerState.getInstance().isSongPlaying() || MusicPlayerState.getInstance().isSongPaused()){

            boolean shuffleOn = MusicPlayerState.getInstance().getShuffleMode();
            if(shuffleOn){
                MusicPlayerState.getInstance().setShuffleMode(false);
                response = "set shuffle mode to false";
            }else{
                MusicPlayerState.getInstance().setShuffleMode(true);
                response = "set shuffle mode to true";
            }
        }else{
            response = "No song playing or paused";
        }

        return response;
    }

    public String setRepeat(){
        String response;
        if(MusicPlayerState.getInstance().isSongPlaying() || MusicPlayerState.getInstance().isSongPaused()){

            boolean repeatOn = MusicPlayerState.getInstance().getRepeatMode();
            if(repeatOn){
                MusicPlayerState.getInstance().setRepeatMode(false);
                response = "set repeat mode to false";
            }else{
                MusicPlayerState.getInstance().setRepeatMode(true);
                response = "set repeat mode to true";
            }
        }else{
            response = "No song playing or paused";
        }
        return response;
    }


}//media player controller class
