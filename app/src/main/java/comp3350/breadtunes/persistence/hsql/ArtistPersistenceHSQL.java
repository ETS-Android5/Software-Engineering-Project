package comp3350.breadtunes.persistence.hsql;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.persistence.ArtistPersistence;
import comp3350.breadtunes.objects.Artist;

public class ArtistPersistenceHSQL implements ArtistPersistence {
    public ArtistPersistenceHSQL() {
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
