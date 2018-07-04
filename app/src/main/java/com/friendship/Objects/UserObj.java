package com.friendship.Objects;

public class UserObj {
    private String email, password;

    public UserObj(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}