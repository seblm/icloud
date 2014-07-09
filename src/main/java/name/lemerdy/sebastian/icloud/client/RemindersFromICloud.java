package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lemerdy.sebastian.icloud.model.Reminder;
import name.lemerdy.sebastian.icloud.model.Reminders;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class RemindersFromICloud {
    private final Session session;

    public RemindersFromICloud(Session session) {
        this.session = session;
    }

    public List<Reminder> reminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        reminders.addAll(getReminders("startup"));
        reminders.addAll(getReminders("completed"));
        return reminders;
    }

    private List<Reminder> getReminders(String type) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(session.getRemindersURL(), "/rd/" + type).openConnection();
            connection.setRequestProperty("Accept", "text/json");
            connection.setRequestProperty("Origin", "https://www.icloud.com");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(connection.getInputStream(), Reminders.class).reminders;
        } catch (Exception e) {
            e.printStackTrace();
            return emptyList();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
