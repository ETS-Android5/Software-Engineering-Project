package comp3350.breadtunes.tests.mocks;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Artist;

public class MockArtists {
    public static List<Artist> getMockArtistList() {
        List<Artist> artists = new ArrayList<>();

        artists.add(new Artist("Beyonce", new ArrayList<>()));
        artists.add(new Artist("Bruce Springsteen", new ArrayList<>()));
        artists.add(new Artist("Beethoven", new ArrayList<>()));

        return artists;
    }
}
