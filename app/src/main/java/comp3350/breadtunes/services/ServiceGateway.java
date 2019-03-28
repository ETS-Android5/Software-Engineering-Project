package comp3350.breadtunes.services;

import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.business.MusicPlayerState;
import comp3350.breadtunes.business.SongFlagger;
import comp3350.breadtunes.persistence.interfaces.CredentialPersistence;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.persistence.loaders.AlbumArtLoader;
import comp3350.breadtunes.presentation.interfaces.MediaManager;
import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.hsql.*;

public class ServiceGateway
{
	private static SongPersistence songPersistence = null;
    private static AudioPlayer audioPlayer = null;
    private static MusicPlayerState musicPlayerState = null;
    private static CredentialManager credentialManager = null;
    private static CredentialPersistence credentialPersistence = null;
    private static DatabaseManager dbManager = null;
    private static AlbumArtLoader albumArtLoader = null;
    private static SongFlagger songFlagger = null;

    public static synchronized SongFlagger getSongFlagger(){
        if (songFlagger == null){
            songFlagger = new SongFlagger(getSongPersistence());
        }

        return songFlagger;
    }

    public static synchronized MediaManager getMediaManager() {
       if (audioPlayer == null)  {
           audioPlayer = new AudioPlayer();
       }

       return audioPlayer;
    }

    public static synchronized MusicPlayerState getMusicPlayerState() {
        if (musicPlayerState == null) {
            musicPlayerState = new MusicPlayerState(getSongPersistence());
        }

        return musicPlayerState;
    }

    public static synchronized AlbumArtLoader getAlbumArtLoader() {
        if (albumArtLoader == null) {
            albumArtLoader = new AlbumArtLoader(AppState.applicationContext);
        }

        return albumArtLoader;
    }

    public static synchronized DatabaseManager getDatabaseManager() {
        if (dbManager == null) {
            dbManager = DatabaseManager.getInstance();
        }

        return dbManager;
    }

    public static synchronized SongPersistence getSongPersistence() {
        if (songPersistence == null) {
            songPersistence = new SongPersistenceHSQL();
        }

        return songPersistence;
    }

    public static synchronized CredentialManager getCredentialManager() {
        if (credentialManager == null) {
            credentialManager = new CredentialManager(getCredentialPersistence());
        }

        return credentialManager;
    }

    public static synchronized CredentialPersistence getCredentialPersistence() {
        if (credentialPersistence == null) {
            credentialPersistence = new CredentialPersistenceHSQL();
        }

        return credentialPersistence;
    }
}
