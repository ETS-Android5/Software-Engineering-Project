package comp3350.breadtunes.persistence.hsql;

import android.net.Uri;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.breadtunes.exception.PersistenceException;
import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.persistence.SongPersistence;
import comp3350.breadtunes.objects.Song;

public class SongPersistenceHSQL implements SongPersistence {

    private final String databasePath;

    public SongPersistenceHSQL(String databasePath) {
        this.databasePath = databasePath;

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connection() throws SQLException {
        String connectionUrl = String.format("jdbc:hsqldb:file:%s;shutdown=true", databasePath);
        return DriverManager.getConnection(connectionUrl, "SA", "");
    }

    private Song getSongFromResultSet(ResultSet resultSet) throws SQLException {
        return new Song.Builder()
                .songId(resultSet.getInt("SongID"))
                .name(resultSet.getString("Name"))
                .year(resultSet.getInt("Year"))
                .trackNumber(resultSet.getInt("Track"))
                .duration(new SongDuration(resultSet.getString("Duration")))
                .artistId(resultSet.getInt("ArtistID"))
                .artistName(resultSet.getString("ArtistName"))
                .albumId(resultSet.getInt("AlbumID"))
                .albumName(resultSet.getString("AlbumName"))
                .songUri(Uri.parse(resultSet.getString("Uri")))
                .build();
    }

    public List<Song> getAll() {
        final List<Song> songList = new ArrayList<>();

        try (final Connection dbConnection = connection()) {
            final PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM Songs ORDER BY Name ASC");
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Song song = getSongFromResultSet(resultSet);
                songList.add(song);
            }

            resultSet.close();
            statement.close();

            return songList;
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public Song insert(Song song) {
        try (final Connection dbConnection = connection()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO Songs (SongId, Name, Year, Track, Duration, ArtistId, ArtistName, AlbumId, AlbumName, URI) ");
            queryBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            final PreparedStatement statement = dbConnection.prepareStatement(queryBuilder.toString());
            statement.setInt(1, song.getSongId());
            statement.setString(2, song.getName());
            statement.setInt(3, song.getYear());
            statement.setInt(4, song.getTrackNumber());
            statement.setString(5, song.getDuration().toString());
            statement.setInt(6, song.getArtistId());
            statement.setString(7, song.getArtistName());
            statement.setInt(8, song.getAlbumId());
            statement.setString(9, song.getAlbumName());
            statement.setString(10, song.getSongUri().toString());
            statement.executeUpdate();

            statement.close();

            return song;
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public void insertSongs(List<Song> songs) {
        try (final Connection dbConnection = connection()) {
            dbConnection.setAutoCommit(false);

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO Songs (SongId, Name, Year, Track, Duration, ArtistId, ArtistName, AlbumId, AlbumName, URI) ");
            queryBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            final PreparedStatement statement = dbConnection.prepareStatement(queryBuilder.toString());

            int i = 0;
            for (Song song : songs) {
                statement.setInt(1, song.getSongId());
                statement.setString(2, song.getName());
                statement.setInt(3, song.getYear());
                statement.setInt(4, song.getTrackNumber());
                statement.setString(5, song.getDuration().toString());
                statement.setInt(6, song.getArtistId());
                statement.setString(7, song.getArtistName());
                statement.setInt(8, song.getAlbumId());
                statement.setString(9, song.getAlbumName());
                statement.setString(10, song.getSongUri().toString());

                statement.addBatch();
                i++;

                if (i % 1000 == 0 || i == songs.size()) {
                    statement.executeBatch(); // Execute every 1000 items.
                    dbConnection.commit();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    public boolean insertSongsNoDuplicates(List<Song> songs) {
        boolean databaseUpdated = false;

        final List<Song> songsDB = getAll();
        List<Song> songsToInsert = new ArrayList<>();

        for (Song song: songs) {
            boolean songIdMatchFound = false;

            // Check all songs in the database to see if the song is already inserted
            for (Song songDB: songsDB) {
                if (songDB.getSongId() == song.getSongId()) {
                    songIdMatchFound = true;
                    break;
                }
            }

            // If no match was found, add the song to list of songs to insert
            if (songIdMatchFound == false) {
                songsToInsert.add(song);
                databaseUpdated = true;
            }
        }

        insertSongs(songsToInsert);
        return databaseUpdated;
    }

    public Song update(Song song) {
        return song;
    }

    public void delete(Song song) {
    }
}
