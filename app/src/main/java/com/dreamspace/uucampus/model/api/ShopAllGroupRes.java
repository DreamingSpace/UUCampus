package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ShopGroupItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopAllGroupRes {
    private List<ShopGroupItem> group;

    public List<ShopGroupItem> getGroup() {
        return group;
    }

    public void setGroup(List<ShopGroupItem> group) {
        this.group = group;
    }
}
