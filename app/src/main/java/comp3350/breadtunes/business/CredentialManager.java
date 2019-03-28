package comp3350.breadtunes.business;

import java.util.Date;

import comp3350.breadtunes.objects.SecureCredentials;
import comp3350.breadtunes.persistence.interfaces.CredentialPersistence;
import comp3350.breadtunes.services.ServiceGateway;

public class CredentialManager {

    public CredentialManager() {

    }

    /**
     * Determine if there are any previous credentials in the database.
     *
     * @return true if there are existing credentials, false if not.
     */
    public boolean credentialsHaveBeenSet(){
        CredentialPersistence credentialPersistence = ServiceGateway.getCredentialPersistence();
        SecureCredentials credentials = credentialPersistence.getMostRecentCredentials();

        if (credentials == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Inserts a new set of hashed credentials into the database.
     *
     * @param pin The pin the user submitted, in plaintext.
     * @param secretQ The security question the user entered, in plaintext.
     * @param secretQAnswer The answer to the security question, in plaintext.
     */
    public void writeNewCredentials(String pin, String secretQ, String secretQAnswer) {
        // Create a new set of credentials
        String pinHashed = StringHasher.sha256HexHash(pin);
        String secretAnswerHashed = StringHasher.sha256HexHash(secretQAnswer);
        Date currentTime = new Date();
        SecureCredentials newCredentials = new SecureCredentials(pinHashed, secretQ, secretAnswerHashed, currentTime);

        // Insert the new credentials into the database
        CredentialPersistence credentialPersistence = ServiceGateway.getCredentialPersistence();
        credentialPersistence.insertNewCredentials(newCredentials);
    }

    /**
     * Determines whether the user answered the security question correctly.
     *
     * @param pin The pin the user entered and submitted, in plaintext.
     * @return true if the hashed pin matches the value in the database, false otherwise.
     */
    public boolean validatePIN(String pin) {
        // Hash the pin
        String pinHashed = StringHasher.sha256HexHash(pin);

        // Get most recent hashed pin from database
        CredentialPersistence credentialPersistence = ServiceGateway.getCredentialPersistence();
        SecureCredentials credentials = credentialPersistence.getMostRecentCredentials();
        String pinHashedStored = credentials.getHashedPin();

        // Compare pin to the database value
        if (pinHashed.equals(pinHashedStored)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines whether the user answered the security question correctly.
     *
     * @param answer The answer the user submitted for the security question, in plaintext.
     * @return true if the answer's hash matches the database value, false otherwise.
     */
    public boolean validateSecretQuestionAswer(String answer) {
        // Hash the security question answer
        String securityAnswerHashed = StringHasher.sha256HexHash(answer);

        // Get most recent secret question answer from database
        CredentialPersistence credentialPersistence = ServiceGateway.getCredentialPersistence();
        SecureCredentials credentials = credentialPersistence.getMostRecentCredentials();
        String securityAnswerHashedStored = credentials.getHashedSecurityQuestionAnswer();

        // Compare answer to the database value
        if (securityAnswerHashed.compareTo(securityAnswerHashedStored) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the security question for the current credentials.
     *
     * @return The security question for the most recent credentials in the database.
     */
    public String getSecretQuestion() {
        CredentialPersistence credentialPersistence = ServiceGateway.getCredentialPersistence();
        SecureCredentials credentials = credentialPersistence.getMostRecentCredentials();
        return credentials.getSecurityQuestion();
    }


    /**
     * Updates the pin for the most recent credential set to the new pin.
     *
     * @param newPIN The new pin for the credentials, in plaintext.
     */
    public void updatePIN(String newPIN) {
        String pinHashed = StringHasher.sha256HexHash(newPIN);
        CredentialPersistence credentialPersistence = ServiceGateway.getCredentialPersistence();
        credentialPersistence.updateMostRecentCredentialsPin(pinHashed);
    }


}
