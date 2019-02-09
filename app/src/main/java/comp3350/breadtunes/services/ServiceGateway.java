package comp3350.breadtunes.services;

import comp3350.breadtunes.business.interfaces.MediaManager;
import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.stubs.*;

public class ServiceGateway
{
	private static SongPersistence songPersistence = null;
    private static AlbumPersistence albumPersistence = null;
    private static ArtistPersistence artistPersistence = null;
    private static AudioPlayer audioPlayer = null;

    public static synchronized MediaManager getMediaManager() {
       if (audioPlayer == null)  {
           audioPlayer = new AudioPlayer();
       }

       return audioPlayer;
    }

    public static synchronized SongPersistence getSongPersistence() {
        if (songPersistence == null) {
            songPersistence = new SongPersistenceStub();
        }

        return songPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence() {
        if (albumPersistence == null) {
            albumPersistence = new AlbumPersistenceStub();
        }

        return albumPersistence;
    }

    public static synchronized ArtistPersistence getArtistPersistence() {
        if (artistPersistence == null) {
            artistPersistence = new ArtistPersistenceStub();
        }

        return artistPersistence;
    }
}
