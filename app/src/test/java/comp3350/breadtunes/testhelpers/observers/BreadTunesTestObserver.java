package comp3350.breadtunes.testhelpers.observers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import comp3350.breadtunes.business.observables.BreadTunesObservable;

public class BreadTunesTestObserver<T> implements Observer {
    private List<T> receivedValues = new ArrayList<>();

    public T getMostRecentReceived() {
        if (receivedValues.isEmpty()) {
            return null;
        }

        return receivedValues.get(receivedValues.size() - 1);
    }

    public List<T> getAllReceived() {
        return receivedValues;
    }

    public void update(Observable obs, Object obj) {
        if (obs instanceof BreadTunesObservable) {
            receivedValues.add((T) ((BreadTunesObservable) obs).getValue());
        } else {
            System.out.println("WARN: BreadTunesTestObserver does not work with Observables not of type BreadTunesObservable");
        }
    }
}
