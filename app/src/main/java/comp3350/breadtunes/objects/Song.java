package comp3350.breadtunes.objects;

import android.net.Uri;

public class Song {
    private int songId;
    private String name;
    private int year = Integer.MIN_VALUE;
    private int trackNumber;
    private SongDuration duration;
    private int artistId;
    private String artistName;
    private String albumName;
    private String rawName;
    private int albumId;
    private Uri songUri;

    public Song(){
    }

    private Song(Builder builder) {
        songId = builder.songId;
        name = builder.name;
        rawName = builder.rawName;
        year = builder.year;
        trackNumber = builder.trackNumber;
        duration = builder.duration;
        artistId = builder.artistId;
        albumId = builder.albumId;
        albumName = builder.albumName;
        artistName = builder.artistName;
        songUri = builder.songUri;
    }

    // Getters only, disallow changing Song information
    public int getSongId() { return songId; }
    public String getName() { return name; }
    public String getRawName() { return rawName; }
    public int getYear() { return year; }
    public int getTrackNumber() { return trackNumber; }
    public SongDuration getDuration() { return duration; }
    public Uri getSongUri() { return songUri; }
    public int getArtistId() { return this.artistId; }
    public String getArtistName() { return this.artistName; }
    public int getAlbumId() { return this.albumId; }
    public String getAlbumName() { return this.albumName; }

    public static final class Builder {
        private int songId;
        private String name;
        private String rawName;
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

        public Builder rawName(String val) {
            rawName = val;
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
