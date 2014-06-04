package name.lemerdy.sebastian.icloud.client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class ReminderGraphTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private ReminderGraph reminderGraph;

    @Before
    public void createReminderGraph() throws Exception {
        reminderGraph = new ReminderGraph("/reminders.json");
    }

    @Test
    public void should_get_reminder_by_guid() {
        Reminder reminder = reminderGraph.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.guid).isEqualTo("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");
    }

    @Test
    public void should_fail_if_reminder_is_not_found() {
        thrown.expect(NoSuchElementException.class);

        reminderGraph.getByGuid("N01F0UN0-GU10-0000-0000-000000000000");
    }

    @Test
    public void should_read_created_date() {
        Reminder reminder = reminderGraph.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).isEqualTo("2014-05-19T04:45:00");
    }
}