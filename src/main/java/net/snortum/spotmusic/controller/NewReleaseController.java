package net.snortum.spotmusic.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.NewReleaseView;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewReleaseController {
    private final Data data;
    private final ClientServices clientServices;
    private final NewReleaseView newReleaseView;

    NewReleaseController(Data data) {
        this.data = data;
        clientServices = new ClientServices(data);
        newReleaseView = new NewReleaseView(data);
    }

    void doNewReleasesProcessing() {
        if (!data.isAuthorized()) {
            newReleaseView.notAuthorizedMessage();
            return;
        }

        Optional<HttpResponse<String>> response =
                clientServices.getResponse("/v1/browse/new-releases");

        if (response.isEmpty()) {
            return;
        }

        List<String> lines = parseNewReleaseData(response.get().body());
        newReleaseView.printLines(lines);
    }

    private List<String> parseNewReleaseData(String jsonString) {
        List<String> newReleaseData = new ArrayList<>();
        JsonObject top = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject albums = top.getAsJsonObject("albums");
        JsonArray items = albums.getAsJsonArray("items");

        for (JsonElement item : items) {
            var itemObject = item.getAsJsonObject();
            var namePrimitive = itemObject.getAsJsonPrimitive("name");
            newReleaseData.add(namePrimitive.getAsString());

            var artistsArray = itemObject.getAsJsonArray("artists");
            StringBuilder sb = new StringBuilder("[");

            for (JsonElement artistElement: artistsArray) {
                var artistObject = artistElement.getAsJsonObject();
                var artistName = artistObject.getAsJsonPrimitive("name");
                sb.append(artistName.getAsString());
                sb.append(", ");
            }

            String artistsString = sb.substring(0, sb.length() - 2);
            newReleaseData.add(artistsString + "]");

            var externalURLObject = itemObject.getAsJsonObject("external_urls");
            var spotifyURLPrimitive = externalURLObject.getAsJsonPrimitive("spotify");
            newReleaseData.add(spotifyURLPrimitive.getAsString());

            newReleaseData.add("");
        }

        return newReleaseData;
    }
}
