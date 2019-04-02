package comp3350.breadtunes.services;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.concurrent.TimeUnit;

import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.presentation.interfaces.MediaManager;

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
    public String getCurrentPositionString(){
        int currentPos = ServiceGateway.getMediaManager().getCurrentPosition();
        int hours = (int) TimeUnit.MILLISECONDS.toHours(currentPos);
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(currentPos) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(currentPos)));
        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(currentPos) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPos)));

        SongDuration currentPosition = new SongDuration(hours, minutes, seconds);

        return currentPosition.toDurationString();
    }

    @Override
    public String getDurationString(){
        int songDuration = ServiceGateway.getMediaManager().getDuration();
        int hours = (int) TimeUnit.MILLISECONDS.toHours(songDuration);
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(songDuration) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(songDuration)));
        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(songDuration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(songDuration)));

        SongDuration currentSongDuration = new SongDuration(hours, minutes, seconds);

        return currentSongDuration.toDurationString();
    }

    @Override
    public void seekTo(int progress){player.seekTo(progress);}

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

}
