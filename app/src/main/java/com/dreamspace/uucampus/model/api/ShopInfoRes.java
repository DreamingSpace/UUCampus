package com.dreamspace.uucampus.model.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopInfoRes implements Parcelable{
    private String phone_num;
    private String description;
    private String image;
    private int is_active;
    private int is_collected;
    private String address;
    private String owner;
    private String main;
    private String name;

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopInfoRes(Parcel in){
        phone_num = in.readString();
        description = in.readString();
        image = in.readString();
        is_active = in.readInt();
        is_collected = in.readInt();
        address = in.readString();
        owner = in.readString();
        main = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone_num);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeInt(is_active);
        dest.writeInt(is_collected);
        dest.writeString(address);
        dest.writeString(owner);
        dest.writeString(main);
        dest.writeString(name);
    }

    public static Parcelable.Creator<ShopInfoRes> CREATOR = new Parcelable.Creator<ShopInfoRes>(){

        @Override
        public ShopInfoRes createFromParcel(Parcel source) {
            return new ShopInfoRes(source);
        }

        @Override
        public ShopInfoRes[] newArray(int size) {
            return new ShopInfoRes[size];
        }
    };
}
