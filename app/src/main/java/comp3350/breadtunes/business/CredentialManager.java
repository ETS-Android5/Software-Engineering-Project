package comp3350.breadtunes.business;

import comp3350.breadtunes.business.StringHasher;

public class CredentialManager {


    //class in charge of writing into database new credentials (pin, secret question, secret q answer) when credentials setup first time


    //class in charge of validating if given pin matches the one in the database

    //class of handling on forgetting pin (dealing with secret question and secret answer)

    // class ifnroms if credentials have been setup or not

    public CredentialManager(){

    }

    public static boolean credentialsHaveBeenSet(){
        // Find most recent credential set in database.

        // If no entries, return false
        return false;

        // Else, return true
    }

    public static void writeNewCredentials(String pin, String secretQ, String secretQAnswer, DateTimeHelper dateTimeHelper) {
        String pinHashed = StringHasher.sha256HexHash(pin);
        String secretAnswerHashed = StringHasher.sha256HexHash(secretQAnswer);
        String currentTime = dateTimeHelper.getCurrentTimeString();

        // Insert information into database
    }

    public static boolean validatePIN(String pin) {
        String pinHashed = StringHasher.sha256HexHash(pin);

        // Get most recent pin from database

        // Compare pinHashed with value in database

        return false;
    }

    public static boolean validateSecretQuestionAswer(String answer) {
        String secretAnswerHashed = StringHasher.sha256HexHash(answer);

        // Get most recent secret question answer from database

        // Compare secretAnswerHashed with value in database

        return false;
    }

    //used when "forgot password" and must answer secret question
    public static String getSecretQuestion(){
        // Get most recent secret question from database

        return "";
    }


}
