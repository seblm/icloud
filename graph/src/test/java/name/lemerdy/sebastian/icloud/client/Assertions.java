package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.StringAssert;

import java.util.Set;

import static org.assertj.core.api.Fail.fail;

public class Assertions {
    public static StringAssert assertThat(Set<ReminderResourceResult> reminderResourceResults) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return org.assertj.core.api.Assertions.assertThat(mapper.writeValueAsString(reminderResourceResults));
        } catch (JsonProcessingException e) {
            fail("can't deserialize", e);
            throw new RuntimeException(e);
        }
    }
}
