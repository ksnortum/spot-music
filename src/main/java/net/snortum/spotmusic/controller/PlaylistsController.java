package net.snortum.spotmusic.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.PlaylistsView;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PlaylistsController {
    private final Data data;
    private final ClientServices clientServices;
    private final PlaylistsView playlistsView;

    PlaylistsController(Data data) {
        this.data = data;
        clientServices = new ClientServices(data);
        playlistsView = new PlaylistsView(data);
    }

    void doPlaylistsProcessing(String category) {
        if (!data.isAuthorized()) {
            playlistsView.notAuthorizedMessage();
            return;
        }

        if (!data.getCategories().containsKey(category)) {
            playlistsView.noCategoryMessage();
            return;
        }

        String categoryId = data.getCategories().get(category);
        Optional<HttpResponse<String>> response =
                clientServices.getResponse(String.format("/v1/browse/categories/%s/playlists", categoryId));

        if (response.isEmpty()) {
            return;
        }

        List<String> printLines = parsePlaylistsData(response.get().body());
        playlistsView.printLines(printLines);
    }

    private List<String> parsePlaylistsData(String jsonString) {
        List<String> playlistsData = new ArrayList<>();
        JsonObject top = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject playlists = top.getAsJsonObject("playlists");
        JsonArray items = playlists.getAsJsonArray("items");

        for (JsonElement item : items) {
            var itemObject = item.getAsJsonObject();
            var namePrimitive = itemObject.getAsJsonPrimitive("name");
            playlistsData.add(namePrimitive.getAsString());

            var externalURLObject = itemObject.getAsJsonObject("external_urls");
            var spotifyURLPrimitive = externalURLObject.getAsJsonPrimitive("spotify");
            playlistsData.add(spotifyURLPrimitive.getAsString());

            playlistsData.add("");
        }

        return playlistsData;
    }
}
