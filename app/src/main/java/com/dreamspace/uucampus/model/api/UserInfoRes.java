package com.dreamspace.uucampus.model.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wufan on 2015/9/28.
 */
public class UserInfoRes implements Parcelable{
    private String image;
    private String name;
    private String enroll_year;
    private String location;
    private String reg_date;
    private String weixin_bind;
    private String weibo_bind;
    private String phone_num;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnroll_year() {
        return enroll_year;
    }

    public void setEnroll_year(String enroll_year) {
        this.enroll_year = enroll_year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getWeixin_bind() {
        return weixin_bind;
    }

    public void setWeixin_bind(String weixin_bind) {
        this.weixin_bind = weixin_bind;
    }

    public String getWeibo_bind() {
        return weibo_bind;
    }

    public void setWeibo_bind(String weibo_bind) {
        this.weibo_bind = weibo_bind;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public static Creator<UserInfoRes> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(enroll_year);
        dest.writeString(location);
    }

    public UserInfoRes(Parcel in){
        image = in.readString();
        name = in.readString();
        enroll_year = in.readString();
        location = in.readString();
    }

   public static final Creator<UserInfoRes> CREATOR = new Parcelable.Creator<UserInfoRes>(){

       @Override
       public UserInfoRes createFromParcel(Parcel source) {
           return new UserInfoRes(source);
       }

       @Override
       public UserInfoRes[] newArray(int size) {
           return new UserInfoRes[size];
       }
   };
}
