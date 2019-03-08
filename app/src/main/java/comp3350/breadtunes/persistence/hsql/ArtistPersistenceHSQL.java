package comp3350.breadtunes.persistence.hsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.persistence.ArtistPersistence;
import comp3350.breadtunes.objects.Artist;

public class ArtistPersistenceHSQL implements ArtistPersistence {

    private final String databasePath;

    public ArtistPersistenceHSQL(String databasePath) {
        this.databasePath = databasePath;
    }

    private Connection connection() throws SQLException {
        String connectionUrl = String.format("jdbc:hsqldb:file:%s;shutdown=true", databasePath);
        return DriverManager.getConnection(connectionUrl, "SA", "");
    }

    public List<Artist> getAll() {
        return new ArrayList<>();
    }

    public Artist insert(Artist album) {
        return album;
    }

    public Artist update(Artist album) {
        return album;
    }

    public void delete(Artist album) {

    }
}
