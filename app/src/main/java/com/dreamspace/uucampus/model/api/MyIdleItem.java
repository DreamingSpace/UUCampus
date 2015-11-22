package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/4.
 */
public class MyIdleItem {
    private String idle_id;
    private float price;
    private String name;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIdle_id() {
        return idle_id;
    }

    public void setIdle_id(String idle_id) {
        this.idle_id = idle_id;
    }
}
