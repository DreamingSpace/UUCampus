package com.dreamspace.uucampus.model;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public class ErrorRes {
    private String reason;
    private String state;
    private int code;

    @Override
    public String toString() {
        return "ErrorRes{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getState() {
        return state;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setState(String state) {
        this.state = state;
    }

}
