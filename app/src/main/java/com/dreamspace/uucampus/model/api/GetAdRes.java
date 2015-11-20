package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.AdItem;

import java.util.ArrayList;

/**
 * Created by money on 2015/11/17.
 */
public class GetAdRes {
    private ArrayList<AdItem> advertisement;

    public ArrayList<AdItem> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(ArrayList<AdItem> advertisement) {
        this.advertisement = advertisement;
    }
}
