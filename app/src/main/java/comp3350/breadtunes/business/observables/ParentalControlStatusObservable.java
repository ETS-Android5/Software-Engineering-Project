package comp3350.breadtunes.business.observables;

import java.util.Observable;


public class ParentalControlStatusObservable extends Observable {

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

    public boolean getParentalControlStatusBoolean() {
        return parentalControlStatus;
    }
}