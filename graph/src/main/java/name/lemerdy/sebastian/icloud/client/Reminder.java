package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.ZonedDateTime;

public class Reminder {
    public final String guid;

    @JsonDeserialize(using = ICloudDateTimeDeserializer.class)
    public final ZonedDateTime createdDate;

    public Reminder(@JsonProperty("guid") String guid,
                    @JsonProperty("createdDate") ZonedDateTime createdDate) {
        this.guid = guid;
        this.createdDate = createdDate;
    }
}
