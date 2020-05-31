package net.snortum.spotmusic.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snortum.spotmusic.model.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static net.snortum.spotmusic.model.GlobalData.*;

public class ClientServices {
    private final Data data;

    ClientServices(Data data) {
        this.data = data;
    }

    HttpRequest buildHttpRequest(String restURL) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + data.getAccessCode())
                .uri(URI.create(data.getApiURL() + restURL))
                .GET()
                .build();
    }

    Optional<HttpResponse<String>> getResponse(String restURL) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = buildHttpRequest(restURL);
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Problem sending an HTTP request");
            e.printStackTrace();
            return Optional.empty();
        }

        Optional<String> status = response.headers().firstValue(":status");
        Optional<String> message = response.headers().firstValue("message");

        if (status.isEmpty() || !STATUS_OK.equals(status.get())) {
            message.ifPresent(System.err::println);
            return Optional.empty();
        }

        return Optional.of(response);
    }

    boolean getAccess() {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = buildAccessHttpRequest();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Problem sending an HTTP request");
            e.printStackTrace();
            return false;
        }

        Optional<String> status = response.headers().firstValue(":status");
        Optional<String> message = response.headers().firstValue("message");

        if (status.isEmpty() || !STATUS_OK.equals(status.get())) {
            message.ifPresent(System.err::println);
            return false;
        }

        data.setAccessCode(parseAccessCode(response.body()));

        return true;
    }

    private HttpRequest buildAccessHttpRequest() {
        Base64.Encoder encoder = Base64.getEncoder();
        String stringToEncode = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedClientIdSecret = "Basic " + encoder.encodeToString(stringToEncode.getBytes());

        return HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", encodedClientIdSecret)
                .uri(URI.create(data.getAccessURL() + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code"
                        + String.format("&code=%s", data.getAuthCode())
                        + String.format("&redirect_uri=%s", data.getRedirectURL())))
                .build();
    }

    private String parseAccessCode(String jsonString) {
        JsonObject jo = JsonParser.parseString(jsonString).getAsJsonObject();
        return jo.get("access_token").getAsString();
    }

}
