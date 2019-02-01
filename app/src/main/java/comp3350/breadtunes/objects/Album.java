package comp3350.breadtunes.objects;

import java.util.Collection;

class Album {
    private String name;
    private Collection<Song> songs;
    private Genre genre = null;

    public Album(String name, Collection<Song> songs) {
        this.name = name;
        this.songs = songs;
        this.genre = new UnknownGenre();
    }

    public Album(String name, Collection<Song> songs, Genre genre) {
        this.name = name;
        this.songs = songs;
        this.genre = genre;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Collection<Song> getSongs() { return songs; }
    public void setSongs(Collection<Song> songs) { this.songs = songs; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
}
