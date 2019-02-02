package comp3350.breadtunes.persistence;

import java.util.List;

import comp3350.breadtunes.exception.RecordDoesNotExistException;
import comp3350.breadtunes.objects.Song;

public interface SongPersistence {
    List<Song> getAllSongs();

    Song insertSong(Song currentCourse);

    Song updateSong(Song currentCourse) throws RecordDoesNotExistException;

    void deleteSong(Song currentCourse) throws RecordDoesNotExistException;
}
