package comp3350.breadtunes.persistence;

import java.util.List;

import comp3350.breadtunes.objects.Song;

public interface SongPersistence extends GeneralPersistence<Song> {
    boolean insertSongsNoDuplicates(List<Song> song);
}
