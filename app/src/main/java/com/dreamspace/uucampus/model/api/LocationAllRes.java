package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.LocationItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class LocationAllRes {

    private List<LocationItem> location;

    public List<LocationItem> getLocation() {
        return location;
    }

    public void setLocation(List<LocationItem> location) {
        this.location = location;
    }
}
