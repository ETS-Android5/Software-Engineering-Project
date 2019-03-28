package comp3350.breadtunes.tests.business;

import org.junit.Test;

import static junit.framework.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import comp3350.breadtunes.business.DateTimeHelper;
import comp3350.breadtunes.tests.watchers.TestLogger;

public class DateTimeHelperTest extends TestLogger {
    DateTimeHelper testTarget = new DateTimeHelper();

    @Test
    public void parseDateOKTest() {
        try {
            Date date = testTarget.parseTimeString("2019-02-02 01:00:00");
            assertNotNull(date);
        } catch (ParseException e) {
            fail("Parse exception was thrown");
        }
    }

    @Test
    public void parseDateFAILTest() {
        try {
            Date date = testTarget.parseTimeString("2019/01/01");
            fail("Parse exception should have been thrown");
        } catch (ParseException e) {
        }
    }

    @Test
    public void dateToStringTest() {
        // Create a test date
        Calendar testCalendar = Calendar.getInstance();
        testCalendar.set(2010, 2, 1);
        Date testDate = testCalendar.getTime();

        String dateString = testTarget.dateToString(testDate);

        assertNotNull(dateString);
        assertEquals(dateString.split(" ")[0], "2010-03-01");
    }
}
