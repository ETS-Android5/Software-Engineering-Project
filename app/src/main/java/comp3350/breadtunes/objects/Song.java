package comp3350.breadtunes.objects;

import android.net.Uri;
import android.util.Log;

public class Song {
    private int songId;
    private String name;
    private int year = Integer.MIN_VALUE;
    private int trackNumber;
    private SongDuration duration;
    private int artistId;
    private String artistName;
    private String albumName;
    private int albumId;
    private Uri songUri;
    private boolean flaggedAsInnapropriate;

    public Song(){
    }

    private Song(Builder builder) {
        songId = builder.songId;
        name = builder.name;
        year = builder.year;
        trackNumber = builder.trackNumber;
        duration = builder.duration;
        artistId = builder.artistId;
        albumId = builder.albumId;
        albumName = builder.albumName;
        artistName = builder.artistName;
        songUri = builder.songUri;
        flaggedAsInnapropriate = builder.flaggedAsInappropriate;
    }

    // Getters only, disallow changing Song information
    public int getSongId() { return songId; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public int getTrackNumber() { return trackNumber; }
    public SongDuration getDuration() { return duration; }
    public Uri getSongUri() { return songUri; }
    public int getArtistId() { return this.artistId; }
    public String getArtistName() { return this.artistName; }
    public int getAlbumId() { return this.albumId; }
    public String getAlbumName() { return this.albumName; }
    public boolean getFlaggedStatus() { return this.flaggedAsInnapropriate; }

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
        private boolean flaggedAsInappropriate;

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

        public Builder flaggedAsInappropriate(boolean val) {
            flaggedAsInappropriate = val;
            return this;
        }
        
        public Song build() {
            return new Song(this);
        }

    }

    public boolean equals(Object object){
        if (!(object instanceof Song)) {
            return false;
        }

        Song otherSongObject = (Song) object;
        return this.songId == otherSongObject.getSongId();
    }
}
