package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static org.junit.Assert.*;

public class SongDurationTest extends TestLogger {
    @Test
    public void testSongDurationConstructor1() {
        // Act
        SongDuration duration = new SongDuration(2, 2, 2);

        // Assert
        assertTrue(duration.getHours() == 2);
        assertTrue(duration.getMinutes() == 2);
        assertTrue(duration.getSeconds() == 2);
    }

    @Test
    public void testSongDurationConstructor2() {
        // Act
        SongDuration duration = new SongDuration(2, 2);

        // Assert
        assertTrue(duration.getHours() == 0);
        assertTrue(duration.getMinutes() == 2);
        assertTrue(duration.getSeconds() == 2);
    }

    @Test
    public void testSongDurationSetters() {
        // Act
        SongDuration duration = new SongDuration(0, 0, 0);
        duration.setHours(10);
        duration.setMinutes(10);
        duration.setSeconds(10);

        // Assert
        assertTrue(duration.getHours() == 10);
        assertTrue(duration.getMinutes() == 10);
        assertTrue(duration.getSeconds() == 10);
    }
}
