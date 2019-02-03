package comp3350.breadtunes.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.breadtunes.objects.Album;
import comp3350.breadtunes.objects.Artist;
import comp3350.breadtunes.persistence.ArtistPersistence;

public class ArtistPersistenceStub implements ArtistPersistence {
    private List<Artist> artists;

    public ArtistPersistenceStub() {
        artists = new ArrayList<Artist>();

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
        return null;
    }

    @Override
    public Artist update(Artist updateArtist) {
        return null;
    }

    @Override
    public void delete(Artist deleteArtist) {
    }
}
