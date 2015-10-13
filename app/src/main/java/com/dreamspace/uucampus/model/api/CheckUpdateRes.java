package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/29.
 */
public class CheckUpdateRes {
    private float version;
    private String downlink;

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public String getDownlink() {
        return downlink;
    }

    public void setDownlink(String downlink) {
        this.downlink = downlink;
    }
}
