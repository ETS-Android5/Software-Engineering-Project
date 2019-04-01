package comp3350.breadtunes.business.observables;

public class ParentalControlStatusObservable extends BreadTunesObservable<Boolean> {

    private boolean parentalControlStatus;

    public ParentalControlStatusObservable() {
    }

    public void setValue(Boolean status) {
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

    public Boolean getValue() {
        return parentalControlStatus;
    }
}