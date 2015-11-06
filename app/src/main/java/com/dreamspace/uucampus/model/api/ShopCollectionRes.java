package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ShopCollectionItem;
import com.dreamspace.uucampus.model.ShopItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopCollectionRes {
    private ArrayList<ShopItem> collection;

    public ArrayList<ShopItem> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<ShopItem> collection) {
        this.collection = collection;
    }
}
