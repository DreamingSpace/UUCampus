package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/29.
 */
public class CategoryReq {
    private String name;
    private String type;  //type为shop/idle

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
