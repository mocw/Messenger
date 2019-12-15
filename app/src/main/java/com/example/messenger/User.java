package com.example.messenger;

public class User {
    private String nick;
    private String password;
    private String status;

    public User(String nick, String password,String status) {
        this.nick = nick;
        this.password=password;
        this.status=status;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}
