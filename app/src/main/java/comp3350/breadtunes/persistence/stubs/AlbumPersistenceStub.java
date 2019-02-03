package comp3350.breadtunes.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.breadtunes.exception.RecordDoesNotExistException;
import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.AlbumPersistence;

public class AlbumPersistenceStub implements AlbumPersistence {
    private List<Album> albums;

    public AlbumPersistenceStub() {
        albums = new ArrayList<>();

        albums.add(new Album("Album 1", new ArrayList<Song>()));
        albums.add(new Album("Album 2", new ArrayList<Song>()));
        albums.add(new Album("Album 3", new ArrayList<Song>()));
        albums.add(new Album("Album 4", new ArrayList<Song>()));
    }

    @Override
    public List<Album> getAll() {
        return Collections.unmodifiableList(albums);
    }

    @Override
    public Album insert(Album insertAlbum) {
        // Not checking for duplicates
        albums.add(insertAlbum);
        return insertAlbum;
    }

    @Override
    public Album update(Album updateAlbum) {
        int index = albums.indexOf(updateAlbum);

        if (index < 0) {
            throw new RecordDoesNotExistException(String.format("Trying to update nonexistent album (name: %s).", updateAlbum.getName()));
        }

        albums.set(index, updateAlbum);

        return updateAlbum;
    }

    @Override
    public void delete(Album deleteAlbum) {
        int index = albums.indexOf(deleteAlbum);

        if (index < 0) {
            throw new RecordDoesNotExistException(String.format("Tying to delete nonexistent album (name: %s)", deleteAlbum.getName()));
        }

        albums.remove(index);
    }
}
