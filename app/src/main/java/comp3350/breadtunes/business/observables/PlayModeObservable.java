package comp3350.breadtunes.business.observables;

import java.util.Observable;


public class PlayModeObservable extends Observable { //play mode referes to if the music player is playing on repeat, on shuffle, or both or neither
    private String playMode;

    public PlayModeObservable() {
    }

    public void setPlayMode(String newMode){
        this.playMode = newMode;
        setChanged();
        notifyObservers();
    }

    public String getPlayMode() {
        return playMode;
    }
}
