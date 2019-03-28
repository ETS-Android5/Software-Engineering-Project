package comp3350.breadtunes.business;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;

public class SongFlagger {
    SongPersistence songPersistence;

    public SongFlagger(SongPersistence songPersistence) {
        this.songPersistence = songPersistence;
    }

    public void flagSong(Song song, boolean flag){
        songPersistence.setSongFlagged(song, flag);
    }

    public boolean songIsFlagged(Song song){
        boolean songFlagged = songPersistence.isSongFlagged(song);
        return songFlagged;
    }
}
