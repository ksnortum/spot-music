package net.snortum.spotmusic.controller;

import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.PaginationView;

import java.util.List;

public class Pagination {
    public static final int FIRST_TIME = -1;

    private final Data data;
    private final PaginationView paginationView;

    public Pagination(Data data) {
        this.data = data;
        paginationView = new PaginationView();
    }

    public void printNextPage() {
        List<String> printLines = data.getPrintLines();
        int itemsPerPage = data.getItemsPerPage();
        int linesPerItem = data.getLinesPerItem();
        int pageNumber = data.getPageNumber();
        int totalPages = data.getTotalPages();

        if (pageNumber == FIRST_TIME) {
            pageNumber = 1;
        } else {
            pageNumber++;
        }

        if (pageNumber > totalPages) {
            paginationView.noMorePages();
            data.setPageNumber(pageNumber - 1);
            return;
        }

        int linesPerPage = itemsPerPage * linesPerItem;
        int topOfItemIndex = (pageNumber - 1) * linesPerPage;
        int index = topOfItemIndex;

        for (int item = 0; item < itemsPerPage && index < printLines.size(); item++) {
            while (index < topOfItemIndex + linesPerItem && index < printLines.size()) {
                paginationView.printLine(printLines.get(index));
                index++;
            }

            topOfItemIndex = index;
        }

        paginationView.pageFooter(pageNumber, totalPages);
        data.setPageNumber(pageNumber);
    }

    public void printPreviousPage() {
        List<String> printLines = data.getPrintLines();
        int itemsPerPage = data.getItemsPerPage();
        int linesPerItem = data.getLinesPerItem();
        int pageNumber = data.getPageNumber();
        int totalPages = data.getTotalPages();

        if (pageNumber > totalPages) {
            pageNumber -= 2;
        } else {
            pageNumber--;
        }

        if (pageNumber == 0) {
            paginationView.noMorePages();
            data.setPageNumber(pageNumber + 1);
            return;
        }

        int linesPerPage = itemsPerPage * linesPerItem;
        int topOfItemIndex = (pageNumber - 1) * linesPerPage;
        int index = topOfItemIndex;

        for (int item = 0; item < itemsPerPage && index < printLines.size(); item++) {
            while (index < topOfItemIndex + linesPerItem && index < printLines.size()) {
                paginationView.printLine(printLines.get(index));
                index++;
            }

            topOfItemIndex = index;
        }

        paginationView.pageFooter(pageNumber, totalPages);
        data.setPageNumber(pageNumber);
    }

    public void calculatePageInfo() {
        List<String> printLines = data.getPrintLines();

        if (data.getPrintLines().size() == 0) {
            System.err.println("Cannot calculate page info: no print lines");
            return;
        }

        int itemsPerPage = data.getItemsPerPage();
        int linesPerItem = data.getLinesPerItem();
        int linesPerPage = itemsPerPage * linesPerItem;

        if (linesPerItem == 0) {
            System.err.println("Cannot calculate page info: items per page or lines per item have not been set");
            return;
        }

        data.setTotalPages((int) Math.ceil( (double) printLines.size() / linesPerPage ));
        data.setPageNumber(FIRST_TIME);
    }
}
