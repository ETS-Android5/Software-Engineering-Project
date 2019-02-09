package comp3350.breadtunes.business.interfaces;

import android.content.Context;
import android.media.MediaPlayer;

public interface MediaManager {
    void startPlayingSong(Context context, int resourceId);

    void stopPlayingSong();

    void pausePlayingSong();

    void resumePlayingSong();

    boolean isPlaying();

    int getCurrentPosition();

    void setOnCompletionListener(MediaPlayer.OnCompletionListener listener);

    void close();
}
