package comp3350.breadtunes.persistence.hsql;

import java.util.ArrayList;
import java.util.List;
import java8.util.stream.*;

import comp3350.breadtunes.persistence.AlbumPersistence;
import comp3350.breadtunes.objects.Album;

import static java8.util.stream.StreamSupport.stream;

public class AlbumPersistenceHSQL implements AlbumPersistence {

    private final String databasePath;

    public AlbumPersistenceHSQL(String databasePath) {
        this.databasePath = databasePath;
    }

    public List<Album> getAll() {
        // TODO: REMOVE
        // Stream<Album> albumStream = stream(albums);
        // Album[] filtered = (Album[]) albumStream.filter(album -> album.getName().contains("1")).toArray();
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
