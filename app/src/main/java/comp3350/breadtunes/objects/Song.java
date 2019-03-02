package comp3350.breadtunes.objects;

import android.net.Uri;

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
    private Uri songUri;


    public Song(){

    }
    public Song(String name, int trackNumber, SongDuration duration, Artist artist, Album album, Uri songUri) {
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.songUri = songUri;
    }

    public Song(String name, int trackNumber, SongDuration duration, Artist artist, Album album, String songPath) {
        this.name = name;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.songUri = new Uri.Builder().appendPath(songPath).build();
    }

    public Song(String name,String rawName, int trackNumber, SongDuration duration, Artist artist, Album album, String songPath) {
        this.name = name;
        this.rawName = rawName;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.songUri = new Uri.Builder().appendPath(songPath).build();
    }

    private Song(Builder builder) {
        songId = builder.songId;
        setName(builder.name);
        setYear(builder.year);
        setTrackNumber(builder.trackNumber);
        setDuration(builder.duration);
        artistId = builder.artistId;
        albumId = builder.albumId;
        albumName = builder.albumName;
        artistName = builder.artistName;
        setSongUri(builder.songUri);
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
    public int getArtistId() { return this.artistId; }
    public String getArtistName() { return this.artistName; }

    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
    public int getAlbumId() { return this.albumId; }
    public String getAlbumName() { return this.albumName; }

    public Uri getSongUri() { return songUri; }
    public void setSongUri(Uri songUri) {  this.songUri = songUri; }


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
        private Uri songUri;

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

        public Builder songUri(Uri val) {
            songUri = val;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }
}
