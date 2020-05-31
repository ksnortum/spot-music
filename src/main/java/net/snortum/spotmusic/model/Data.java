package net.snortum.spotmusic.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private String accessURL;
    private String apiURL;
    private int itemsPerPage;
    private String redirectURL;
    private boolean authorized = false;
    private String accessCode = "";
    private String authCode = "";
    private Map<String, String> categories = new HashMap<>();
    private List<String> printLines;
    private int linesPerItem;
    private int pageNumber;
    private int totalPages;

    public String getAccessURL() {
        return accessURL;
    }

    public void setAccessURL(String accessURL) {
        this.accessURL = accessURL;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    public List<String> getPrintLines() {
        return printLines;
    }

    public void setPrintLines(List<String> printLines) {
        this.printLines = printLines;
    }

    public int getLinesPerItem() {
        return linesPerItem;
    }

    public void setLinesPerItem(int linesPerItem) {
        this.linesPerItem = linesPerItem;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
