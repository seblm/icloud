package name.lemerdy.sebastian.icloud.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;

public class Reminders {
    public final List<Reminder> reminders;

    public Reminders(@JsonProperty("Reminders") Reminder... reminders) {
        this.reminders = asList(reminders);
    }

    public Reminder getByGuid(String guid) {
        return reminders.stream()
                .filter(reminder -> guid.equals(reminder.guid))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public static Reminders readFromFile(String resourceName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            return mapper.readValue(Reminders.class.getResourceAsStream(resourceName), Reminders.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
