package com.friendship.Objects;


public class ChatObj {
    private String nick;
    private String msg;
    private String icon;

    public ChatObj(String icon, String nick, String msg) {
        this.icon = icon;
        this.nick = nick;
        this.msg = msg;
    }

    public String getNick() {
        return nick;
    }

    public String getMsg() {
        return msg;
    }

    public String getIcon() {
        return icon;
    }
}
