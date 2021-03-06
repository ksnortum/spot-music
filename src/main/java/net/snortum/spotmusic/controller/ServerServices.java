package net.snortum.spotmusic.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.ServerView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static net.snortum.spotmusic.model.GlobalData.PORT_NUMBER;

public class ServerServices {
    private HttpServer server;
    private final Data data;
    private final ServerView serverView;

    ServerServices(Data data) {
        this.data = data;
        serverView = new ServerView();
    }

    void startServer(CountDownLatch latch) {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(PORT_NUMBER), 0);
            server.createContext("/",
                    exchange -> {
                        URI uri = exchange.getRequestURI();
                        String query = uri.getQuery();
                        Map<String, String> queryMap = parseQuery(query);

                        if (queryMap.containsKey("code")) {
                            data.setAuthCode(queryMap.get("code"));
                            writeSuccessToBrowser(exchange);
                        } else if (queryMap.containsKey("error")) {
                            String error = queryMap.get("error");
                            System.err.printf("Return status is an error: %s%n", error);
                            data.setAuthCode("error");
                            writeErrorToBrowser(exchange);
                        } else if (query != null) {
                            System.err.println("Unknown query:");
                            System.err.println(query);
                            writeErrorToBrowser(exchange);
                        }

                        latch.countDown();
                    }
            );
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        if (server != null) {
            server.stop(1);
        }
    }

    Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();

        if (query != null) {
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                String[] part = pair.split("=");

                if (part.length >= 2) {
                    result.put(part[0], part[1]);
                }
            }
        }

        return result;
    }

    private void writeSuccessToBrowser(HttpExchange exchange) {
        String message = serverView.successHtml();
        writeToBrowser(exchange, message, 200);
    }

    private void writeErrorToBrowser(HttpExchange exchange) {
        String message = serverView.failureHtml();
        writeToBrowser(exchange, message, 400);
    }

    void writeToBrowser(HttpExchange exchange, String message, int code) {
        try {
            exchange.sendResponseHeaders(code, message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.getResponseBody().close();
        } catch(IOException e) {
            System.err.println("IO problem writing to browser");
            e.printStackTrace();
        }
    }

}
