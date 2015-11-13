package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/3.
 */
public class IdleCollectionItem {
    private String idle_id;
    private String name;
    private String image;
    private String price;
    private String user_name;
    private String view_number;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getView_number() {
        return view_number;
    }

    public void setView_number(String view_number) {
        this.view_number = view_number;
    }
}
