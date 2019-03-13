package com.encryptionmanager;

public class Website {
    
    private String websiteName;
    private String url;
    private String username;
    private String password;

    public Website(String websiteName, String url, String username, String password) {
        this.websiteName = websiteName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
