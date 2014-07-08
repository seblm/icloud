package name.lemerdy.sebastian.icloud.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    public final Webservices webservices;

    public LoginResponse(@JsonProperty("webservices") Webservices webservices) {
        this.webservices = webservices;
    }
}
