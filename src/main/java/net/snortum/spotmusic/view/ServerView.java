package net.snortum.spotmusic.view;

public class ServerView {
    public String successText() {
        return "Authorization code received. You may close this web page and return back to your program.";
    }

    public String failureText() {
        return "Authorization code was not received. Close this web page and try again.";
    }

    public String successHtml() {
        return  "<html>" +
                "  <body>" +
                "    <h1>Success!</h1>" +
                "    <p>" +
                "      Authorization code received. You may close this web page and return back to your program." +
                "    </p>" +
                "  </body>" +
                "</html>";
    }

    public String failureHtml() {
        return  "<html>" +
                "  <body>" +
                "    <h1>Something went wrong</h1>" +
                "    <p>" +
                "      Authorization code was not received. Close this web page and try again." +
                "    </p>" +
                "  </body>" +
                "</html>";
    }
}
