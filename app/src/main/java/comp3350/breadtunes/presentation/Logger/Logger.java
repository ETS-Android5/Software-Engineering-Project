package comp3350.breadtunes.presentation.Logger;

import android.util.Log;

public class Logger {
    public Logger() {
    }

    public void i(String tag, String message) {
        Log.i(tag, message);
    }
}
