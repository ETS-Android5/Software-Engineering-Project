package comp3350.breadtunes.persistence.hsql;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.persistence.AlbumPersistence;
import comp3350.breadtunes.objects.Album;

public class AlbumPersistenceHSQL implements AlbumPersistence {
    public AlbumPersistenceHSQL() { }

    public List<Album> getAll() {
        return new ArrayList<>();
    }

    public Album insert(Album album) {
        return album;
    }

    public Album update(Album album) {
        return album;
    }

    public void delete(Album album) {

    }
}
