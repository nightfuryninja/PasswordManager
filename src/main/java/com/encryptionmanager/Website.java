package com.encryptionmanager;

public class Website {
    
    private String websiteName;
    private String url;
    private byte[] username;
    private byte[] password;

    public Website(String websiteName, String url, byte[] username, byte[] password) {
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

    public byte[] getUsername() {
        return username;
    }

    public void setUsername(byte[] username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
    
}
