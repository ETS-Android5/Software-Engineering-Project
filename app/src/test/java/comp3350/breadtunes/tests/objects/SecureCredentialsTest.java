package comp3350.breadtunes.tests.objects;

import org.junit.Test;

import java.util.Date;

import comp3350.breadtunes.business.StringHasher;
import comp3350.breadtunes.objects.SecureCredentials;
import comp3350.breadtunes.testhelpers.watchers.TestLogger;

import static junit.framework.Assert.*;

public class SecureCredentialsTest extends TestLogger {

    @Test
    public void secureCredentialsTest() {
        // Arrange
        String hashedPin = StringHasher.sha256HexHash("1212");
        String securityQuestion = "What is my middle name?";
        String securityQuestionAnswer = StringHasher.sha256HexHash("Bread");
        Date date = new Date();

        // Act
        SecureCredentials credentials = new SecureCredentials(hashedPin, securityQuestion, securityQuestionAnswer, date);

        // Assert
        assertEquals(credentials.getHashedPin(), hashedPin);
        assertEquals(credentials.getSecurityQuestion(), securityQuestion);
        assertEquals(credentials.getHashedSecurityQuestionAnswer(), securityQuestionAnswer);
        assertEquals(credentials.getDateUpdated(), date);
    }
}
