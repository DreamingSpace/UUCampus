package com.dreamspace.uucampus.model.person;

/**
 * Created by zsh on 2015/10/6.
 */
public class RegistertokenReq {
    private String phonenum;
    private String password;
    private String code;
    public String getPhonenum () {
        return phonenum;
    }
    public void setPhonenum (String phonenum) {
        this.phonenum = phonenum;
    }
    public String getPassword () {
        return password;
    }
    public void setPassword (String password) {
        this.password = password;
    }
    public String getCode () {
        return code;
    }
    public void setCode (String code) {
        this.code = code;
    }
}
