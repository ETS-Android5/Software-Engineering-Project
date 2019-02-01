package comp3350.breadtunes.objects;

public class Song {
    private String name;
    private int year = Integer.MIN_VALUE;
    private int trackNumber;
    private SongDuration duration;
    private Artist artist;
    private Album album;

    public Song(String name, int trackNumber, SongDuration duration, Artist artist, Album album) {
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getTrackNumber() { return trackNumber; }
    public void setTrackNumber(int trackNumber) { this.trackNumber = trackNumber; }

    public SongDuration getDuration() { return duration; }
    public void setDuration(SongDuration duration) { this.duration = duration; }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
}
