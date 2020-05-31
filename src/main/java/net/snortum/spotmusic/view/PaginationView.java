package net.snortum.spotmusic.view;

public class PaginationView {
    public void noMorePages() {
        System.out.println("No more pages.");
    }

    public void printLine(String line) {
        System.out.println(line);
    }

    public void pageFooter(int pageNumber, int totalPages) {
        System.out.printf("---PAGE %d OF %d---%n", pageNumber, totalPages);
    }
}
