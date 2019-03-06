package comp3350.breadtunes.persistence.hsql;

import java.util.ArrayList;
import java.util.List;
import java8.util.stream.*;

import comp3350.breadtunes.persistence.SongPersistence;
import comp3350.breadtunes.objects.Song;

import static java8.util.stream.StreamSupport.stream;

public class SongPersistenceHSQL implements SongPersistence {

    private final String databasePath;

    public SongPersistenceHSQL(String databasePath) {
        this.databasePath = databasePath;
    }

    public List<Song> getAll() {
        return new ArrayList<>();
    }

    public Song insert(Song album) {
        return album;
    }

    public Song update(Song album) {
        return album;
    }

    public void delete(Song album) {

    }
}
