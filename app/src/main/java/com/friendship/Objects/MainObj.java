package com.friendship.Objects;

import android.graphics.Bitmap;

public class MainObj {
    private Bitmap[] imgs;
    private int[] ids;
    private String[] titles, intros;

    public MainObj(int[] ids, Bitmap[] imgs, String[] titles, String[] intros) {
        this.ids = ids;
        this.imgs = imgs;
        this.titles = titles;
        this.intros = intros;
    }

    public int[] getIds() {
        return ids;
    }

    public Bitmap[] getImgs() {
        return imgs;
    }

    public String[] getTitles() {
        return titles;
    }

    public String[] getIntros() {
        return intros;
    }

}