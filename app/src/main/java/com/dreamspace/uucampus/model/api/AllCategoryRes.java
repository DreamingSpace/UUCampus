package com.dreamspace.uucampus.model.api;


import com.dreamspace.uucampus.model.CategoryItem;

import java.util.ArrayList;

/**
 * Created by wufan on 2015/9/29.
 */
public class AllCategoryRes {
    private ArrayList<CategoryItem> category;

    public ArrayList<CategoryItem> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<CategoryItem> category) {
        this.category = category;
    }
}
