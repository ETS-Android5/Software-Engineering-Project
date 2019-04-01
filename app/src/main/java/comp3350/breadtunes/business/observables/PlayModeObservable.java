package comp3350.breadtunes.business.observables;

import java.util.Observable;


public class PlayModeObservable extends BreadTunesObservable<String> {
    private String playMode;

    public PlayModeObservable() {
    }

    public void setValue(String newMode){
        this.playMode = newMode;
        setChanged();
        notifyObservers();
    }

    public String getValue() {
        return playMode;
    }
}
