package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/28.
 */
public class RegisterReq {
    private String password;
    private String code;

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    private String phone_num;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
