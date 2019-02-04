package comp3350.breadtunes.tests.business;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import comp3350.breadtunes.business.SongInfoReader;

import static org.junit.Assert.*;

public class SongInfoReaderTest
{

    @Test
    public void TestID3V1Read() {
        try {
            System.out.println("\nStarting TestID3V1Read");

            // Arrange
            URL mp3Url = getClass().getResource("/test_id3v1.mp3");
            File testMp3File = new File(mp3Url.getFile());

            // Act
            ID3v1 id3v1Tag = SongInfoReader.getID3v1Tag(testMp3File);

            // Assert
            assertNotNull(id3v1Tag);
            assertTrue(id3v1Tag.getTitle().equals("Four Taps"));
            assertTrue(id3v1Tag.getArtist().equals("Group 15 Breadtunes"));
            assertTrue(id3v1Tag.getAlbum().equals("COMP3350 Winter 2019"));
            assertTrue(id3v1Tag.getYear().equals("2019"));
            assertTrue(id3v1Tag.getTrack().equals("1"));

            System.out.println("Finished TestID3V1Read");
        } catch (Exception e) {
            fail("Exception thrown in ID3V1 read test");
        }
    }

    @Test
    public void TestID3V2Read() {
        try {
            System.out.println("\nStarting TestID3V2Read");

            // Arrange
            URL mp3Url = getClass().getResource("/test_id3v2.mp3");
            File testMp3File = new File(mp3Url.getFile());

            // Act
            ID3v2 id3v2Tag = SongInfoReader.getID3v2Tag(testMp3File);

            // Assert
            assertNotNull(id3v2Tag);
            assertTrue(id3v2Tag.getTitle().equals("Four Taps"));
            assertTrue(id3v2Tag.getArtist().equals("Group 15 Breadtunes"));
            assertTrue(id3v2Tag.getAlbum().equals("COMP3350 Winter 2019"));
            assertTrue(id3v2Tag.getYear().equals("2019"));
            assertTrue(id3v2Tag.getTrack().equals("1"));

            System.out.println("Finished TestID3V2Read");
        } catch (Exception e) {
            fail("Exception thrown in ID3V2 read test");
        }
    }
}
