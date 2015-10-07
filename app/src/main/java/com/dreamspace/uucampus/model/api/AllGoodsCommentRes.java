package com.dreamspace.uucampus.model.api;

import java.util.List;

/**
 * Created by wufan on 2015/10/1.
 */
public class AllGoodsCommentRes {
    private int total;
    private int number;
    private List<CommentItem> result;

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

    public List<CommentItem> getResult() {
        return result;
    }

    public void setResult(List<CommentItem> result) {
        this.result = result;
    }
}
