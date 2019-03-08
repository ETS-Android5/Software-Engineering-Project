package comp3350.breadtunes.objects;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class SongDuration implements Serializable {
    private int hours;
    private int minutes;
    private int seconds;

    public SongDuration(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public SongDuration(int minutes, int seconds) {
        this.hours = 0;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) { this.minutes = minutes; }

    public int getSeconds() { return seconds; }
    public void setSeconds(int seconds) { this.seconds = seconds; }

    public static SongDuration convertMillisToDuration(long millis) {
        int hours = (int) TimeUnit.MILLISECONDS.toHours(millis);

        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));

        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return new SongDuration(hours, minutes , seconds);
    }
}
