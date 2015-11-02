package com.dreamspace.uucampus.model;

import com.dreamspace.uucampus.model.api.AllGoodsCommentItemRes;

import java.util.ArrayList;

/**
 * Created by wufan on 2015/10/1.
 */
public class AllGoodsCommentRes {
    private ArrayList<AllGoodsCommentItemRes> goods_comment;

    public ArrayList<AllGoodsCommentItemRes> getGoods_comment() {
        return goods_comment;
    }

    public void setGoods_comment(ArrayList<AllGoodsCommentItemRes> goods_comment) {
        this.goods_comment = goods_comment;
    }
}
