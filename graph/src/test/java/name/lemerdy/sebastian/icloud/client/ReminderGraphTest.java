package name.lemerdy.sebastian.icloud.client;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReminderGraphTest {
    @Test
    public void should_get_reminder_by_guid() {
        Reminder reminder = new ReminderGraph("/reminders.json").getByGuid("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");

        assertThat(reminder.guid).isEqualTo("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1");
    }
}