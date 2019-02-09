package comp3350.breadtunes.services;

import android.content.Context;
import android.media.MediaPlayer;

import comp3350.breadtunes.business.interfaces.MediaManager;

public class AudioPlayer implements MediaManager {
    MediaPlayer player;
    boolean songPaused;

    public AudioPlayer() {
        songPaused = false;
        player = null;
    }

    @Override
    public void startPlayingSong(Context context, int resourceId) {
        if (player != null) {
            player.reset();
            player.release();
        }

        player = MediaPlayer.create(context, resourceId);
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
        if (player != null && songPaused) {
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
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

    @Override
    public void close() {
        if (player != null) {
            if (isPlaying()) {
                player.stop();
            }

            player.reset();
            player.release();
        }
    }
}