package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/7.
 */
public class CreateOrderReq {
    private String good_id;
    private int quantity;
    private String remark;

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
