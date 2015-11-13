package com.dreamspace.uucampus.model;

import com.dreamspace.uucampus.model.api.IdleCollectionItem;

import java.util.ArrayList;

/**
 * Created by Lx on 2015/11/3.
 */
public class IdleCollectionRes {
    private ArrayList<IdleCollectionItem> collection;

    public ArrayList<IdleCollectionItem> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<IdleCollectionItem> collection) {
        this.collection = collection;
    }
}
