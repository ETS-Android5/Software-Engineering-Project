package comp3350.breadtunes.tests.business;

import org.junit.Test;
import static junit.framework.Assert.*;

import comp3350.breadtunes.business.StringHasher;
import comp3350.breadtunes.tests.watchers.TestLogger;

public class StringHasherTest extends TestLogger {
    @Test
    public void stringHashedTest() {
        String test = "Sample String";

        String hashedTest = StringHasher.sha256HexHash(test);

        // Just test that the string was changed
        assertFalse(test.equals(hashedTest));
    }
}
