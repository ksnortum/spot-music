package net.snortum.spotmusic.controller;

import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.AuthorizationView;

public class AuthorizationController {
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
            // TODO, print some message
            return;
        }

        serverServices.startServer();
        authorizationView.useThisLinkMessage();

        // Wait up to 30 seconds for server to respond
        // TODO, find a better way to do this
        int count = 0;
        while ("".equals(data.getAuthCode()) && count < 30) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Why did you interrupt my sleep?");
                e.printStackTrace();
            }

            count++;
        }

        if ("".equals(data.getAuthCode())) {
            System.err.println("Program not responding");
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
