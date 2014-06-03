package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class Reminder {
    public final String guid;

    public Reminder(@JsonProperty("guid") String guid) {
        this.guid = guid;
    }
}
