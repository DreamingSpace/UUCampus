package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ShopDiscountItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopDiscountRes {
    private List<ShopDiscountItem> discount;

    public List<ShopDiscountItem> getDiscount() {
        return discount;
    }

    public void setDiscount(List<ShopDiscountItem> discount) {
        this.discount = discount;
    }
}