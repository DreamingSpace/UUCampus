package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.IdleItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class SearchIdleRes {
    private List<IdleItem> result;

    public List<IdleItem> getResult() {
        return result;
    }

    public void setResult(List<IdleItem> result) {
        this.result = result;
    }
}
