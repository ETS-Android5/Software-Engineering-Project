package comp3350.breadtunes.business.interfaces;

import comp3350.breadtunes.objects.Song;

public interface sQueue {
    void insert(Song insertSong);

    Song remove();

    void addSongToPlayNext(Song nextSong);

    Song getSong(int index);

    int size();

    boolean isEmpty();

    boolean isFull();

}
