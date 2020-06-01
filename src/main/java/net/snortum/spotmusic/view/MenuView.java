package net.snortum.spotmusic.view;

public class MenuView {
    public void exitMessage() {
        System.out.println("---GOODBYE!---");
    }

    public void actionNotFoundMessage(String action) {
        System.out.printf("\"%s\" is not an action%n", action);
    }

    public void welcome() {
        System.out.println("Welcome to Spot-Music!" + System.lineSeparator());
    }

    //                      ....+....1....+....2....+....3....+....4....+....5....+....6....+....7....+....
    public void displayHelp() {
        System.out.println("help - display this message");
        System.out.println("auth - authorize this application to get your music data");
        System.out.println("quit or exit - quit the application");
        System.out.println("new - display newly released music");
        System.out.println("featured - display featured music ");
        System.out.println("categories - display the categories used by the 'playlists' action");
        System.out.println("playlists <category> - display the playlists for this category");
        System.out.println("next - display the next page of data");
        System.out.println("prev - display the previous page of data");
    }
}
