package name.lemerdy.sebastian.icloud.client;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ICloudDateTimeTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_check_date() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("first date parameter 19811224 doesn't match with second year parameter 1982, third month parameter 2 and fourth day parameter 24");

        new ICloudDateTime("19811224", 1982, 2, 24, 9, 30, 570);
    }

    @Test
    public void should_check_minutes_since_midnight() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("minutes since midnight from hour and minutes parameters 570 doesn't match last minutes since midnight parameter 569");

        new ICloudDateTime("19811224", 1981, 12, 24, 9, 30, 569);
    }
}