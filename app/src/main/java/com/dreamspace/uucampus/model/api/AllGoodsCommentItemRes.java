package com.dreamspace.uucampus.model.api;

import java.util.List;

/**
 * Created by wufan on 2015/10/1.
 */
public class AllGoodsCommentItemRes {
    private int useful_clicked;
    private String name;
    private String image;
    private String content;
    private int score;
    private int useful_number;
    private String date;
    private String id;


    public void setUseful_clicked(int useful_clicked) {
        this.useful_clicked = useful_clicked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUseful_clicked() {
        return useful_clicked;
    }
}
