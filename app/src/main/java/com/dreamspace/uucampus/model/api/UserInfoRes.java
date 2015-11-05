package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/28.
 */
public class UserInfoRes {
    private String image;
    private String birthday;
    private String school;
    private String nickname;
    private String name;
    private String enroll_year;
    private String sex;
    private String location;
    private String reg_data;
    private String is_active;

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEnroll_year() {
        return enroll_year;
    }

    public void setEnroll_year(String enroll_year) {
        this.enroll_year = enroll_year;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReg_data() {
        return reg_data;
    }

    public void setReg_data(String reg_data) {
        this.reg_data = reg_data;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
