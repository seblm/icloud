package name.lemerdy.sebastian.icloud.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import name.lemerdy.sebastian.icloud.graph.RemindersResource;
import name.lemerdy.sebastian.icloud.model.Reminders;
import name.lemerdy.sebastian.icloud.server.internal.JacksonFeatureWithObjectMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class RemindersApplication extends ResourceConfig {
    public RemindersApplication() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        this
                .register(new JacksonFeatureWithObjectMapper(mapper))
                .register(new RemindersResource(Reminders.readFromFile("/completed.json")));
    }
}