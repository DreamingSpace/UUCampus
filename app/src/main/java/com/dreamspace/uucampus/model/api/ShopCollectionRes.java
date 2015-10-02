package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ShopCollectionItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopCollectionRes {
    private int total; //条目数量
    private int number; //页面数量
    private List<ShopCollectionItem> collection;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ShopCollectionItem> getCollection() {
        return collection;
    }

    public void setCollection(List<ShopCollectionItem> collection) {
        this.collection = collection;
    }
}
