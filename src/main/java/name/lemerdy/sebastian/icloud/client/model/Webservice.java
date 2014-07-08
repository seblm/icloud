package name.lemerdy.sebastian.icloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;

public class Webservice {
    public final URL url;

    public Webservice(@JsonProperty("url") URL url) {
        this.url = url;
    }
}
