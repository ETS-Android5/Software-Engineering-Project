package comp3350.breadtunes.objects;

import java.io.File;

public class Song {
    private int songId;
    private String name;
    private String rawName;
    private int year = Integer.MIN_VALUE;
    private int trackNumber;
    private SongDuration duration;
    private int artistId;
    private Artist artist;
    private String artistName;
    private String albumName;
    private int albumId;
    private Album album;
    private File songFile;


    public Song(){

    }
    public Song(String name, int trackNumber, SongDuration duration, Artist artist, Album album, File songFile) {
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.songFile = songFile;
    }

    public Song(String name, int trackNumber, SongDuration duration, Artist artist, Album album, String songPath) {
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.songFile = new File(songPath);
    }

    public Song(String name,String rawName, int trackNumber, SongDuration duration, Artist artist, Album album, String songPath) {
        this.name = name;
        this.rawName = rawName;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.songFile = new File(songPath);
    }

    private Song(Builder builder) {
        songId = builder.songId;
        setName(builder.name);
        setYear(builder.year);
        setTrackNumber(builder.trackNumber);
        setDuration(builder.duration);
        artistId = builder.artistId;
        albumId = builder.albumId;
        setSongFile(builder.songFile);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRawName(){return rawName;}

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

    public File getSongFile() { return songFile; }
    public void setSongFile(File songFile) {  this.songFile = songFile; }


    public static final class Builder {
        private int songId;
        private String name;
        private int year;
        private int trackNumber;
        private SongDuration duration;
        private int artistId;
        private int albumId;
        private String albumName;
        private String artistName;
        private File songFile;

        public Builder() {
        }

        public Builder songId(int val) {
            songId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder year(int val) {
            year = val;
            return this;
        }

        public Builder trackNumber(int val) {
            trackNumber = val;
            return this;
        }

        public Builder duration(SongDuration val) {
            duration = val;
            return this;
        }

        public Builder artistId(int val) {
            artistId = val;
            return this;
        }

        public Builder albumId(int val) {
            albumId = val;
            return this;
        }

        public Builder artistName(String val) {
           artistName = val;
           return this;
        }

        public Builder albumName(String val) {
            albumName = val;
            return this;
        }

        public Builder songFile(File val) {
            songFile = val;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }
}
