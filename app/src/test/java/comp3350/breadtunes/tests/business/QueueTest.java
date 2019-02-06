package comp3350.breadtunes.tests.business;

import org.junit.Test;

import comp3350.breadtunes.business.SongQueue;
import comp3350.breadtunes.objects.Song;

import static org.junit.Assert.*;

public class QueueTest {
    @Test
    public void testInsertQueue() {
        System.out.println("\nStarting testInsertQueue");

        // Arrange
        SongQueue q = new SongQueue(5);
        Song s = new Song();
        Song r = new Song();
        Song w = new Song();

        // Act
        q.insert(s);
        q.insert(w);
        q.insert(r);

        // Assert
        assertEquals(3,q.size());
        assertFalse(q.isEmpty());
        assertFalse(q.isFull());

        System.out.println("Finished testInsertQueue");
    }

    @Test
    public void testRemoveQueue() {
        System.out.println("\nStarting testRemoveQueue");

        // Arrange
        SongQueue q = new SongQueue(5);
        Song s = new Song();
        Song r = new Song();
        Song w = new Song();

        // Act
        q.insert(s);
        q.insert(w);
        q.insert(r);
        q.remove();
        q.remove();

        // Assert
        assertEquals(1,q.size());
        assertFalse(q.isEmpty());
        assertFalse(q.isFull());

        System.out.println("Finished testRemoveQueue");
    }

    @Test
    public void testRemoveEmptyQueue() {
        System.out.println("\nStarting testRemoveEmptyQueue");

        // Arrange
        SongQueue q = new SongQueue(5);

        // Act
        Song song = q.remove();

        // Assert
        assertNull(song);
        assertTrue(q.isEmpty());

        System.out.println("Finished testRemoveEmptyQueue");
    }

    @Test
    public void testAddFullQueue() {
        System.out.println("\nStarting testAddFullQueue");

        // Arrange
        int queueSize = 2;
        SongQueue q = new SongQueue(queueSize);

        // Act
        q.insert(new Song());
        q.insert(new Song());
        q.insert(new Song());

        // Assert
        assertTrue(q.isFull());
        assertTrue(q.size() == queueSize);

        System.out.println("Finished testAddFullQueue");
    }

    @Test(expected = NegativeArraySizeException.class)
    public void testNegativeSizeQueue() {
        System.out.println("\nExpecting exception from testZeroSizeQueue");

        SongQueue q = new SongQueue(-1);
    }

    @Test
    public void testZeroSizeQueue() {
        System.out.println("\nStarting testZeroSizeQueue");

        // Act
        SongQueue q = new SongQueue(0);
        q.insert(new Song());
        Song song = q.remove();

        // Assert
        assertTrue(q.isEmpty());
        assertTrue(q.isFull());
        assertTrue(q.size() == 0);
        assertNull(song);

        System.out.println("Finished testZeroSizeQueue");
    }

    @Test
    public void testOneSizeQueue() {
        System.out.println("\nStarting testOneSizeQueue");

        // Arrange
        Song song1 = new Song();
        Song song2 = new Song();

        // Act
        SongQueue q = new SongQueue(1);
        q.insert(song1);
        q.insert(song2);
        Song expectedSong = q.remove();

        // Assert
        assertEquals(expectedSong, song2); // Expect that the only item in the queue was song2
        assertNotNull(expectedSong);
        assertTrue(q.isFull());
        assertFalse(q.isEmpty());
        assertTrue(q.size() == 1);

        System.out.println("Finished testOneSizeQueue");
    }
}
