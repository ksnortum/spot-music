package net.snortum.spotmusic.controller;

import net.snortum.spotmusic.model.Data;
import net.snortum.spotmusic.view.MenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuController {
    private static final Pattern PLAYLISTS_PATTERN = Pattern.compile("(playlists)\\s+(.+)");

    private final NewReleaseController releaseController;
    private final FeaturedController featuredController;
    private final CategoriesController categoriesController;
    private final PlaylistsController playlistsController;
    private final AuthorizationController authorizationController;
    private final Pagination pagination;
    private final MenuView menuView;

    public MenuController(Data data) {
        releaseController = new NewReleaseController(data);
        featuredController = new FeaturedController(data);
        categoriesController = new CategoriesController(data);
        playlistsController = new PlaylistsController(data);
        authorizationController = new AuthorizationController(data);
        pagination = new Pagination(data);
        menuView = new MenuView();
    }

    public void inputLoop() {
        menuView.welcome();
        menuView.displayHelp();
        String action;

        do {
            menuView.displayPrompt();
            action = MenuInput.getAction();
            String category = "unknown";
            Matcher matcher = PLAYLISTS_PATTERN.matcher(action);

            if (matcher.matches()) {
                action = matcher.group(1);
                category = matcher.group(2);
            }

            switch (action) {
                case "new":
                    releaseController.doNewReleasesProcessing();
                    break;
                case "featured":
                    featuredController.doFeaturedProcessing();
                    break;
                case "categories":
                    categoriesController.doCategoriesProcessing();
                    break;
                case "playlists":
                    playlistsController.doPlaylistsProcessing(category);
                    break;
                case "auth":
                    authorizationController.getAuthorization();
                    break;
                case "next":
                    pagination.printNextPage();
                    break;
                case "prev":
                    pagination.printPreviousPage();
                    break;
                case "help":
                    menuView.displayHelp();
                    break;
                case "exit":
                case "quit":
                    menuView.exitMessage();
                    break;
                default:
                    menuView.actionNotFoundMessage(action);
                    menuView.displayHelp();
            }

        } while (!"exit".equals(action) && !"quit".equals(action));
    }
}
