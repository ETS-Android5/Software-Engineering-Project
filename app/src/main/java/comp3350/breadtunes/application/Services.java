package comp3350.breadtunes.application;

import comp3350.breadtunes.persistence.SongPersistence;
import comp3350.breadtunes.persistence.stubs.SongPersistenceStub;

public class Services
{
	private static SongPersistence songPersistence = null;

    public static synchronized SongPersistence getSongPersistence()
    {
        if (songPersistence == null)
        {
            songPersistence = new SongPersistenceStub();
        }

        return songPersistence;
    }
}
