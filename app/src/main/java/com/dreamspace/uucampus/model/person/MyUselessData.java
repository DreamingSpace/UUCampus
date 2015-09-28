package com.dreamspace.uucampus.model.person;

/**
 * Created by zsh on 2015/9/22.
 */
public class MyUselessData {
    private String GoodsName;
    private String GoodsPrice;

    MyUselessData(String GoodsName, String GoodsPrice) {
        this.GoodsName = GoodsName;
        this.GoodsPrice = GoodsPrice;
    }

    public void setGoodsName (String GoodsName) {
        this.GoodsName = GoodsName;
    }
    public String getGoodsName() {
        return GoodsName;
    }
    public void setGoodsPrice (String GoodsPrice) {
        this.GoodsPrice = GoodsPrice;
    }
    public String getGoodsPrice() {
        return GoodsPrice;
    }
}
