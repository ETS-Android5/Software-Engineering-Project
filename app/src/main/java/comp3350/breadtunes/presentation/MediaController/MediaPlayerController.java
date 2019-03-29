package comp3350.breadtunes.presentation.MediaController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.services.ServiceGateway;

import static android.content.ContentValues.TAG;

// Class that controls the playing, pausing, and playing next/previous
public class MediaPlayerController{

    private static MusicPlayerState musicPlayerState;
    private static MediaPlayerController sInstance;
    private Context mContext;
    private boolean mAudioFocusGranted = false;
    private boolean mAudioIsPlaying = false;
    private MediaPlayer mPlayer;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private BroadcastReceiver mIntentReceiver;
    private boolean mReceiverRegistered = false;
    Song mSong; //Instance of the song currently playing
    private static final String CMD_NAME = "command";
    private static final String CMD_PAUSE = "pause";
    private static final String CMD_STOP = "pause";
    private static final String CMD_PLAY = "play";

    // Jellybean
    private static String SERVICE_CMD = "com.sec.android.app.music.musicservicecommand";
    private static String PAUSE_SERVICE_CMD = "com.sec.android.app.music.musicservicecommand.pause";
    private static String PLAY_SERVICE_CMD = "com.sec.android.app.music.musicservicecommand.play";

