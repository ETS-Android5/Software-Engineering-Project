package comp3350.breadtunes.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private final String format = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat dateFormatter;

    public DateTimeHelper() {
       dateFormatter = new SimpleDateFormat(this.format);
    }

    public Date parseTimeString(String dateString) throws ParseException {
        return dateFormatter.parse(dateString);
    }

    public String dateToString(Date date) {
        return dateFormatter.format(date);
    }
}
