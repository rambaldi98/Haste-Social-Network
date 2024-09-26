package com.example.backback.dto.request;

public class UserForm {
    private String username;

    public UserForm(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
