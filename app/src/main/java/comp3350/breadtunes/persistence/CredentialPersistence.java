package comp3350.breadtunes.persistence;

import comp3350.breadtunes.objects.SecureCredentials;

public interface CredentialPersistence {
    SecureCredentials getMostRecentCredentials();
    void insertNewCredentials(SecureCredentials newCredentials);
}
