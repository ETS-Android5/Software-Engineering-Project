package comp3350.breadtunes.business.observables;

import java.util.Observable;

import comp3350.breadtunes.objects.Song;

public class SongObservable extends Observable {
    private Song song;

    public SongObservable() {
    }

    public void setSong(Song song) {
        this.song = song;
        setChanged();
        notifyObservers();
    }

    public Song getSong() {
       return song;
    }
}
