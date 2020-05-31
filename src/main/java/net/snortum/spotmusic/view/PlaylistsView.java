package net.snortum.spotmusic.view;

import net.snortum.spotmusic.controller.Pagination;
import net.snortum.spotmusic.model.Data;

import java.util.List;

import static net.snortum.spotmusic.model.GlobalData.NO_AUTH_MESSAGE;

public class PlaylistsView {
    private Data data;
    private Pagination pagination;

    public PlaylistsView(Data data) {
        this.data = data;
        pagination = new Pagination(data);
    }

    public void notAuthorizedMessage() {
        System.out.println(NO_AUTH_MESSAGE);
    }

    public void noCategoryMessage() {
        System.out.println("Unknown category name.");
    }

    public void printLines(List<String> lines) {
        data.setPrintLines(lines);
        data.setLinesPerItem(3);
        pagination.calculatePageInfo();
        pagination.printNextPage();
    }
}
