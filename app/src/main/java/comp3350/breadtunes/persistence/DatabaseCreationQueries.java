package comp3350.breadtunes.persistence;

public class DatabaseCreationQueries {
    public static String[] createDb = {
            "CREATE MEMORY TABLE PUBLIC.SONGS(SONGID INTEGER NOT NULL PRIMARY KEY, NAME VARCHAR(256), YEAR INTEGER, TRACK INTEGER, DURATION VARCHAR(256), ARTISTID INTEGER, ARTISTNAME VARCHAR(256), ALBUMID INTEGER, ALBUMNAME VARCHAR(256), URI VARCHAR(256), FLAGGED BOOLEAN);",
            "CREATE MEMORY TABLE PUBLIC.CREDENTIALS(CID INTEGER IDENTITY PRIMARY KEY, PIN CHAR(64) NOT NULL, SECURITYQUESTION VARCHAR(80) NOT NULL, SECURITYQUESTIONANS CHAR(64) NOT NULL, DATEUPDATED VARCHAR(64));",
            "INSERT INTO SONGS VALUES (-10, 'Haydn Adagio', 2000, 1, 'H:0M:3S:1', -10, 'Joseph Haydn', -10, 'Classical Tunes', 'android.resource://comp3350.breadtunes/raw/adagio', false);",
            "INSERT INTO SONGS VALUES (-11, 'Jarabe Tapatio', 2000, 1, 'H:0M:3S:3', -11, 'Mexico', -11, 'Mexican Tunes', 'android.resource://comp3350.breadtunes/raw/jarabe', false);",
            "INSERT INTO SONGS VALUES (-12, 'Nocturne', 2000, 1, 'H:0M:3S:5', -12, 'Frederic Chopin', -12, 'Classical Tunes', 'android.resource://comp3350.breadtunes/raw/nocturne', false);",
            "INSERT INTO SONGS VALUES (-13, 'Bloch Prayer', 2000, 1, 'H:0M:3S:6', -13, 'Ernest Bloch', -13, 'Classical Tunes', 'android.resource://comp3350.breadtunes/raw/prayer', false);"
    };
}
