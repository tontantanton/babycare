package com.example.babycare.Model;

public class User {

    private String id;
    private String username;
    private String imageURL;
    private boolean isseen;

    public User(String id, String username, String imageURL, boolean isseen) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.isseen = isseen;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
