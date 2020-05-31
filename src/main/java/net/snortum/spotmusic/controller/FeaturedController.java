package net.snortum.spotmusic.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.FeaturedView;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeaturedController {
    private final Data data;
    private final ClientServices clientServices;
    private final FeaturedView featuredView;

    FeaturedController(Data data) {
        this.data = data;
        clientServices = new ClientServices(data);
        featuredView = new FeaturedView(data);
    }

    void doFeaturedProcessing() {
        if (!data.isAuthorized()) {
            featuredView.notAuthorizedMessage();
            return;
        }

        Optional<HttpResponse<String>> response =
                clientServices.getResponse("/v1/browse/featured-playlists");

        if (response.isEmpty()) {
            return;
        }

        List<String> printLines = parseFeaturedData(response.get().body());
        featuredView.printLines(printLines);
    }

    private List<String> parseFeaturedData(String jsonString) {
        List<String> featuredData = new ArrayList<>();
        JsonObject top = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject playlists = top.getAsJsonObject("playlists");
        JsonArray items = playlists.getAsJsonArray("items");

        for (JsonElement item : items) {
            var itemObject = item.getAsJsonObject();
            var namePrimitive = itemObject.getAsJsonPrimitive("name");
            featuredData.add(namePrimitive.getAsString());

            var externalURLObject = itemObject.getAsJsonObject("external_urls");
            var spotifyURLPrimitive = externalURLObject.getAsJsonPrimitive("spotify");
            featuredData.add(spotifyURLPrimitive.getAsString());

            featuredData.add("");
        }

        return featuredData;
    }
}
