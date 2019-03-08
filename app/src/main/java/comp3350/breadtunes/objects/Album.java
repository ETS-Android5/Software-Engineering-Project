package comp3350.breadtunes.objects;

import java.util.List;

public class Album implements Comparable<Album> {
    private int albumId;
    private String name;
    private List<Song> songs;
    private Genre genre = null;

    public Album(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public Album(int albumId, String name, List<Song> songs) {
        this.albumId = albumId;
        this.name = name;
        this.songs = songs;
    }

    public Album(String name, List<Song> songs, Genre genre) {
        this.name = name;
        this.songs = songs;
        this.genre = genre;
    }

    public int getId() { return albumId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Song> getSongs() { return songs; }
    public void setSongs(List<Song> songs) { this.songs = songs; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public int compareTo(Album album) {
        return name.compareTo(album.getName());
    }
}
