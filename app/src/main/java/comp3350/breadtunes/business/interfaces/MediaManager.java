package comp3350.breadtunes.business.interfaces;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public interface MediaManager {

    void startPlayingSong(Context context, Uri songUri);

    void stopPlayingSong();

    void pausePlayingSong();

    void resumePlayingSong();

    boolean isPlaying();

    boolean isPaused();

    int getCurrentPosition();

    int getDuration();

    void seekTo(int progress);

    void setOnCompletionListener(MediaPlayer.OnCompletionListener listener);

    //void close();
}
