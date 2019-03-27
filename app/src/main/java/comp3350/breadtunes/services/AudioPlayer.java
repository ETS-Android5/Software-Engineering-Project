package comp3350.breadtunes.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import comp3350.breadtunes.business.interfaces.MediaManager;

public class AudioPlayer implements MediaManager {
    private MediaPlayer player;
    private boolean songPaused;

    public AudioPlayer() {
        songPaused = false;
        player = null;
    }


    @Override
    public void startPlayingSong(Context context, Uri songUri) {
        if (player != null) {
            player.reset();
            player.release();
        }

        player = MediaPlayer.create(context, songUri);
        player.start();
    }

    @Override
    public void stopPlayingSong() {
        if (player != null) {
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }

    @Override
    public void pausePlayingSong() {
        if (player != null && isPlaying()) {
            player.pause();
            songPaused = true;
        }
    }

    @Override
    public void resumePlayingSong() {
        if (player != null && isPaused()) {
            player.start();
            songPaused = false;
        }
    }

    @Override
    public boolean isPlaying() {
        boolean isPlaying = false;

        if (player != null) {
            isPlaying = player.isPlaying();
        }

        return isPlaying;
    }

    @Override
    public boolean isPaused() { return songPaused; }


    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration(){ return player.getDuration(); }

    @Override
    public void seekTo(int progress){player.seekTo(progress);}

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

}
