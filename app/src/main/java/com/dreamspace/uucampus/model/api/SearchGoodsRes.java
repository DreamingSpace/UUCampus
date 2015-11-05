package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.GoodsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufan on 2015/10/1.
 */
public class SearchGoodsRes {
    private ArrayList<GoodsItem> result;

    public ArrayList<GoodsItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<GoodsItem> result) {
        this.result = result;
    }
}
