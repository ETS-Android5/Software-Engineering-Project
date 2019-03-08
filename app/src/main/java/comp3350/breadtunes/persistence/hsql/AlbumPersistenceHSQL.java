package comp3350.breadtunes.persistence.hsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.persistence.AlbumPersistence;
import comp3350.breadtunes.objects.Album;

public class AlbumPersistenceHSQL implements AlbumPersistence {

    private final String databasePath;

    public AlbumPersistenceHSQL(String databasePath) {
        this.databasePath = databasePath;
    }

    private Connection connection() throws SQLException {
        String connectionUrl = String.format("jdbc:hsqldb:file:%s;shutdown=true", databasePath);
        return DriverManager.getConnection(connectionUrl, "SA", "");
    }

    public List<Album> getAll() {
        return new ArrayList<>();
    }

    public Album insert(Album album) {
        return album;
    }

    public Album update(Album album) {
        return album;
    }

    public void delete(Album album) {

    }
}
