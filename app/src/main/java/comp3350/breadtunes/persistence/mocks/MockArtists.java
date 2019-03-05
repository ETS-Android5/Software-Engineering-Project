package comp3350.breadtunes.persistence.mocks;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Artist;

public class MockArtists {
    public static List<Artist> getMockArtistList() {
        List<Artist> artists = new ArrayList<>();

        artists.add(new Artist("Artist 1", new ArrayList<>()));
        artists.add(new Artist("Artist 2", new ArrayList<>()));
        artists.add(new Artist("Artist 3", new ArrayList<>()));

        return artists;
    }
}
