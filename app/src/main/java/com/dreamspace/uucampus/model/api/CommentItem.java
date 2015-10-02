package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/29.
 */
public class CommentItem {
    private String user_id;
    private String content;
    private String date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
