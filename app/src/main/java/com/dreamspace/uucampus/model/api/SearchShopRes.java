package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ShopItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class SearchShopRes {
    private int total;  //条目总数
    private int number; //本页条目数量
    private List<ShopItem> results;

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

    public List<ShopItem> getResults() {
        return results;
    }

    public void setResults(List<ShopItem> results) {
        this.results = results;
    }
}
