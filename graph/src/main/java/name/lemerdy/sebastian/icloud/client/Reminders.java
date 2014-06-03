package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Reminders {
    public final List<Reminder> reminders;

    public Reminders(@JsonProperty("Reminders") List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
