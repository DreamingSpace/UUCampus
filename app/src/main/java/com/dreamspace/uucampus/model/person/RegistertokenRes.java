package com.dreamspace.uucampus.model.person;

/**
 * Created by zsh on 2015/10/6.
 */
public class RegistertokenRes {
    private String access_token;
    private String timelimit;
    public String getAccess_token () {
        return access_token;
    }
    public void setAccess_token (String access_token) {
        this.access_token = access_token;
    }
    public String getTimelimit () {
        return timelimit;
    }
    public void setTimelimit (String timelimit) {
        this.timelimit = timelimit;
    }
}
