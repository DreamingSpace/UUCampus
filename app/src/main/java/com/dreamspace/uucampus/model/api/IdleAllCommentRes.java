package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.FreeGoodsCommentItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class IdleAllCommentRes {
    private List<FreeGoodsCommentItem> idle_comment;

    public List<FreeGoodsCommentItem> getIdle_comment() {
        return idle_comment;
    }

    public void setIdle_comment(List<FreeGoodsCommentItem> idle_comment) {
        this.idle_comment = idle_comment;
    }
}

