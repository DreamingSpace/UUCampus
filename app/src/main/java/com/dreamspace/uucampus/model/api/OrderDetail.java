package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/8.
 */
public class OrderDetail {
    private int status;
    private OrderShopItem shop;
    private OrderGood good;
    private String code;
    private Buyer buyer;
    private String remark;
    private int total_price;
    private boolean use_card;
    private String time;
    private String _id;
    private int quantity;
    private OrderComment comment;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderShopItem getShop() {
        return shop;
    }

    public void setShop(OrderShopItem shop) {
        this.shop = shop;
    }

    public OrderGood getGood() {
        return good;
    }

    public void setGood(OrderGood good) {
        this.good = good;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public boolean isUse_card() {
        return use_card;
    }

    public void setUse_card(boolean use_card) {
        this.use_card = use_card;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderComment getComment() {
        return comment;
    }

    public void setComment(OrderComment comment) {
        this.comment = comment;
    }
}
