package net.snortum.spotmusic.controller;

import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.AuthorizationView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AuthorizationController {
    private static final int TIMEOUT_IN_SECONDS = 60;

    private final Data data;
    private final ServerServices serverServices;
    private final ClientServices clientServices;
    private final AuthorizationView authorizationView;

    AuthorizationController(Data data) {
        this.data = data;
        serverServices = new ServerServices(data);
        clientServices = new ClientServices(data);
        authorizationView = new AuthorizationView(data);
    }

    void getAuthorization() {
        if (data.isAuthorized()) {
            authorizationView.alreadyAuthorized();
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        serverServices.startServer(latch);
        authorizationView.useThisLinkMessage(TIMEOUT_IN_SECONDS);

        try {
            latch.await(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Do nothing
        }

        if ("".equals(data.getAuthCode()) || "error".equals(data.getAuthCode())) {
            System.err.println("Could not authorize user");
            data.setAuthCode("");
            serverServices.stopServer();
            return;
        }

        authorizationView.codeReceivedMessage();

        if (clientServices.getAccess()) {
            authorizationView.successMessage();
            data.setAuthorized(true);
        }

        serverServices.stopServer();
    }

}
