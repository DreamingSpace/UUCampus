package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.IdleItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class GetMyIdleRes {
    private int total;
    private int number;
    private List<IdleItem> result;

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

    public List<IdleItem> getResult() {
        return result;
    }

    public void setResult(List<IdleItem> result) {
        this.result = result;
    }
}
