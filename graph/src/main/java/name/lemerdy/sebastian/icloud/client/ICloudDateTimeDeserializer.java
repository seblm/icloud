package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ICloudDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        jp.nextToken();
        return new ICloudDate(jp.nextTextValue(),
                jp.nextIntValue(2014), jp.nextIntValue(1), jp.nextIntValue(1),
                jp.nextIntValue(0), jp.nextIntValue(0), jp.nextIntValue(0)).toZonedDateTime();
    }
}
