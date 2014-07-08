package name.lemerdy.sebastian.icloud.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

public class Session {
    private final URL loginURL;

    private boolean logged = false;

    public Session(URL serverURL) {
        try {
            this.loginURL = new URL(serverURL, "/setup/ws/1/login");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
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

            try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String currentLine;
                System.out.println("response :");
                while ((currentLine = rd.readLine()) != null) {
                    System.out.println(currentLine);
                }
            }
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
