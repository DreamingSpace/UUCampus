package com.dreamspace.uucampus.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.dreamspace.uucampus.api.UUService;

/**
 * Created by wufan on 2015/9/28.
 */
public class UserInfoRes implements Parcelable{
    private String image;
    private String enroll_year;
    private String location;
    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
//    public static final Parcelable.Creator<UserInfoRes> CREATOR = new Parcelable.ClassLoaderCreator<UserInfoRes>(){
//        @Override
//        public UserInfoRes createFromParcel(Parcel source) {
//            return new UserInfoRes(source);
//        }
//
//        @Override
//        public UserInfoRes[] newArray(int size) {
//            return new UserInfoRes[size];
//        }
//
//        @Override
//        public UserInfoRes createFromParcel(Parcel source, ClassLoader loader) {
//            return new UserInfoRes(source);
//        }
//    };

}
