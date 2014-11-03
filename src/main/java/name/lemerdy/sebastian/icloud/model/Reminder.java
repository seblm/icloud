package name.lemerdy.sebastian.icloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import name.lemerdy.sebastian.icloud.graph.ICloudDateTimeDeserializer;

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

    @Override
    public String toString() {
        return "Reminder{" +
                "guid='" + guid + '\'' +
                ", createdDate=" + createdDate +
                ", completedDate=" + completedDate +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reminder reminder = (Reminder) o;

        if (completedDate != null ? !completedDate.equals(reminder.completedDate) : reminder.completedDate != null)
            return false;
        if (!createdDate.equals(reminder.createdDate)) return false;
        if (!guid.equals(reminder.guid)) return false;
        if (!title.equals(reminder.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guid.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + (completedDate != null ? completedDate.hashCode() : 0);
        result = 31 * result + title.hashCode();
        return result;
    }
}
