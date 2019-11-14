package com.example.upvote;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String id;

    public User()
    {
        username = "";
        password = "";
    }
    public User(String u, String p, String uid)
    {
        username = u;
        password = p;
        id = uid;
    }

    public void setId(String i)
    {
        id = i;
    }

    public String getId()
    {
        return id;
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