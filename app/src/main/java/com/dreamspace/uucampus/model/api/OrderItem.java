package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/8.
 */
public class OrderItem {
    /*
    status
    -1 退款中
    0 订单取消
    1 未支付
    2 未消费
    3 已消费
    4 已评价
     */
    private int status;
    private OrderShopItem shop;
    private OrderGood good;
    private int total_price;
    private String _id;
    private int quantity;

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

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
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
}
