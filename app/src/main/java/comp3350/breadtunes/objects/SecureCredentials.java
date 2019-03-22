package comp3350.breadtunes.objects;

import java.util.Comparator;
import java.util.Date;

public class SecureCredentials {
    private String hashedPin;
    private String securityQuestion;
    private String hashedSecurityQuestionAnswer;
    private Date dateUpdated;

    public SecureCredentials(String hashedPin, String securityQuestion, String hashedSecurityQuestionAnswer,
                             Date dateUpdated) {
        this.hashedPin = hashedPin;
        this.securityQuestion = securityQuestion;
        this.hashedSecurityQuestionAnswer = hashedSecurityQuestionAnswer;
        this.dateUpdated = dateUpdated;
    }

    public String getHashedPin() {
        return hashedPin;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getHashedSecurityQuestionAnswer() {
        return hashedSecurityQuestionAnswer;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public static class RecencyComparator implements Comparator<SecureCredentials> {
        @Override
        public int compare(SecureCredentials a, SecureCredentials b) {
            Date aDate = a.getDateUpdated();
            Date bDate = b.getDateUpdated();

            if (aDate == null && bDate == null) {
                return 0;
            } else if (aDate == null && bDate != null) {
                return 1;
            } else if (aDate != null && bDate == null) {
                return -1;
            } else {
                return a.getDateUpdated().compareTo(b.getDateUpdated());
            }
        }
    }
}
