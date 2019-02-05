package comp3350.breadtunes.tests.business;

import org.junit.Test;

import comp3350.breadtunes.business.SongQueue;
import comp3350.breadtunes.objects.Song;

import static org.junit.Assert.assertEquals;

public class QueueTest {
    @Test
    public void testInsertQueue() {
        SongQueue q = new SongQueue(5);
        Song s = new Song();
        Song r = new Song();
        Song w = new Song();
        q.insert(s);
        q.insert(w);
        q.insert(r);
        assertEquals(3,q.size());
    }

    @Test
    public void testRemoveQueue() {
        SongQueue q = new SongQueue(5);
        Song s = new Song();
        Song r = new Song();
        Song w = new Song();
        q.insert(s);
        q.insert(w);
        q.insert(r);
        q.remove();
        q.remove();
        assertEquals(1,q.size());
    }
}