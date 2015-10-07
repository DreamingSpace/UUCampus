package com.dreamspace.uucampus.model;

/**
 * Created by Administrator on 2015/7/27 0027.
 */
public class FreeGoodsInfo {
    @Override
    public String toString() {
        return "FreeGoodsInfo{" +
                "courseName='" + courseName + '\'' +
                '}';
    }

    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
