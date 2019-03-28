package comp3350.breadtunes.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import comp3350.breadtunes.business.CredentialManager;
import comp3350.breadtunes.business.StringHasher;
import comp3350.breadtunes.objects.SecureCredentials;
import comp3350.breadtunes.persistence.interfaces.CredentialPersistence;
import comp3350.breadtunes.tests.watchers.TestLogger;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

public class CredentialManagerTest extends TestLogger {
    CredentialPersistence mockCredentialPersistence = mock(CredentialPersistence.class);
    CredentialManager testTarget;

    String testPinHashed = StringHasher.sha256HexHash("0000");
    String testSecurityQuestion = "TEST QUESTION";
    String testAnswerHashed = StringHasher.sha256HexHash("ANS");

    @Before
    public void setup() {
        // Create a test date
        Calendar testCalendar = Calendar.getInstance();
        testCalendar.set(2010, 2, 1);
        Date testDate = testCalendar.getTime();

        SecureCredentials mockCredentials = new SecureCredentials(testPinHashed,
                testSecurityQuestion, testAnswerHashed, testDate);

        when(mockCredentialPersistence.getMostRecentCredentials()).thenReturn(mockCredentials);

        testTarget = new CredentialManager(mockCredentialPersistence);
    }

    @Test
    public void credentialsSetTest() {
        boolean credentialsSet = testTarget.credentialsHaveBeenSet();

        assertTrue(credentialsSet);
    }

    @Test
    public void credentialsNotSetTest() {
        when(mockCredentialPersistence.getMostRecentCredentials()).thenReturn(null);

        boolean credentialsSet = testTarget.credentialsHaveBeenSet();

        assertFalse(credentialsSet);
    }

    @Test
    public void writeNewCredentialsTest() {
        testTarget.writeNewCredentials("Pin", "Question", "Answer");

        verify(mockCredentialPersistence, times(1))
                .insertNewCredentials(any(SecureCredentials.class));
    }

    @Test
    public void validatePinOKTest() {
        boolean pinAccepted = testTarget.validatePIN("0000");

        assertTrue(pinAccepted);
    }

    @Test
    public void validatePinFAILTest() {
        boolean pinAccepted = testTarget.validatePIN("4444");

        assertFalse(pinAccepted);
    }

    @Test
    public void validateSecurityAnswerOKTest() {
        boolean answerAccepted = testTarget.validateSecretQuestionAnswer("ANS");

        assertTrue(answerAccepted);
    }

    @Test
    public void validateSecurityAnswerFAILTest() {
        boolean answerAccepted = testTarget.validateSecretQuestionAnswer("WHAT?");

        assertFalse(answerAccepted);
    }

    @Test
    public void getSecurityQuestionTest() {
        String question = testTarget.getSecretQuestion();

        assertEquals(question, "TEST QUESTION");
    }

    @Test
    public void setNewPinTest() {
        String newPin = "1919";
        String newPinHash = StringHasher.sha256HexHash(newPin);

        testTarget.updatePIN(newPin);

        verify(mockCredentialPersistence, times(1))
                .updateMostRecentCredentialsPin(newPinHash);
    }
}
