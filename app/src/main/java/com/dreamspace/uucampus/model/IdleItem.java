package com.dreamspace.uucampus.model;

/**
 * Created by wufan on 2015/9/29.
 */
public class IdleItem {
    private String idle_id;
    private String name;
    private String image;
    private float price;
    private String user_name;  //only in idle search
    private int view_number;
    private int like_number;

    public String getIdle_id() {
        return idle_id;
    }

    public void setIdle_id(String idle_id) {
        this.idle_id = idle_id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getView_number() {
        return view_number;
    }

    public void setView_number(int view_number) {
        this.view_number = view_number;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(int like_number) {
        this.like_number = like_number;
    }
}
