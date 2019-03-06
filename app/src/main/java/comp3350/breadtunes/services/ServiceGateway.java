package comp3350.breadtunes.services;

import comp3350.breadtunes.business.interfaces.MediaManager;
import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.hsql.*;

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
            songPersistence = new SongPersistenceHSQL(AppState.databasePath);
        }

        return songPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence() {
        if (albumPersistence == null) {
            albumPersistence = new AlbumPersistenceHSQL(AppState.databasePath);
        }

        return albumPersistence;
    }

    public static synchronized ArtistPersistence getArtistPersistence() {
        if (artistPersistence == null) {
            artistPersistence = new ArtistPersistenceHSQL(AppState.databasePath);
        }

        return artistPersistence;
    }
}
