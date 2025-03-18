package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtility {
    public static String getCurrentDateTimeInFormat(String expectedFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(expectedFormat);
        LocalDateTime today = LocalDateTime .now();
        return today.format(formatter);
    }
}
