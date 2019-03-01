package comp3350.breadtunes.objects;

import java.util.List;

public class Album {
    private String name;
    private List<Song> songs;
    private Genre genre = null;

    public Album(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public Album(String name, List<Song> songs, Genre genre) {
        this.name = name;
        this.songs = songs;
        this.genre = genre;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Song> getSongs() { return songs; }
    public void setSongs(List<Song> songs) { this.songs = songs; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
}
