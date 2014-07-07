package name.lemerdy.sebastian.icloud.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lemerdy.sebastian.icloud.model.Reminder;
import name.lemerdy.sebastian.icloud.model.Reminders;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static name.lemerdy.sebastian.icloud.graph.Assertions.assertThat;

public class RemindersResourceTest {
    private static ZonedDateTime date(int day) {
        return ZonedDateTime.of(2014, 5, day, 0, 0, 0, 0, ZoneId.of("UTC"));
    }

    @Test
    public void one_reminder_not_stopped() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(
                new Reminder("1", date(19), null, null)));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[{\"guid\":\"1\",\"startX\":0,\"startY\":0,\"steps\":[1]}]");
    }

    @Test
    public void one_reminder_stopped() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(
                new Reminder("1", date(19), date(20), null)));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[{\"guid\":\"1\",\"startX\":0,\"startY\":0,\"steps\":[1]}]");
    }

    @Test
    public void should_create_computed_graph_with_two_reminders() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(
                new Reminder("1", date(19), date(21), null),
                new Reminder("2", date(20), date(22), null)));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[" +
                "{\"guid\":\"1\",\"startX\":0,\"startY\":0,\"steps\":[2]}," +
                "{\"guid\":\"2\",\"startX\":1,\"startY\":1,\"steps\":[1,1]}" +
                "]");
    }

    @Test
    public void should_create_computed_graph_with_two_reminders_seconds_ends_before_first() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(
                new Reminder("1", date(19), date(22), null),
                new Reminder("2", date(20), date(21), null)));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[" +
                "{\"guid\":\"1\",\"startX\":0,\"startY\":0,\"steps\":[3]}," +
                "{\"guid\":\"2\",\"startX\":1,\"startY\":1,\"steps\":[1]}" +
                "]");
    }

    @Test
    public void should_create_computed_graph_with_more_reminders() {
        Reminders reminders = new Reminders(
                new Reminder("1", date(19), date(22), null),
                new Reminder("2", date(20), date(24), null),
                new Reminder("3", date(21), date(26), null),
                new Reminder("4", date(23), date(25), null));
        try {
            System.out.println(new ObjectMapper().writeValueAsString(reminders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RemindersResource remindersResource = new RemindersResource(reminders);

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[" +
                "{\"guid\":\"1\",\"startX\":0,\"startY\":0,\"steps\":[3]}," +
                "{\"guid\":\"2\",\"startX\":1,\"startY\":1,\"steps\":[2,2]}," +
                "{\"guid\":\"3\",\"startX\":2,\"startY\":2,\"steps\":[1,2,2]}," +
                "{\"guid\":\"4\",\"startX\":4,\"startY\":2,\"steps\":[1,1]}" +
                "]");
    }
}