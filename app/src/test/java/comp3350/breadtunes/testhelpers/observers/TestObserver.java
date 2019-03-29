package comp3350.breadtunes.testhelpers.observers;

import java.util.Observer;

public abstract class TestObserver<T> implements Observer {
    public abstract T getMostRecent();
}
