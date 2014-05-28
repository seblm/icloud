package name.lemerdy.sebastian.icloud.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Session {
    private final URL loginURL;
    private boolean logged;

    public Session(URL serverURL) {
        try {
            this.loginURL = new URL(serverURL, "/setup/ws/1/login?clientBuildNumber=14C.131972&clientId=9D9BEA3F-FCB9-4F0C-923C-3EB365F97BC7");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(String appleId, String password) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) loginURL.openConnection();
            String payload = "{\"apple_id\":\"myemail\",\"password\":\"mypassword\",\"extended_login\":false}";
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(payload.getBytes().length));
            connection.setRequestProperty("Origin", "https://www.icloud.com");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(payload);
            }

            //Get Response
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
