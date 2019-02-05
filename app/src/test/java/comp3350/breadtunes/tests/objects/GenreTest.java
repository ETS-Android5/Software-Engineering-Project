package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import comp3350.breadtunes.objects.Genre;

import static org.junit.Assert.*;

public class GenreTest {
    @Test
    public void testGenre() {
        System.out.println("\nRunning testGenre");

        // Act
        Genre genre = new Genre("Test Genre");

        // Assert
        assertNotNull(genre);
        assertTrue(genre.getName().equals("Test Genre"));

        System.out.println("Finished testGenre");
    }
}
