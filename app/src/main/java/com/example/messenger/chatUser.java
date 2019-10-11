package com.example.messenger;

public class chatUser {
    private String nick;
    private String UID;

    public chatUser(String nick,String UID) {
        this.nick = nick;
        this.UID = UID;
    }

    public String getNick() {
        return nick;
    }

    public String getUID() {
        return UID;
    }
}
