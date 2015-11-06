package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.GoodsCollectionItem;
import com.dreamspace.uucampus.model.GoodsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufan on 2015/10/1.
 */
public class AllGoodsCollectionRes {
    private ArrayList<GoodsItem> collection;

    public ArrayList<GoodsItem> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<GoodsItem> collection) {
        this.collection = collection;
    }
}
