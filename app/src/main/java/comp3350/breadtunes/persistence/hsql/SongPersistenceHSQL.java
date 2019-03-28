package comp3350.breadtunes.persistence.hsql;

import android.net.Uri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.breadtunes.exception.PersistenceException;
import comp3350.breadtunes.objects.SongDuration;
import comp3350.breadtunes.persistence.DatabaseManager;
import comp3350.breadtunes.persistence.interfaces.SongPersistence;
import comp3350.breadtunes.objects.Song;
import comp3350.breadtunes.services.ServiceGateway;

public class SongPersistenceHSQL implements SongPersistence {
    private DatabaseManager databaseManager;

    public SongPersistenceHSQL() {
        databaseManager = ServiceGateway.getDatabaseManager();
    }

    /**
     * Get all of the songs in the database.
     *
     * @return A list of all the songs.
     */
    public List<Song> getAll() {
        final List<Song> songList = new ArrayList<>();

        try {
            Connection dbConnection = databaseManager.getDbConnection();
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

    /**
     * Get all songs from the database that have not been flagged as inappropriate.
     *
     * @return A list of all the unflagged songs.
     */
    public List<Song> getAllNotFlagged() {
        final List<Song> songList = new ArrayList<>();

        try {
            Connection dbConnection = databaseManager.getDbConnection();
            final PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM Songs WHERE Flagged=FALSE ORDER BY Name ASC");
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

    /**
     * Inserts songs into the database without adding duplicates.
     *
     * @param songs The list of songs to add to the database.
     * @return true if database changed, false otherwise.
     */
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

    /**
     * Set the "flagged as inappropriate" status for a single song. Updates the song in the
     * database. Does not update the song if the flagged status already matches the status of the
     * song passed in.
     *
     * @param song The song to update.
     * @param isFlagged The flagged status to set for the song.
     */
    public void setSongFlagged(Song song, boolean isFlagged) {
        try {
            Connection dbConnection = databaseManager.getDbConnection();

            if (isSongFlagged(song) == isFlagged) {
                return;
            }

            String query = "UPDATE Songs SET Flagged=? WHERE SongID=?";
            final PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setBoolean(1, isFlagged);
            statement.setInt(2, song.getSongId());

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Check if a song is flagged in the database, return true if it is.
     * the song that will be looked for in the database
        @param song the song that will be looked up in the database
     */
    public boolean isSongFlagged(Song song){

        try{
            Connection dbConnection = databaseManager.getDbConnection();
            String query = "SELECT * FROM Songs WHERE URI=?";
            final PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1,song.getSongUri().toString());
            final ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                final Song songInDB = getSongFromResultSet(resultSet);
                return songInDB.getFlaggedStatus();
            }else{
                throw new PersistenceException("Failed to find song");
            }
        }catch(Exception e){
            throw new PersistenceException(e.getMessage());
        }

    }

    private void insertSongs(List<Song> songs) {
        try {
            Connection dbConnection = databaseManager.getDbConnection();
            dbConnection.setAutoCommit(false);

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO Songs (SongId, Name, Year, Track, Duration, ArtistId, ArtistName, AlbumId, AlbumName, URI, Flagged) ");
            queryBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
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
                statement.setBoolean(11, false);

                statement.addBatch();
                i++;

                if (i % 1000 == 0 || i == songs.size()) {
                    statement.executeBatch(); // Execute every 1000 items.
                    dbConnection.commit();
                }
            }

            dbConnection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
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
                .flaggedAsInappropriate(resultSet.getBoolean("Flagged"))
                .build();
    }

}
