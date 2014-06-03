package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ReminderGraph {
    private final Reminders reminders;

    public ReminderGraph(String resourceName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            reminders = mapper.readValue(this.getClass().getResourceAsStream(resourceName), Reminders.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Reminder getByGuid(String guid) {
        return reminders.reminders.stream()
                .filter(reminder -> guid.equals(reminder.guid))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
