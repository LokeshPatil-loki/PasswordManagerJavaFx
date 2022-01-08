package com.example.passwordmanager;

public class User {
    private int userid;
    private String username;
    private String password;

    User(int userid, String username, String password){
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

