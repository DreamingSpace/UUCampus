package com.dreamspace.uucampus.model.api;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class IdleAllCommentRes {
    private int total;
    private int number;
    private List<CommentItem> comment;

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

    public List<CommentItem> getComment() {
        return comment;
    }

    public void setComment(List<CommentItem> comment) {
        this.comment = comment;
    }
}

