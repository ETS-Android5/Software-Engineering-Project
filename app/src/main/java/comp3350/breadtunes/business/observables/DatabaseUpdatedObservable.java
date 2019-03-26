package comp3350.breadtunes.business.observables;

import java.util.Observable;

import comp3350.breadtunes.presentation.enums.DatabaseState;

public class DatabaseUpdatedObservable extends Observable {
    private DatabaseState state;

    public DatabaseUpdatedObservable() {
    }

    public void setState(DatabaseState state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }

    public DatabaseState getState() {
        return state;
    }
}
