package net.snortum.spotmusic.view;

import net.snortum.spotmusic.model.Data;

import static net.snortum.spotmusic.model.GlobalData.CLIENT_ID;

public class AuthorizationView {
    private final Data data;

    public AuthorizationView(Data data) {
        this.data = data;
    }

    public void useThisLinkMessage() {
        System.out.println("use this link to request the access access to Spotify:");
        System.out.printf("%s/authorize?client_id=%s&redirect_uri=%s&response_type=code%n%n",
                data.getAccessURL(), CLIENT_ID, data.getRedirectURL());
        System.out.println("waiting for authorization...");
    }

    public void codeReceivedMessage() {
        System.out.println("authorization received");
        System.out.println("requesting access token...");
    }

    public void successMessage() {
        System.out.println("Success!");
    }

    public void alreadyAuthorized() {
        System.out.println("You are already authorized and ready to go!");
    }
}
