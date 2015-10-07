package com.dreamspace.uucampus.model.person;

/**
 * Created by zsh on 2015/10/7.
 */
public class CheckUpdateRes {
    private String version;
    private String downlink;
    public String getVersion(){
        return version;
    }
    public void setVersion(String version){
        this.version = version;
    }
    public String getDownlink(){
        return downlink;
    }
    public void setDownlink(String downlink){
        this.downlink = downlink;
    }
}
