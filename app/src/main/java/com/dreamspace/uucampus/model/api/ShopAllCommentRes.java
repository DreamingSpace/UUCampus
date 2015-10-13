package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ShopCommentItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class ShopAllCommentRes {
    private int total;
    private int number;
    private List<ShopCommentItem> comment;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ShopCommentItem> getComment() {
        return comment;
    }

    public void setComment(List<ShopCommentItem> comment) {
        this.comment = comment;
    }
}
