package comp3350.breadtunes.business;

import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;

public class SongInfoReader {

    public static ID3v1 getID3v1Tag(File song) throws IOException, UnsupportedTagException, InvalidDataException{
        Mp3File file = new Mp3File(song);

        if (file.hasId3v1Tag()) {
            return file.getId3v1Tag();
        } else {
            return null;
        }
    }

    public static ID3v2 getID3v2Tag(File song) throws IOException, UnsupportedTagException, InvalidDataException{
        Mp3File file = new Mp3File(song);

        if (file.hasId3v2Tag()) {
            return file.getId3v2Tag();
        } else {
            return null;
        }
    }
}
