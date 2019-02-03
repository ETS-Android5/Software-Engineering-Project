package comp3350.breadtunes.objects;

import java.util.Collection;

public class Artist {
    private String name;
    private Collection<Album> albums;

    public Artist(String name, Collection<Album> albums) {
        this.name = name;
        this.albums = albums;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Collection<Album> getAlbums() { return albums; }
    public void setAlbums(Collection<Album> albums) { this.albums = albums; }
}
