package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/11/9.
 */
public class Buyer {
    private String _id;
    private String phone_num;
    private String location;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
