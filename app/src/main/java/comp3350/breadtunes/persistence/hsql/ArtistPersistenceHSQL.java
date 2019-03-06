package comp3350.breadtunes.persistence.hsql;

import java.util.ArrayList;
import java.util.List;
import java8.util.stream.*;

import comp3350.breadtunes.persistence.ArtistPersistence;
import comp3350.breadtunes.objects.Artist;

import static java8.util.stream.StreamSupport.stream;

public class ArtistPersistenceHSQL implements ArtistPersistence {

    private final String databasePath;

    public ArtistPersistenceHSQL(String databasePath) {
        this.databasePath = databasePath;
    }

    public List<Artist> getAll() {
        return new ArrayList<>();
    }

    public Artist insert(Artist album) {
        return album;
    }

    public Artist update(Artist album) {
        return album;
    }

    public void delete(Artist album) {

    }
}
