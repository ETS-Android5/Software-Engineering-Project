package comp3350.breadtunes.application;

import comp3350.breadtunes.persistence.*;
import comp3350.breadtunes.persistence.stubs.*;

public class Services
{
	private static SongPersistence songPersistence = null;
    private static AlbumPersistence albumPersistence = null;
    private static ArtistPersistence artistPersistence = null;

    public static synchronized SongPersistence getSongPersistence()
    {
        if (songPersistence == null)
        {
            songPersistence = new SongPersistenceStub();
        }

        return songPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence()
    {
        if (albumPersistence == null)
        {
            albumPersistence = new AlbumPersistenceStub();
        }

        return albumPersistence;
    }

    public static synchronized ArtistPersistence getArtistPersistence()
    {
        if (artistPersistence == null)
        {
            artistPersistence = new ArtistPersistenceStub();
        }

        return artistPersistence;
    }
}
