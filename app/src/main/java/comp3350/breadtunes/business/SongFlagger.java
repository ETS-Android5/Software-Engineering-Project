package comp3350.breadtunes.business;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.services.ServiceGateway;

public class SongFlagger {

    public void flagSong(Song song, boolean flag){
        SongPersistence songPersistence = ServiceGateway.getSongPersistence();
        songPersistence.setSongFlagged(song, flag);
    }

    public boolean songIsFlagged(Song song){
        SongPersistence songPersistence = ServiceGateway.getSongPersistence();
        boolean songFlagged = songPersistence.isSongFlagged(song);
        return songFlagged;

        //return song.getFlaggedStatus();
    }
}
