package comp3350.breadtunes.services;

import java.util.Observer;

import comp3350.breadtunes.business.enums.DatabaseState;
import comp3350.breadtunes.business.interfaces.MediaManager;
import comp3350.breadtunes.business.observables.DatabaseUpdatedObservable;
import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.hsql.*;

public class ServiceGateway
{
	private static SongPersistence songPersistence = null;
    private static AudioPlayer audioPlayer = null;
    private static DatabaseUpdatedObservable dbObservable = new DatabaseUpdatedObservable();

    public static void subscribeToDatabaseStateChanges(Observer observer) {
        dbObservable.addObserver(observer);
    }

    public static void updateDatabaseState(DatabaseState state) {
        dbObservable.setState(state);
    }

    public static synchronized MediaManager getMediaManager() {
       if (audioPlayer == null)  {
           audioPlayer = new AudioPlayer();
       }

       return audioPlayer;
    }

    public static synchronized SongPersistence getSongPersistence() {
        if (songPersistence == null) {
            songPersistence = new SongPersistenceHSQL();
        }

        return songPersistence;
    }
}
