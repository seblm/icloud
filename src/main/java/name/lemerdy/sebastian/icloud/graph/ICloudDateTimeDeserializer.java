package name.lemerdy.sebastian.icloud.graph;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

public class ICloudDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        jp.nextToken();
        final ZonedDateTime zonedDateTime = new ICloudDateTime(jp.getText(),
                jp.nextIntValue(2014), jp.nextIntValue(1), jp.nextIntValue(1),
                jp.nextIntValue(0), jp.nextIntValue(0), jp.nextIntValue(0)).toZonedDateTime();
        jp.nextToken();
        return zonedDateTime;
    }
}
