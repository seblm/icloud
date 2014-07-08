package name.lemerdy.sebastian.icloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Webservices {
    public final Webservice reminders;

    public Webservices(@JsonProperty("reminders") Webservice reminders) {
        this.reminders = reminders;
    }
}