    // Honeycomb
    {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            SERVICE_CMD = "com.android.music.musicservicecommand";
            PAUSE_SERVICE_CMD = "com.android.music.musicservicecommand.pause";
            PLAY_SERVICE_CMD = "com.android.music.musicservicecommand.play";
        }
    };

    public MediaPlayerController(MusicPlayerState musicPlayerState){
        this.musicPlayerState = musicPlayerState;
    }

    public static MediaPlayerController getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MediaPlayerController(context);
            musicPlayerState = ServiceGateway.getMusicPlayerState();
        }
        return sInstance;
    }

    private MediaPlayerController(Context context) {
        mContext = context;

        mOnAudioFocusChangeListener = focusChange -> {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.i(TAG, "AUDIOFOCUS_GAIN");
                    playSong(mSong,mContext);
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    Log.i(TAG, "AUDIOFOCUS_GAIN_TRANSIENT");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    Log.i(TAG, "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK");
                    pauseSong();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.e(TAG, "AUDIOFOCUS_LOSS");
                    pauseSong();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                    pauseSong();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;
                case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                    Log.e(TAG, "AUDIOFOCUS_REQUEST_FAILED");
                    break;
                default:
                    //nothing to do
            }
        };
    }

    //plays a song, returns a string "succesful" or "failed to find resource" so that activity that calls this metjod can display toast message
    public String playSong(Song song, Context context){
        String response  ="";
        //mContext = context;
        if (!mAudioIsPlaying) {
            if (song.getSongUri() == null) {
                response = "Could not find location of song";
            } else {
                if (musicPlayerState.getParentalControlModeOn() && ServiceGateway.getSongFlagger().songIsFlagged(song)) {
                    response = "Parental control does not allow this song to be played";
                } else {

                    if (ServiceGateway.getMediaManager() != null && ServiceGateway.getMediaManager().isPlaying()) {
                        ServiceGateway.getMediaManager().stopPlayingSong();
                    }

                    if (!mAudioFocusGranted && requestAudioFocus()) {
                        // 2. Kill off any other play back sources
                        System.out.println("to be killed");
                        forceMusicStop();
                        // 3. Register broadcast receiver for player intents
                        setupBroadcastReceiver();
                    }

                        ServiceGateway.getMediaManager().startPlayingSong(mContext, song.getSongUri());
                        mSong = song;
                        mAudioIsPlaying = true;
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
                                    playSong(nextSong, mContext);
                                }
                            }
                        });

                        response = "Playing " + song.getName();
                }
            }
        }
        return response;
    }



    //pause a song, and return a string so that main activity can display toast message with information
    public String pauseSong(){
        String response = "";
        //check first if a song is playing
        if (mAudioFocusGranted && mAudioIsPlaying) {
            if (musicPlayerState.isSongPlaying()) {
                if (ServiceGateway.getMediaManager() != null) { //make sure the media player object is not in idle state
                    musicPlayerState.setPausedPosition(ServiceGateway.getMediaManager().getCurrentPosition()); // save the timestamp in the state so we can resume where we left off
                    ServiceGateway.getMediaManager().pausePlayingSong();

                    response = "paused song " + musicPlayerState.getCurrentlyPlayingSong().getName();
                    musicPlayerState.setIsSongPaused(true); //update the state of the music player
                    musicPlayerState.setIsSongPlaying(false);
                    mAudioIsPlaying = false;

                } else {
                    response = "Can not pause, app state is null";
                }
            } else {
                response = "Can not pause, the song is already paused";
            }
        }
        return response;
    }


    // WHAT TO DO WHEN THE CURRENT SONG PLAYING FINISHES
    //https://developer.android.com/reference/android/media/MediaPlayer.OnCompletionListener

    // play next song button
    public String playNextSong(Context context) {
        String response;
        //mContext = context;
        if(musicPlayerState.getCurrentlyPlayingSong() != null && musicPlayerState.getCurrentSongList()!= null){ //make sure there is a song playing


            Song nextSong = musicPlayerState.getNextSong();

            if(nextSong != null) {
                playSong(nextSong, mContext);
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
                playSong(previousSong, mContext); //play the new song
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

    private boolean requestAudioFocus() {
        if (!mAudioFocusGranted) {
            AudioManager am = (AudioManager) mContext
                    .getSystemService(Context.AUDIO_SERVICE);
            // Request audio focus for play back
            int result = am.requestAudioFocus(mOnAudioFocusChangeListener,
                    // Use the music stream.
                    AudioManager.STREAM_MUSIC,
                    // Request permanent focus.
                    AudioManager.AUDIOFOCUS_GAIN);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mAudioFocusGranted = true;
            } else {
                // FAILED
                Log.e(TAG,">>>>>>>>>>>>> FAILED TO GET AUDIO FOCUS <<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }
        return mAudioFocusGranted;
    }

   /* private void abandonAudioFocus() {
        AudioManager am = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        int result = am.abandonAudioFocus(mOnAudioFocusChangeListener);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mAudioFocusGranted = false;
        } else {
            // FAILED
            Log.e(TAG,
                    ">>>>>>>>>>>>> FAILED TO ABANDON AUDIO FOCUS <<<<<<<<<<<<<<<<<<<<<<<<");
        }
        mOnAudioFocusChangeListener = null;
    }*/

    private void setupBroadcastReceiver() {
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                String cmd = intent.getStringExtra(CMD_NAME);
                Log.i(TAG, "mIntentReceiver.onReceive " + action + " / " + cmd);

                if (PAUSE_SERVICE_CMD.equals(action)
                        || (SERVICE_CMD.equals(action) && CMD_PAUSE.equals(cmd))) {
                    playSong(mSong,context);
                }

                if (PLAY_SERVICE_CMD.equals(action)
                        || (SERVICE_CMD.equals(action) && CMD_PLAY.equals(cmd))) {
                    pauseSong();
                }
            }
        };

        // Do the right thing when something else tries to play
        if (!mReceiverRegistered) {
            IntentFilter commandFilter = new IntentFilter();
            commandFilter.addAction(SERVICE_CMD);
            commandFilter.addAction(PAUSE_SERVICE_CMD);
            commandFilter.addAction(PLAY_SERVICE_CMD);
            mContext.registerReceiver(mIntentReceiver, commandFilter);
            mReceiverRegistered = true;
        }
    }

    private void forceMusicStop() {
        AudioManager am = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        if (am.isMusicActive()) {
            System.out.println("forcemusic");
            Intent intentToStop = new Intent(SERVICE_CMD);
            intentToStop.putExtra(CMD_NAME, CMD_STOP);
            mContext.sendBroadcast(intentToStop);
       }
    }

}//media player controller class
