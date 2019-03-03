package comp3350.breadtunes.tests.mocks;

import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.objects.Album;

public class MockAlbums {
    public static List<Album> getMockAlbumList() {
        List<Album> albums = new ArrayList<>();

        albums.add(new Album("2019 Top Hits", new ArrayList<>()));
        albums.add(new Album("Album 2", new ArrayList<>()));
        albums.add(new Album("Album 3", new ArrayList<>()));
        albums.add(new Album("Album 4", new ArrayList<>()));

        return albums;
    }
}