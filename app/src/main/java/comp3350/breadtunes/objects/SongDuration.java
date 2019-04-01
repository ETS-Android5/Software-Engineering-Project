package comp3350.breadtunes.objects;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SongDuration implements Serializable {
    private static final String toStringFormat = "H:%dM:%dS:%d";
    private static final Pattern durationStringPattern = Pattern.compile("H:(\\d+)M:(\\d+)S:(\\d+)");
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

    public SongDuration(String songDurationStringRepresentation) {
        Matcher matcher = durationStringPattern.matcher(songDurationStringRepresentation);

        if (matcher.find()) {
            hours = Integer.parseInt(matcher.group(1));
            minutes = Integer.parseInt(matcher.group(2));
            seconds = Integer.parseInt(matcher.group(3));
        } else {
            hours = 0;
            minutes = 0;
            seconds = 0;
        }
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

    public String toDurationString(){
        String result = "";

        if(this.hours < 1){}
            // do nothing
        else if(this.hours < 10){
            result += "0"+hours+":";
        }
        else{
            result += hours+":";
        }

        if(this.minutes < 10){
            result += "0"+minutes;
        }else{
            result += ""+minutes;
        }

        if(seconds < 10){
            result += ":0"+seconds;
        }else{
            result += ":"+seconds;
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format(toStringFormat, hours, minutes, seconds);
    }
}
