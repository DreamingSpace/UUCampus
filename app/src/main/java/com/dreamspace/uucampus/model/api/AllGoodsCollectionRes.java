package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.GoodsCollectionItem;

import java.util.List;

/**
 * Created by wufan on 2015/10/1.
 */
public class AllGoodsCollectionRes {
    private int total;
    private int number;
    private List<GoodsCollectionItem>result;

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

    public List<GoodsCollectionItem> getResult() {
        return result;
    }

    public void setResult(List<GoodsCollectionItem> result) {
        this.result = result;
    }
}
