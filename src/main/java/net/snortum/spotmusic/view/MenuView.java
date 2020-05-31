package net.snortum.spotmusic.view;

public class MenuView {
    public void exitMessage() {
        System.out.println("---GOODBYE!---");
    }

    public void actionNotFoundMessage(String action) {
        System.out.printf("%s is not an action%n", action);
    }
}
