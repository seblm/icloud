package name.lemerdy.sebastian.icloud.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ICloudDateTime {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public ICloudDateTime(String date, int year, int month, int day, int hour, int minute, int minutesSinceMidnight) {
//        if (String.format("%4$d")) //  TODO check that date is correct
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        minutesSinceMidnight = minutesSinceMidnight; // TODO check that minutes since midnight is correct
    }

    public ZonedDateTime toZonedDateTime() {
        ZonedDateTime utc = ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZoneId.of("UTC"));
        return utc;
    }
}
