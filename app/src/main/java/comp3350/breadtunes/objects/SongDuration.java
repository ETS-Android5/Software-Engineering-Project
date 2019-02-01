package comp3350.breadtunes.objects;

public class SongDuration {
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
}
