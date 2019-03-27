package comp3350.breadtunes.services;

import java.util.Observer;
import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.business.SongFlagger;
import comp3350.breadtunes.persistence.interfaces.CredentialPersistence;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.persistence.loaders.AlbumArtLoader;
import comp3350.breadtunes.presentation.enums.DatabaseState;
import comp3350.breadtunes.presentation.interfaces.MediaManager;
import comp3350.breadtunes.business.observables.DatabaseUpdatedObservable;
import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.hsql.*;

public class ServiceGateway
{
	private static SongPersistence songPersistence = null;
    private static AudioPlayer audioPlayer = null;
    private static CredentialManager credentialManager = null;
    private static CredentialPersistence credentialPersistence = null;
    private static DatabaseUpdatedObservable dbObservable = new DatabaseUpdatedObservable();
    private static DatabaseManager dbManager = null;
    private static AlbumArtLoader albumArtLoader = null;
    private static SongFlagger songFlagger = null;

    public static void subscribeToDatabaseStateChanges(Observer observer) {
        dbObservable.addObserver(observer);
    }

    public static void updateDatabaseState(DatabaseState state) {
        dbObservable.setState(state);
    }

    public static synchronized SongFlagger getSongFlagger(){
        if (songFlagger == null){
            songFlagger = new SongFlagger();
        }

        return songFlagger;
    }

    public static synchronized MediaManager getMediaManager() {
       if (audioPlayer == null)  {
           audioPlayer = new AudioPlayer();
       }

       return audioPlayer;
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
            credentialManager = new CredentialManager();
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
