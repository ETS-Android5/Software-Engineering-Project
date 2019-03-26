package comp3350.breadtunes.persistence.interfaces;

import comp3350.breadtunes.objects.SecureCredentials;

public interface CredentialPersistence {
    SecureCredentials getMostRecentCredentials();
    void insertNewCredentials(SecureCredentials newCredentials);
    void updateMostRecentCredentialsPin(String hashedPin);
}
