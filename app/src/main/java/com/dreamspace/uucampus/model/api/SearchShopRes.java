package com.dreamspace.uucampus.model.api;


import com.dreamspace.uucampus.model.ShopItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class SearchShopRes {
    private ArrayList<ShopItem> result;

    public ArrayList<ShopItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<ShopItem> result) {
        this.result = result;
    }
}
