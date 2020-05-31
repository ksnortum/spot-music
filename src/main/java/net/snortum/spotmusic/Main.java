package net.snortum.spotmusic;

import net.snortum.spotmusic.controller.MenuController;
import net.snortum.spotmusic.model.Data;

import java.util.HashMap;
import java.util.Map;

import static net.snortum.spotmusic.model.GlobalData.PORT_NUMBER;

public class Main {
    private static final String ACCESS_FLAG = "-access";
    private static final String RESOURCE_FLAG = "-resource";
    private static final String PAGE_FLAG = "-page";

    public static void main(String[] args) {
        new Main().run(args);
    }

    private void run(String[] args) {
        Map<String, String> argMap = parseArgs(args);
        Data data = new Data();
        data.setAccessURL(argMap.get(ACCESS_FLAG));
        data.setApiURL(argMap.get(RESOURCE_FLAG));
        data.setItemsPerPage(Integer.parseInt(argMap.get(PAGE_FLAG)));
        data.setRedirectURL(String.format("http://localhost:%d", PORT_NUMBER));
        MenuController controller = new MenuController(data);
        controller.inputLoop();
    }

    private Map<String, String> parseArgs(String[] args) {
        Map<String, String> argMap = new HashMap<>();

        for (int i = 0; i < args.length; i = i + 2) {
            if (args.length >= i + 1) {
                argMap.put(args[i], args[i + 1]);
            }
        }

        return setDefaults(argMap);
    }

    private Map<String, String> setDefaults(Map<String, String> argMap) {
        if (!argMap.containsKey(ACCESS_FLAG)) {
            argMap.put(ACCESS_FLAG, "https://accounts.spotify.com");
        }

        if (!argMap.containsKey(RESOURCE_FLAG)) {
            argMap.put(RESOURCE_FLAG, "https://api.spotify.com");
        }

        if (!argMap.containsKey(PAGE_FLAG)) {
            argMap.put(PAGE_FLAG, "5");
        } else {
            try {
                Integer.parseInt(argMap.get(PAGE_FLAG));
            } catch (NumberFormatException nfe) {
                argMap.put(PAGE_FLAG, "5");
            }
        }

        return argMap;
    }

}
