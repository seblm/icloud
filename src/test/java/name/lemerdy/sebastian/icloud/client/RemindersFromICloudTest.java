package name.lemerdy.sebastian.icloud.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import name.lemerdy.sebastian.icloud.model.Reminder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemindersFromICloudTest {
    private HttpServer iCloudStub;

    @Mock
    private Session session;

    @Before
    public void createHttpServer() throws IOException {
        iCloudStub = HttpServer.create(new InetSocketAddress(0), 0);
        iCloudStub.createContext("/rd/startup", httpExchange -> writeResponse(httpExchange, "{\n" +
                "\"Reminders\": [{\n" +
                "    \"title\": \"s'investir dans le tech event micro service\",\n" +
                "    \"createdDate\": [\n" +
                "        20140603,\n" +
                "        2014,\n" +
                "        6,\n" +
                "        3,\n" +
                "        7,\n" +
                "        14,\n" +
                "        434\n" +
                "    ],\n" +
                "    \"completedDate\": null,\n" +
                "    \"guid\": \"14B91359-3F5A-4FD2-800A-8E113BEDC51C\"\n" +
                "}\n" +
                "]\n" +
                "}"));
        iCloudStub.createContext("/rd/completed", httpExchange -> writeResponse(httpExchange, "{\"Reminders\": [{" +
                "\"title\": \"Remplir mon CRA\", " +
                "\"createdDate\": [20140704, 2014, 7, 4, 21, 8, 1268], " +
                "\"completedDate\": [20140704, 2014, 7, 4, 23, 8, 1388], " +
                "\"guid\": \"69D49AE8-926E-4095-A9DF-A4E3CECB2563\"" +
                "}]}"));
        iCloudStub.setExecutor(null);
        iCloudStub.start();
    }

    @After
    public void stopHttpServer() {
        iCloudStub.stop(0);
    }

    @Test
    public void should_retrieve_reminders() throws MalformedURLException {
        when(session.getRemindersURL()).thenReturn(new URL(format("http://localhost:%s", iCloudStub.getAddress().getPort())));
        RemindersFromICloud remindersFromICloud = new RemindersFromICloud(session);

        List<Reminder> reminders = remindersFromICloud.reminders();

        assertThat(reminders).containsExactly(
                new Reminder("14B91359-3F5A-4FD2-800A-8E113BEDC51C",
                        ZonedDateTime.of(2014, 6, 3, 7, 14, 0, 0, ZoneId.of("UTC")),
                        null,
                        "s'investir dans le tech event micro service"),
                new Reminder("69D49AE8-926E-4095-A9DF-A4E3CECB2563",
                        ZonedDateTime.of(2014, 7, 4, 21, 8, 0, 0, ZoneId.of("UTC")),
                        ZonedDateTime.of(2014, 7, 4, 23, 8, 0, 0, ZoneId.of("UTC")),
                        "Remplir mon CRA"));
    }

    private void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        byte[] responseAsArray = response.getBytes(Charset.forName("UTF-8"));
        httpExchange.sendResponseHeaders(200, responseAsArray.length);
        try (OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(responseAsArray);
        }
    }
}