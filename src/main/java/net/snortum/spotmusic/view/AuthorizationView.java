package net.snortum.spotmusic.view;

import net.snortum.spotmusic.model.Data;

import static net.snortum.spotmusic.model.GlobalData.CLIENT_ID;

public class AuthorizationView {
    private final Data data;

    public AuthorizationView(Data data) {
        this.data = data;
    }

    public void useThisLinkMessage() {
        System.out.println("use this link to request the access code:");
        System.out.printf("%s/authorize?client_id=%s&redirect_uri=%s&response_type=code%n%n",
                data.getAccessURL(), CLIENT_ID, data.getRedirectURL());
        System.out.println("waiting for code...");
    }

    public void codeReceivedMessage() {
        System.out.println("code received");
        System.out.println("Making http request for access_token...");
    }

    public void successMessage() {
        System.out.println("Success!");
    }
}
