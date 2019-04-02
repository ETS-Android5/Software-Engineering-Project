package comp3350.breadtunes.persistence.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import comp3350.breadtunes.business.DateTimeHelper;
import comp3350.breadtunes.exception.PersistenceException;
import comp3350.breadtunes.objects.SecureCredentials;
import comp3350.breadtunes.persistence.interfaces.CredentialPersistence;
import comp3350.breadtunes.persistence.DatabaseManager;
import comp3350.breadtunes.services.ServiceGateway;

public class CredentialPersistenceHSQL implements CredentialPersistence {
    private DatabaseManager databaseManager;
    public boolean credentialInserted;
    public CredentialPersistenceHSQL() {
        databaseManager = ServiceGateway.getDatabaseManager();
    }

    /**
     * Get the most recent credentials from the database, based on the DateUpdated field.
     *
     * @return The most recent credentials, if there are any. Returns null if there are no credentials.
     */
    public SecureCredentials getMostRecentCredentials() {
        try {
            List<SecureCredentials> credentialList = new ArrayList<>();

            Connection dbConnection = databaseManager.getDbConnection();
            final PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM Credentials;");
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SecureCredentials credentials = getCredentialsFromResultSet(resultSet);
                credentialList.add(credentials);
            }

            resultSet.close();
            statement.close();

            // Return null if the table is empty
            if (credentialList.isEmpty()) {
                return null;
            }

            // Sort credentials to have the most recent credentials at the start of the list
            Collections.sort(credentialList, new SecureCredentials.RecencyComparator());
            Collections.sort(credentialList, Collections.reverseOrder());
            return credentialList.get(0);

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Inserts a new set of credentials into the database.
     *
     * @param credentials The credentials, with pin and security question answer hashed (SHA256).
     */
    public void insertNewCredentials(SecureCredentials credentials) {
        try {
            Connection dbConnection = databaseManager.getDbConnection();

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO Credentials (Pin, SecurityQuestion, SecurityQuestionAns, DateUpdated) ");
            queryBuilder.append("VALUES (?, ?, ?, ?);");
            final PreparedStatement statement = dbConnection.prepareStatement(queryBuilder.toString());

            DateTimeHelper helper = new DateTimeHelper();

            statement.setString(1, credentials.getHashedPin());
            statement.setString(2, credentials.getSecurityQuestion());
            statement.setString(3, credentials.getHashedSecurityQuestionAnswer());
            statement.setString(4, helper.dateToString(credentials.getDateUpdated()));
            credentialInserted = true;
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    /**
     * Update the hashed pin for the most recent credentials.
     *
     * @param hashedPin The new hashed pin (SHA256).
     */
    public void updateMostRecentCredentialsPin(String hashedPin) {
        DateTimeHelper dth = new DateTimeHelper();

        try {
            SecureCredentials mostRecentCredentials = getMostRecentCredentials();

            // Update the PIN for the most recent credentials
            Connection dbConnection = databaseManager.getDbConnection();
            String getIdQuery = "UPDATE Credentials SET Pin=? WHERE Pin=? AND DateUpdated=?";
            final PreparedStatement statement = dbConnection.prepareStatement(getIdQuery);
            statement.setString(1, hashedPin);
            statement.setString(2, mostRecentCredentials.getHashedPin());
            statement.setString(3, dth.dateToString(mostRecentCredentials.getDateUpdated()));
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    private SecureCredentials getCredentialsFromResultSet(ResultSet resultSet) throws SQLException {
        DateTimeHelper dateTimeHelper = new DateTimeHelper();

        String hashedPin = resultSet.getString("Pin");
        String securityQuestion = resultSet.getString("SecurityQuestion");
        String securityQuestionAnswer = resultSet.getString("SecurityQuestionAns");
        String dateUpdatedNotParsed = resultSet.getString("DateUpdated");
        Date updatedDate = null;

        try {
            updatedDate = dateTimeHelper.parseTimeString(dateUpdatedNotParsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SecureCredentials(hashedPin, securityQuestion, securityQuestionAnswer, updatedDate);
    }
}
