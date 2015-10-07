package com.dreamspace.uucampus.model.person;

/**
 * Created by zsh on 2015/10/6.
 */
public class ErrorRes {
    private String reason;
    private String state;
    private int code;
    public String toString() {
        return "reason={" + "code=" + code + "state=" + state + "reason=" + reason + "}";
    }
    public String getReason () {
        return reason;
    }
    public void setReason (String reason) {
        this.reason = reason;
    }
    public String getState () {
        return state;
    }
    public void setState (String state) {
        this.state = state;
    }
    public int getCode () {
        return code;
    }
    public void setCode (int code) {
        this.code = code;
    }
}
