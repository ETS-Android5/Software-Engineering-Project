package comp3350.breadtunes.objects;

import java.util.Collection;
import java.util.List;

public class Artist implements Comparable<Artist> {
    private String name;
    private List<Album> albums;

    public Artist(String name, List<Album> albums) {
        this.name = name;
        this.albums = albums;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Album> getAlbums() { return albums; }
    public void setAlbums(List<Album> albums) { this.albums = albums; }

    public int compareTo(Artist artist) {
        return name.compareTo(artist.getName());
    }
}
