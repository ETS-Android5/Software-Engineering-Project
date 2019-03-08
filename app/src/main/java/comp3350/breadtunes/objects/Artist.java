package comp3350.breadtunes.objects;

import java.util.List;

public class Artist implements Comparable<Artist> {
    private int artistId;
    private String name;
    private List<Album> albums;

    public Artist(String name, List<Album> albums) {
        this.name = name;
        this.albums = albums;
    }

    public Artist(int artistId,String name, List<Album> albums) {
        this.artistId = artistId;
        this.name = name;
        this.albums = albums;
    }

    public int getId() { return artistId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Album> getAlbums() { return albums; }
    public void setAlbums(List<Album> albums) { this.albums = albums; }

    public int compareTo(Artist artist) {
        return name.compareToIgnoreCase(artist.getName());
    }
}
