package com.friendship.Objects;

import org.json.JSONArray;

public class BoardObj {
    private String b_Title, b_Writer, b_Content, Wdate;
    private JSONArray commenters;

    public String getB_Title() {
        return b_Title;
    }

    public void setB_Title(String title) {
        b_Title = title;
    }

    public String getB_Writer() {
        return b_Writer;
    }

    public void setB_Writer(String writer) {
        b_Writer = writer;
    }

    public String getB_Content() {
        return b_Content;
    }

    public void setB_Content(String content) {
        b_Content = content;
    }

    public String getWdate() {
        return Wdate;
    }

    public void setWdate(String date) {
        Wdate = date;
    }

    public JSONArray getCommenters() {
        return commenters;
    }

    public void setCommenters(JSONArray commenters) {
        this.commenters = commenters;
    }
}