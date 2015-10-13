package com.dreamspace.uucampus.model.api;

/**
 * Created by wufan on 2015/9/28.
 */
public class UserInfoRes {
    private String image;
    private String birthday;
    private String school;
    private String enroll_year;
    private String sex;
    private String location;
    private String reg_data;

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
}
