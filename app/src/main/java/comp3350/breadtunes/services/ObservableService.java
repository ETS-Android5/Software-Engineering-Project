package comp3350.breadtunes.services;

import java.util.Observer;

import comp3350.breadtunes.business.observables.*;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.presentation.enums.DatabaseState;

public class ObservableService {
    private static DatabaseUpdatedObservable databaseState = new DatabaseUpdatedObservable();
    private static SongObservable songState = new SongObservable();
    private static ParentalControlStatusObservable parentalStatus = new ParentalControlStatusObservable();
    private static PlayModeObservable playMode = new PlayModeObservable();

    public static void subscribeToDatabaseStateChanges(Observer observer) {
        databaseState.addObserver(observer);
    }

    public static void updateDatabaseState(DatabaseState state) {
        databaseState.setValue(state);
    }

    public static void subscribeToSongChanges(Observer observer) {
        songState.addObserver(observer);
    }

    public static void updateCurrentSong(Song song) {
        songState.setValue(song);
    }

    public static void subscribeToParentalModeStatus(Observer observer) {
        parentalStatus.addObserver(observer);
    }

    public static void updateParentalModeStatus(boolean status) {
        parentalStatus.setValue(status);
    }

    public static void subscribeToPlayModeChange(Observer observer) {
        playMode.addObserver(observer);
    }

    public static void updatePlayMode(String mode) {
        playMode.setValue(mode);
    }
}
