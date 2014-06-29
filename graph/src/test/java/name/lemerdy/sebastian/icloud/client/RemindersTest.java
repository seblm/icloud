package name.lemerdy.sebastian.icloud.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class RemindersTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Reminders reminders;

    @Before
    public void createReminderGraph() throws Exception {
        reminders = Reminders.readFromFile("/reminders.json");
    }

    @Test
    public void should_get_reminder_by_guid() {
        Reminder reminder = reminders.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.guid).isEqualTo("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");
    }

    @Test
    public void should_fail_if_reminder_is_not_found() {
        thrown.expect(NoSuchElementException.class);

        reminders.getByGuid("N01F0UN0-GU10-0000-0000-000000000000");
    }

    @Test
    public void should_read_created_date() {
        Reminder reminder = reminders.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).isEqualTo("2014-05-19T04:45:00");
    }

    @Test
    public void should_read_completed_date() {
        Reminder reminder = reminders.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.completedDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).isEqualTo("2014-05-21T16:23:00");
    }

    @Test
    public void should_read_title() {
        Reminder reminder = reminders.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.title).isEqualTo("Lampes de chevet");
    }
}