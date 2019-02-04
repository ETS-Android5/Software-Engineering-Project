package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.objects.SongDuration;

import static org.junit.Assert.*;

public class SongDurationTest {
    @Test
    public void testSongDurationConstructor1() {
        System.out.println("\nStarting testSongDurationConstructor1");

        // Act
        SongDuration duration = new SongDuration(2, 2, 2);

        // Assert
        assertTrue(duration.getHours() == 2);
        assertTrue(duration.getMinutes() == 2);
        assertTrue(duration.getSeconds() == 2);

        System.out.println("Finished testSongDurationConstructor1");
    }

    @Test
    public void testSongDurationConstructor2() {
        System.out.println("\nStarting testSongDurationConstructor2");

        // Act
        SongDuration duration = new SongDuration(2, 2);

        // Assert
        assertTrue(duration.getHours() == 0);
        assertTrue(duration.getMinutes() == 2);
        assertTrue(duration.getSeconds() == 2);

        System.out.println("Finished testSongDurationConstructor2");
    }

    @Test
    public void testSongDurationSetters() {
        System.out.println("\nStarting testSongDurationSetters");

        // Act
        SongDuration duration = new SongDuration(0, 0, 0);
        duration.setHours(10);
        duration.setMinutes(10);
        duration.setSeconds(10);

        // Assert
        assertTrue(duration.getHours() == 10);
        assertTrue(duration.getMinutes() == 10);
        assertTrue(duration.getSeconds() == 10);

        System.out.println("Finished testSongDurationSetters");
    }
}
