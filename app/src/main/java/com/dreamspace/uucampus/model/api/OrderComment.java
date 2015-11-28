package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/27.
 */
public class OrderComment {
    private String content;
    private int score;
    private OrderCommentUser user;
    private int useful_number;
    private String date;
    private String _id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public OrderCommentUser getUser() {
        return user;
    }

    public void setUser(OrderCommentUser user) {
        this.user = user;
    }

    public int getUseful_number() {
        return useful_number;
    }

    public void setUseful_number(int useful_number) {
        this.useful_number = useful_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
