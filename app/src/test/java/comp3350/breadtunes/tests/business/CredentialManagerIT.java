package comp3350.breadtunes.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.business.StringHasher;
import comp3350.breadtunes.objects.SecureCredentials;
import comp3350.breadtunes.persistence.hsql.CredentialPersistenceHSQL;
import comp3350.breadtunes.presentation.Logger.Logger;
import comp3350.breadtunes.services.ServiceGateway;
import comp3350.breadtunes.testhelpers.values.BreadTunesIntegrationTests;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CredentialManagerIT extends TestLogger {
    CredentialPersistenceHSQL credentialPersistence;
    CredentialManager testTarget;
    SecureCredentials mockCredentials;

    String testPinHashed = StringHasher.sha256HexHash("0000");
    String testSecurityQuestion = "TEST QUESTION";
    String testAnswerHashed = StringHasher.sha256HexHash("ANS");

    @Before
    public void setup() {
        // Make DatabaseManager use a copy of the integration test database

        File realDirectory = new File(BreadTunesIntegrationTests.realDatabasePath);
        File copyDirectory = new File(BreadTunesIntegrationTests.copyDatabasePath);
        ServiceGateway.getDatabaseManager().initializeDatabase(realDirectory, mock(Logger.class));
        ServiceGateway.getDatabaseManager().createAndUseDatabaseCopy(copyDirectory);

        Calendar testCalendar = Calendar.getInstance();
        testCalendar.set(2010, 2, 1);
        Date testDate = testCalendar.getTime();
        mockCredentials = new SecureCredentials(testPinHashed,
               testSecurityQuestion, testAnswerHashed, testDate);

        // Create CredentialManager class
        credentialPersistence = new CredentialPersistenceHSQL();
        credentialPersistence.insertNewCredentials(mockCredentials);
        testTarget = new CredentialManager(credentialPersistence);

    }

    @Test
    public void credentialsNotSetTest() {
        boolean credentialsSet = testTarget.credentialsHaveBeenSet();

        assertFalse(!credentialsSet);
    }

    @Test
    public void credentialsSetTest() {
        assertTrue(testTarget.credentialsHaveBeenSet());
    }

    @Test
    public void writeNewCredentialsTest() {
        testTarget.writeNewCredentials("Pin", "Question", "Answer");

        assertTrue(credentialPersistence.credentialInserted);
    }

    @Test
    public void validatePinOKTest() {
        assertTrue(testTarget.validatePIN("0000"));
    }

    @Test
    public void validatePinFAILTest() {
        assertFalse(testTarget.validatePIN("4444"));
    }

    @Test
    public void validateSecurityAnswerOKTest() {
        assertTrue(testTarget.validateSecretQuestionAnswer("ANS"));
    }

    @Test
    public void validateSecurityAnswerFAILTest() {
        assertFalse(testTarget.validateSecretQuestionAnswer("WHAT?"));
    }

    @Test
    public void getSecurityQuestionTest() {
        assertEquals("TEST QUESTION", testTarget.getSecretQuestion());
    }

    @Test
    public void setNewPinTest() {
        String newPin = "1919";
        String newPinHash = StringHasher.sha256HexHash(newPin);

        testTarget.updatePIN(newPin);

        assertEquals(credentialPersistence.getMostRecentCredentials().getHashedPin(), newPinHash);
    }

    @After
    public void tearDown(){
        ServiceGateway.getDatabaseManager().destroyTempDatabaseAndCloseConnection();
    }
}
