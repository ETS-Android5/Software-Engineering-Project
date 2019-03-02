package comp3350.breadtunes.persistence.stubs;

import java.util.Collections;
import java.util.List;

import comp3350.breadtunes.exception.RecordDoesNotExistException;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.persistence.SongPersistence;
import comp3350.breadtunes.persistence.mocks.*;

public class SongPersistenceStub implements SongPersistence {
    private List<Song> songs;

    public SongPersistenceStub() {
        this.songs = MockSongs.getMockSongList();
    }

    @Override
    public List<Song> getAll() {
        return Collections.unmodifiableList(songs);
    }

    @Override
    public Song insert(Song newSong) {
        // Not checking for duplicates
        songs.add(newSong);
        return newSong;
    }

    @Override
    public Song update(Song updateSong) {
        int index = songs.indexOf(updateSong);

        if (index < 0) {
            throw new RecordDoesNotExistException(String.format("Trying to update nonexistent song (name: %s).", updateSong.getName()));
        }

        songs.set(index, updateSong);

        return updateSong;
    }

    @Override
    public void delete(Song deleteSong) {
        int index = songs.indexOf(deleteSong);

        if (index < 0) {
            throw new RecordDoesNotExistException(String.format("Tying to delete nonexistent song (name: %s)", deleteSong.getName()));
        }

        songs.remove(index);
    }
}
