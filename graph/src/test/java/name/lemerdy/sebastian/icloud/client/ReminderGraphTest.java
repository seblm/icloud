package name.lemerdy.sebastian.icloud.client;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

public class ReminderGraphTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_get_reminder_by_guid() {
        ReminderGraph reminderGraph = new ReminderGraph("/reminders.json");

        Reminder reminder = reminderGraph.getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.guid).isEqualTo("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");
    }

    @Test
    public void should_fail_if_reminder_is_not_found() {
        ReminderGraph reminderGraph = new ReminderGraph("/reminders.json");
        thrown.expect(NoSuchElementException.class);

        reminderGraph.getByGuid("N01F0UN0-GU10-0000-0000-000000000000");
    }
}