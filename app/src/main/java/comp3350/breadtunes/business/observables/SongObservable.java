package comp3350.breadtunes.business.observables;

import java.util.Observable;

import comp3350.breadtunes.objects.Song;

public class SongObservable extends BreadTunesObservable<Song> {
    private Song song;

    public SongObservable() {
    }

    public void setValue(Song song) {
        this.song = song;
        setChanged();
        notifyObservers();
    }

    public Song getValue() {
       return song;
    }
}
