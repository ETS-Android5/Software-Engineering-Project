package comp3350.breadtunes.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.breadtunes.exception.RecordDoesNotExistException;
import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.persistence.ArtistPersistence;

public class ArtistPersistenceStub implements ArtistPersistence {
    private List<Artist> artists;

    public ArtistPersistenceStub() {
        artists = new ArrayList<>();

        artists.add(new Artist("Artist 1", new ArrayList<Album>()));
        artists.add(new Artist("Artist 2", new ArrayList<Album>()));
        artists.add(new Artist("Artist 3", new ArrayList<Album>()));
    }

    @Override
    public List<Artist> getAll() {
        return Collections.unmodifiableList(artists);
    }

    @Override
    public Artist insert(Artist insertArtist) {
        // Not checking for duplicates
        artists.add(insertArtist);
        return insertArtist;
    }

    @Override
    public Artist update(Artist updateArtist) {
        int index = artists.indexOf(updateArtist);

        if (index < 0) {
            throw new RecordDoesNotExistException(String.format("Trying to update nonexistent song (name: %s).", updateArtist.getName()));
        }

        artists.set(index, updateArtist);

        return updateArtist;
    }

    @Override
    public void delete(Artist deleteArtist) {
        int index = artists.indexOf(deleteArtist);

        if (index < 0) {
            throw new RecordDoesNotExistException(String.format("Tying to delete nonexistent album (name: %s)", deleteArtist.getName()));
        }

        artists.remove(index);
    }
}
