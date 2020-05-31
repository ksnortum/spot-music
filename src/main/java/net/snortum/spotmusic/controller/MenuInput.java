package net.snortum.spotmusic.controller;

import java.util.Scanner;

public class MenuInput {
    private static final Scanner STDIN = new Scanner(System.in);

    static String getAction() {
        return STDIN.nextLine();
    }
}
