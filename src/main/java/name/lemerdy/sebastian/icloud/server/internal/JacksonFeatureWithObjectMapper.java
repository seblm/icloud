package name.lemerdy.sebastian.icloud.server.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.core.FeatureContext;

public class JacksonFeatureWithObjectMapper extends JacksonFeature {
    private final ObjectMapper mapper;

    public JacksonFeatureWithObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean configure(FeatureContext context) {
        return super.configure(new FeatureWithObjectMapper(context, mapper));
    }

}
