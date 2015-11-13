package com.dreamspace.uucampus.model.api;

import java.util.ArrayList;

/**
 * Created by wufan on 2015/9/29.
 */
public class IdleInfoRes {
    private ArrayList<MyIdleItem> idle;

    public ArrayList<MyIdleItem> getIdle() {
        return idle;
    }

    public void setIdle(ArrayList<MyIdleItem> idle) {
        this.idle = idle;
    }
}