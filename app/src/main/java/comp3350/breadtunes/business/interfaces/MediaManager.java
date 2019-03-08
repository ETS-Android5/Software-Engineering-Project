package comp3350.breadtunes.business.interfaces;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public interface MediaManager {
    void startPlayingSong(Context context, int resourceId);

    void startPlayingSong(Context context, Uri songUri);

    MediaPlayer create(Context context, Uri songUri);

    MediaPlayer create(Context context, int resourceId);

    void stopPlayingSong();

    void pausePlayingSong();

    void resumePlayingSong();

    boolean isPlaying();

    boolean isPaused();

    int getCurrentPosition();

    void setOnCompletionListener(MediaPlayer.OnCompletionListener listener);

    void close();
}
