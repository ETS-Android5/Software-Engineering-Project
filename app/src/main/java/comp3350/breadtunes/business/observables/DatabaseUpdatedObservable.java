package comp3350.breadtunes.business.observables;

import comp3350.breadtunes.presentation.enums.DatabaseState;

public class DatabaseUpdatedObservable extends BreadTunesObservable<DatabaseState> {
    private DatabaseState state;

    public DatabaseUpdatedObservable() {
    }

    public void setValue(DatabaseState state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }

    public DatabaseState getValue() {
        return state;
    }
}
