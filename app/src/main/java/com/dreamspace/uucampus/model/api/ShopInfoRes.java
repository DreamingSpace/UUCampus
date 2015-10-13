package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopInfoRes {
    private String name;
    private String image;
    private String category;
    private String phone;
    private String address;
    private String description;
    private int view_number;
    private int like_number;
    private int collect_number;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getCollect_number() {
        return collect_number;
    }

    public void setCollect_number(int collect_number) {
        this.collect_number = collect_number;
    }
}
