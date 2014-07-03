package name.lemerdy.sebastian.icloud.server.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;
import java.util.Map;

class FeatureWithObjectMapper implements FeatureContext {
    private final FeatureContext context;
    private final ObjectMapper mapper;

    public FeatureWithObjectMapper(FeatureContext context, ObjectMapper mapper) {
        this.context = context;
        this.mapper = mapper;
    }

    @Override
    public Configuration getConfiguration() {
        return context.getConfiguration();
    }

    @Override
    public FeatureContext property(String name, Object value) {
        return context.property(name, value);
    }

    @Override
    public FeatureContext register(Class<?> componentClass) {
        return context.register(componentClass);
    }

    @Override
    public FeatureContext register(Class<?> componentClass, int priority) {
        return context.register(componentClass, priority);
    }

    @Override
    public FeatureContext register(Class<?> componentClass, Class<?>... contracts) {
        if (componentClass.equals(JacksonJaxbJsonProvider.class)) {
            JacksonJaxbJsonProvider jacksonJaxbJsonWithMapper = new JacksonJaxbJsonProvider();
            jacksonJaxbJsonWithMapper.setMapper(mapper);
            return context.register(jacksonJaxbJsonWithMapper, contracts);
        }
        return context.register(componentClass, contracts);
    }

    @Override
    public FeatureContext register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        return context.register(componentClass, contracts);
    }

    @Override
    public FeatureContext register(Object component) {
        return context.register(component);
    }

    @Override
    public FeatureContext register(Object component, int priority) {
        return context.register(component, priority);
    }

    @Override
    public FeatureContext register(Object component, Class<?>... contracts) {
        return context.register(component, contracts);
    }

    @Override
    public FeatureContext register(Object component, Map<Class<?>, Integer> contracts) {
        return context.register(component, contracts);
    }
}
