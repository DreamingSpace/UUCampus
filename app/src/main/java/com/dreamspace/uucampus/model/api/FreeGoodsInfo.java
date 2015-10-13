package com.dreamspace.uucampus.model.api;

/**
 * Created by Lx on 2015/10/13.
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
