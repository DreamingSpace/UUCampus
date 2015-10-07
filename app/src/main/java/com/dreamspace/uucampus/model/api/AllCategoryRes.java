package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.CategoryItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class AllCategoryRes {
    private List<CategoryItem> category;

    public List<CategoryItem> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryItem> category) {
        this.category = category;
    }
}
