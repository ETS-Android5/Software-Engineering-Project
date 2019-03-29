package comp3350.breadtunes.business.observables;

import java.util.Observable;

// For application observables to be testable, extend this class
public abstract class BreadTunesObservable<T> extends Observable {
    public abstract void setValue(T val);

    public abstract T getValue();
}
