package com.dreamspace.uucampus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by money on 2015/11/8.
 * 保存微信授权后获取的用户信息
 */
public class WeChatUser implements Parcelable{
    private String unionid;
    private String country;
    private String nickname;
    private String city;
    private String province;
    private String language;
    private String headimgurl;
    private String sex;
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    private String openid;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public WeChatUser(){

    }

    public WeChatUser(Parcel in){
        unionid = in.readString();
        country = in.readString();
        nickname = in.readString();
        city = in.readString();
        province = in.readString();
        language = in.readString();
        headimgurl = in.readString();
        sex = in.readString();
        access_token = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(unionid);
        parcel.writeString(country);
        parcel.writeString(nickname);
        parcel.writeString(city);
        parcel.writeString(province);
        parcel.writeString(language);
        parcel.writeString(headimgurl);
        parcel.writeString(sex);
        parcel.writeString(access_token);
    }

    public static Parcelable.Creator<WeChatUser> CREATOR = new Parcelable.Creator<WeChatUser>(){

        @Override
        public WeChatUser createFromParcel(Parcel parcel) {
            return new WeChatUser(parcel);
        }

        @Override
        public WeChatUser[] newArray(int i) {
            return new WeChatUser[i];
        }
    };
}
