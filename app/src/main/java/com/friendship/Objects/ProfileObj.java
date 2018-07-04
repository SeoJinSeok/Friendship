package com.friendship.Objects;

import android.graphics.Bitmap;

public class ProfileObj {
    private String nick, comm, birth, region, pwd;
    private String[] favos;
    private boolean sex;
    private int[] getAlarm;
    private Bitmap icon;
    private MoimObj[] joinmoims;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String[] getFavos() {
        return favos;
    }

    public void setFavos(String[] favos) {
        this.favos = favos;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public MoimObj[] getJoinmoims() {
        return joinmoims;
    }

    public void setJoinmoims(MoimObj[] joinmoims) {
        this.joinmoims = joinmoims;
    }
}