package comp3350.breadtunes.tests.business;

import org.junit.Test;

import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static org.junit.Assert.*;

public class QueueTest extends TestLogger {
    @Test
    public void testInsertQueue() {
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
    }

    @Test
    public void testRemoveQueue() {
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
    }

    @Test
    public void testRemoveEmptyQueue() {
        // Arrange
        SongQueue q = new SongQueue(5);

        // Act
        Song song = q.remove();

        // Assert
        assertNull(song);
        assertTrue(q.isEmpty());
    }

    @Test
    public void testAddFullQueue() {
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
    }

    @Test(expected = NegativeArraySizeException.class)
    public void testNegativeSizeQueue() {
        SongQueue q = new SongQueue(-1);
    }

    @Test
    public void testZeroSizeQueue() {
        // Act
        SongQueue q = new SongQueue(0);
        q.insert(new Song());
        Song song = q.remove();

        // Assert
        assertTrue(q.isEmpty());
        assertTrue(q.isFull());
        assertTrue(q.size() == 0);
        assertNull(song);
    }

    @Test
    public void testOneSizeQueue() {
        // Arrange
        Song song1 = new Song();
        Song song2 = new Song();

        // Act
        SongQueue q = new SongQueue(1);
        q.insert(song1);
        q.insert(song2);
        Song expectedSong = q.remove();

        // Assert
        assertEquals(expectedSong, song1); // Expect that the only item in the updateQueue was song1
        assertNotNull(expectedSong);
        assertFalse(q.isFull());
        assertTrue(q.isEmpty());
        assertTrue(q.size() == 0);
    }

    @Test
    public void testAddSongToPlayNext()
    {
        // Arrange
        Song mocksong1 = new Song();
        Song mocksong2 = new Song();
        Song mocksong3 = new Song();
        Song mocksong4 = new Song();
        Song mocksong5 = new Song();

        // Act
        SongQueue testTarget = new SongQueue(5);
        testTarget.insert(mocksong1);
        testTarget.insert(mocksong2);
        testTarget.insert(mocksong3);

        testTarget.addSongToPlayNext(mocksong4);
        testTarget.addSongToPlayNext(mocksong5);

        assertEquals(testTarget.getSong(0), mocksong5);
        assertEquals(testTarget.getSong(1), mocksong4);
        assertEquals(testTarget.getSong(2), mocksong1);
        assertEquals(testTarget.getSong(3), mocksong2);
        assertEquals(testTarget.getSong(4), mocksong3);
    }// testAddSongToPlayNext
}
