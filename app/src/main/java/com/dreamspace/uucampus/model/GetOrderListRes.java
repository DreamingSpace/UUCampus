package com.dreamspace.uucampus.model;

import com.dreamspace.uucampus.model.api.OrderItem;

import java.util.ArrayList;

/**
 * Created by Lx on 2015/11/9.
 */
public class GetOrderListRes {
    private ArrayList<OrderItem> orders;

    public ArrayList<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderItem> orders) {
        this.orders = orders;
    }
}
