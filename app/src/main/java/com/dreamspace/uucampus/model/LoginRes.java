package com.dreamspace.uucampus.model;

/**
 * Created by Wells on 2015/9/27.
 */
public class LoginRes {
    private String access_token;
    private String timelimit;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(String timelimit) {
        this.timelimit = timelimit;
    }
}
