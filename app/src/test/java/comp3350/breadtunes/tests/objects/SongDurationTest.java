package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static org.junit.Assert.*;

public class SongDurationTest extends TestLogger {
    @Test
    public void testSongDurationConstructor1() {
        // Act
        SongDuration duration = new SongDuration(2, 2, 2);

        // Assert
        assertEquals(2, duration.getHours());
        assertEquals(2, duration.getMinutes());
        assertEquals(2, duration.getSeconds());
    }

    @Test
    public void testSongDurationConstructor2() {
        // Act
        SongDuration duration = new SongDuration(2, 2);

        // Assert
        assertEquals(0, duration.getHours());
        assertEquals(2, duration.getMinutes());
        assertEquals(2, duration.getSeconds());
    }

    @Test
    public void testSongDurationSetters() {
        // Act
        SongDuration duration = new SongDuration(0, 0, 0);
        duration.setHours(10);
        duration.setMinutes(10);
        duration.setSeconds(10);

        int h = duration.getHours();
        int m = duration.getMinutes();
        int s = duration.getSeconds();

        // Assert
        assertEquals(10, h);
        assertEquals(10, m);
        assertEquals(10, s);
    }

    @Test
    public void testToDurationString(){
        SongDuration twoDigitDurations = new SongDuration(10,10,10);
        SongDuration singleDigitDurations = new SongDuration(1,1,1);

        assertEquals("10:10:10", twoDigitDurations.toDurationString());
        assertEquals("01:01:01", singleDigitDurations.toDurationString());
    }
}
