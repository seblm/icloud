package name.lemerdy.sebastian.icloud.graph;

import name.lemerdy.sebastian.icloud.model.Reminder;
import name.lemerdy.sebastian.icloud.model.Reminders;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static name.lemerdy.sebastian.icloud.graph.Assertions.assertThat;

public class RemindersResourceTest {
    @Test
    public void one_reminder_not_stopped() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(new Reminder("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1", ZonedDateTime.of(2014, 5, 19, 9, 30, 0, 0, ZoneId.of("UTC")), null, "Lampes de chevet")));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[{\"guid\":\"1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1\",\"startX\":0,\"startY\":0,\"steps\":[1]}]");
    }

    @Test
    public void one_reminder_stopped() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(new Reminder("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1", ZonedDateTime.of(2014, 5, 19, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 21, 9, 30, 0, 0, ZoneId.of("UTC")), "Lampes de chevet")));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[{\"guid\":\"1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1\",\"startX\":0,\"startY\":0,\"steps\":[1]}]");
    }

    @Test
    public void should_create_computed_graph_with_two_reminders() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(
                new Reminder("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1", ZonedDateTime.of(2014, 5, 19, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 21, 9, 30, 0, 0, ZoneId.of("UTC")), "Lampes de chevet"),
                new Reminder("2B7DF5C2-A9FE-56A0-BCB4-4412C54EAFD2", ZonedDateTime.of(2014, 5, 20, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 22, 9, 30, 0, 0, ZoneId.of("UTC")), "Remplir mon CRA")));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[" +
                "{\"guid\":\"1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1\",\"startX\":0,\"startY\":0,\"steps\":[2]}," +
                "{\"guid\":\"2B7DF5C2-A9FE-56A0-BCB4-4412C54EAFD2\",\"startX\":1,\"startY\":1,\"steps\":[1,1]}" +
                "]");
    }

    @Test
    public void should_create_computed_graph_with_more_reminders() {
        RemindersResource remindersResource = new RemindersResource(new Reminders(
                new Reminder("1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1", ZonedDateTime.of(2014, 5, 19, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 22, 9, 30, 0, 0, ZoneId.of("UTC")), "Lampes de chevet"),
                new Reminder("2B7DF5C2-A9FE-56A0-BCB4-4412C54EAFD2", ZonedDateTime.of(2014, 5, 20, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 24, 9, 30, 0, 0, ZoneId.of("UTC")), "Remplir mon CRA"),
                new Reminder("3C8E06D3-BA0F-67B1-CDC5-5523D65FB0E3", ZonedDateTime.of(2014, 5, 21, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 26, 9, 30, 0, 0, ZoneId.of("UTC")), "Encaisser les ch\\u00e8ques"),
                new Reminder("4D9F17E4-CB10-78C2-DED6-6634E760C1F4", ZonedDateTime.of(2014, 5, 23, 9, 30, 0, 0, ZoneId.of("UTC")), ZonedDateTime.of(2014, 5, 25, 9, 30, 0, 0, ZoneId.of("UTC")), "Payer l'h\\u00f4pital")));

        Set<ReminderResourceResult> reminderResourceResults = remindersResource.reminders();

        assertThat(reminderResourceResults).isEqualTo("[" +
                "{\"guid\":\"1A6CE4B1-98ED-459F-ABA3-3301B43D9EC1\",\"startX\":0,\"startY\":0,\"steps\":[3]}," +
                "{\"guid\":\"2B7DF5C2-A9FE-56A0-BCB4-4412C54EAFD2\",\"startX\":1,\"startY\":1,\"steps\":[2,2]}," +
                "{\"guid\":\"3C8E06D3-BA0F-67B1-CDC5-5523D65FB0E3\",\"startX\":2,\"startY\":2,\"steps\":[1,2,1,1]}," +
                "{\"guid\":\"4D9F17E4-CB10-78C2-DED6-6634E760C1F4\",\"startX\":4,\"startY\":2,\"steps\":[1,1]}" +
                "]");
    }
}