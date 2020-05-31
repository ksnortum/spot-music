package net.snortum.spotmusic.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.CategoriesView;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CategoriesController {
    private final Data data;
    private final ClientServices clientServices;
    private final CategoriesView categoriesView;

    CategoriesController(Data data) {
        this.data = data;
        clientServices = new ClientServices(data);
        categoriesView = new CategoriesView(data);
    }

    void doCategoriesProcessing() {
        if (!data.isAuthorized()) {
            categoriesView.notAuthorizedMessage();
            return;
        }

        Optional<HttpResponse<String>> response =
                clientServices.getResponse("/v1/browse/categories");

        if (response.isEmpty()) {
            return;
        }

        List<String> printLines = parseCategoriesData(response.get().body());
        categoriesView.printLines(printLines);
    }

    private List<String> parseCategoriesData(String jsonString) {
        List<String> categoriesData = new ArrayList<>();
        data.setCategories(new HashMap<>());
        JsonObject top = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject playlists = top.getAsJsonObject("categories");
        JsonArray items = playlists.getAsJsonArray("items");

        for (JsonElement item : items) {
            var itemObject = item.getAsJsonObject();
            var namePrimitive = itemObject.getAsJsonPrimitive("name");
            String name = namePrimitive.getAsString();
            categoriesData.add(name);

            var idPrimitive = itemObject.getAsJsonPrimitive("id");
            String id = idPrimitive.getAsString();
            data.getCategories().put(name, id);
        }

        return categoriesData;
    }
}
