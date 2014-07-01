package name.lemerdy.sebastian.icloud.server;

import name.lemerdy.sebastian.icloud.graph.RemindersResource;
import name.lemerdy.sebastian.icloud.model.Reminders;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class RemindersApplication extends ResourceConfig {
    public RemindersApplication() {
        register(JacksonFeature.class)
                .register(new RemindersResource(Reminders.readFromFile("/completed.json")));
    }
}