package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.objects.Genre;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static org.junit.Assert.*;

public class GenreTest extends TestLogger {
    @Test
    public void testGenre() {
        // Act
        Genre genre = new Genre("Test Genre");

        // Assert
        assertNotNull(genre);
        assertTrue(genre.getName().equals("Test Genre"));
    }
}
