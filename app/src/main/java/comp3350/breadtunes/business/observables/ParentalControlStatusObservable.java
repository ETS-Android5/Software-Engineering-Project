package comp3350.breadtunes.business.observables;

import java.util.Observable;


public class ParentalControlStatusObservable extends Observable { //play mode referes to if the music player is playing on repeat, on shuffle, or both or neither

    private boolean parentalControlStatus;

    public ParentalControlStatusObservable() {
    }

    public void setParentalControlStatus(boolean status) {
        this.parentalControlStatus = status;
        setChanged();
        notifyObservers();
    }

    public String getParentalControlStatus() {
        if(parentalControlStatus){
            return "Parental Control On";
        }else{
            return "Parental Control Off";
        }
    }

}