package name.lemerdy.sebastian.icloud.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lemerdy.sebastian.icloud.client.model.LoginResponse;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

public class Session {
    private final URL loginURL;

    private boolean logged = false;
    private URL remindersURL;

    public Session(URL serverURL) {
        try {
            this.loginURL = new URL(serverURL, "/setup/ws/1/login");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getRemindersURL() {
        if (!isLogged()) {
            throw new IllegalStateException("not logged");
        }
        return remindersURL;
    }

    public void login(String appleId, String password) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) loginURL.openConnection();
            String payload = format("{\"apple_id\":\"%s\",\"password\":\"%s\",\"extended_login\":false}", appleId, password);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(payload.getBytes("UTF-8").length));
            connection.setRequestProperty("Origin", "https://www.icloud.com");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try (DataOutputStream requestContent = new DataOutputStream(connection.getOutputStream())) {
                requestContent.writeUTF(payload);
            }

            LoginResponse loginResponse = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(connection.getInputStream(), LoginResponse.class);
            this.remindersURL = loginResponse.webservices.reminders.url;
            this.logged = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.logged = false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public boolean isLogged() {
        return logged;
    }
}
