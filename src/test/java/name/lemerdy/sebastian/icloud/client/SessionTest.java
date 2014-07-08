package name.lemerdy.sebastian.icloud.client;

import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    private HttpServer iCloudStub;

    public static void main(String[] args) throws IOException {
        SessionTest sessionTest = new SessionTest();

        sessionTest.createHttpServer();
    }

    @Before
    public void createHttpServer() throws IOException {
        iCloudStub = HttpServer.create(new InetSocketAddress(8080), 0);
        iCloudStub.createContext("/setup/ws/1/login", httpExchange -> {
            try (BufferedReader request = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {
                String currentLine = null;
                while ((currentLine = request.readLine()) != null) {
                    System.out.format("request: %s%n", currentLine);
                }
            }
            byte[] response = ("{" +
                    "\"isExtendedLogin\":false," +
                    "\"webservices\":{" +
                    "\"reminders\":{\"status\":\"active\",\"url\":\"https://p05-remindersws.icloud.com:443\"}," +
                    "\"ubiquity\":{\"status\":\"active\",\"url\":\"https://p05-ubiquityws.icloud.com:443\"}," +
                    "\"iwmb\":{\"status\":\"active\",\"url\":\"https://p05-iwmb.icloud.com:443\"}," +
                    "\"account\":{\"iCloudEnv\":{\"shortId\":\"p\"},\"status\":\"active\",\"url\":\"https://p05-setup.icloud.com:443\"}," +
                    "\"streams\":{\"status\":\"active\",\"url\":\"https://p05-streams.icloud.com:443\"}," +
                    "\"keyvalue\":{\"status\":\"active\",\"url\":\"https://p05-keyvalueservice.icloud.com:443\"}," +
                    "\"push\":{\"status\":\"active\",\"url\":\"https://p05-pushws.icloud.com:443\"}," +
                    "\"findme\":{\"status\":\"active\",\"url\":\"https://p05-fmipweb.icloud.com:443\"}," +
                    "\"contacts\":{\"status\":\"active\",\"url\":\"https://p05-contactsws.icloud.com:443\"}," +
                    "\"calendar\":{\"status\":\"active\",\"url\":\"https://p05-calendarws.icloud.com:443\"}" +
                    "}," +
                    "\"dsInfo\":{\"primaryEmailVerified\":true,\"lastName\":\"Le Merdy\",\"iCloudAppleIdAlias\":\"\",\"appleIdAlias\":\"\",\"hasICloudQualifyingDevice\":true,\"isPaidDeveloper\":false,\"appleId\":\"sebastian.lemerdy@gmail.com\",\"gilligan-invited\":\"true\",\"gilligan-enabled\":\"true\",\"dsid\":\"157245782\",\"primaryEmail\":\"sebastian.lemerdy@gmail.com\",\"statusCode\":2,\"aDsID\":\"sebastian.lemerdy@gmail.com\",\"locked\":false,\"fullName\":\"Sébastian Le Merdy\",\"firstName\":\"Sébastian\",\"appleIdAliases\":[]}," +
                    "\"appsOrder\":[\"mail\",\"contacts\",\"calendar\",\"notes\",\"reminders\",\"find\",\"pages\",\"numbers\",\"keynote\"]," +
                    "\"apps\":{\"mail\":{},\"reminders\":{},\"numbers\":{\"isQualifiedForBeta\":true},\"pages\":{\"isQualifiedForBeta\":true},\"notes\":{},\"find\":{},\"contacts\":{},\"calendar\":{},\"keynote\":{\"isQualifiedForBeta\":true}}," +
                    "\"requestInfo\":{\"timeZone\":\"GMT+1\",\"country\":\"FR\"}," +
                    "\"version\":1" +
                    "}").getBytes(Charset.forName("UTF-8"));
            httpExchange.sendResponseHeaders(200, response.length);
            try (OutputStream responseBody = httpExchange.getResponseBody()) {
                responseBody.write(response);
            }
        });
        iCloudStub.setExecutor(null);
        iCloudStub.start();
    }

    @After
    public void stopHttpServer() {
        iCloudStub.stop(0);
    }

    @Test
    public void should_login() throws MalformedURLException {
        Session session = new Session(new URL("http://localhost:8080"));

        session.login("my_email@provider.net", "passw0rd");

        assertThat(session.isLogged()).isTrue();
    }
}