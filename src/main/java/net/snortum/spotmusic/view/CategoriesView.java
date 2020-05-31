package net.snortum.spotmusic.view;

import net.snortum.spotmusic.controller.Pagination;
import net.snortum.spotmusic.model.Data;

import java.util.List;

import static net.snortum.spotmusic.model.GlobalData.NO_AUTH_MESSAGE;

public class CategoriesView {
    private Data data;
    private Pagination pagination;

    public CategoriesView(Data data) {
        this.data = data;
        pagination = new Pagination(data);
    }

    public void notAuthorizedMessage() {
        System.out.println(NO_AUTH_MESSAGE);
    }

    public void printLines(List<String> lines) {
        data.setPrintLines(lines);
        data.setLinesPerItem(1);
        pagination.calculatePageInfo();
        pagination.printNextPage();
    }
}
