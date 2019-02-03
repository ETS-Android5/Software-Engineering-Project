package comp3350.breadtunes.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return null;
    }

    @Override
    public Album update(Album updateAlbum) {
        return null;
    }

    @Override
    public void delete(Album deleteAlbum) {
    }
}
