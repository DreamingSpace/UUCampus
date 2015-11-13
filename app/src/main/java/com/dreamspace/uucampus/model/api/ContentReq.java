package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/29.
 */
public class ContentReq {
    private String order_id;
    private int score;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
