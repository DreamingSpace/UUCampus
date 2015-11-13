package com.dreamspace.uucampus.model;

/**
 * Created by wufan on 2015/10/1.
 */
public class GoodsCollectionItem {
    private String goods_id;
    private String name;
    private String image;
    private String original_price;
    private String price;
    private String shop_name;
    private String view_number;
    private String discount;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
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

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getView_number() {
        return view_number;
    }

    public void setView_number(String view_number) {
        this.view_number = view_number;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
