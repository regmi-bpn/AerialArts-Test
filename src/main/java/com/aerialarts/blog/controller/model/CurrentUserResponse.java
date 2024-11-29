package com.aerialarts.blog.controller.model;

public class CurrentUserResponse {
    private String username;

    public CurrentUserResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
