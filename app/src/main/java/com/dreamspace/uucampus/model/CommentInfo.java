package com.dreamspace.uucampus.model;

/**
 * Created by wufan on 2015/9/24.
 */
public class CommentInfo {

    @Override
    public String toString() {
        return "CommentInfo{" +
                "userName='" + userName + '\'' +
                '}';
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
