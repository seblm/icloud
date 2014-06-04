package name.lemerdy.sebastian.icloud.client;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ICloudDateTime {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public ICloudDateTime(String date, int year, int month, int day, int hour, int minute, int minutesSinceMidnight) {
        if (!String.format("%d%02d%02d", year, month, day).equals(date)) {
            throw new IllegalArgumentException(String.format("first date parameter %s doesn't match with second year parameter %d, third month parameter %d and fourth day parameter %d", date, year, month, day));
        }
        int computedMinutesSinceMidnight = hour * 60 + minute;
        if (computedMinutesSinceMidnight != minutesSinceMidnight) {
            throw new IllegalArgumentException(String.format("minutes since midnight from hour and minutes parameters %d doesn't match last minutes since midnight parameter %d", computedMinutesSinceMidnight, minutesSinceMidnight));
        }
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZoneId.of("UTC"));
    }
}
