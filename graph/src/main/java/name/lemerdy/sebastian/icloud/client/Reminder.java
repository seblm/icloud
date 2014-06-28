package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.ZonedDateTime;

public class Reminder {
    public final String guid;

    @JsonDeserialize(using = ICloudDateTimeDeserializer.class)
    public final ZonedDateTime createdDate;

    @JsonDeserialize(using = ICloudDateTimeDeserializer.class)
    public final ZonedDateTime completedDate;

    public final String title;

    public Reminder(@JsonProperty("guid") String guid,
                    @JsonProperty("createdDate") ZonedDateTime createdDate,
                    @JsonProperty("completedDate") ZonedDateTime completedDate,
                    @JsonProperty("title") String title) {
        this.guid = guid;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.title = title;
    }
}
