package comp3350.breadtunes.business;

public class CredentialManager {


    //class in charge of writing into database new credentials (pin, secret question, secret q answer) when credentials setup first time


    //class in charge of validating if given pin matches the one in the database

    //class of handling on forgetting pin (dealing with secret question and secret answer)

    // class ifnroms if credentials have been setup or not

    public CredentialManager(){

    }

    public static boolean credentialsHaveBeenSet(){
        //query database and find out if credentials have been set or not
        return false;
    }

    public static void writeNewCredentials(String pin, String secretQ, String secretQAnswer){

    }

    public static boolean validatePIN(String pin){
        return false;
    }

    public static boolean validateSecretQuestionAswer(String answer){
        return false;
    }

    //used when "forgot password" and must answer secret question
    public static String getSecretQuestion(){
        return "";
    }


}
