package comp3350.breadtunes.persistence;

import java.util.List;

import comp3350.breadtunes.objects.Song;

public interface SongPersistence {
    List<Song> getAll();
    boolean insertSongsNoDuplicates(List<Song> song);
}
