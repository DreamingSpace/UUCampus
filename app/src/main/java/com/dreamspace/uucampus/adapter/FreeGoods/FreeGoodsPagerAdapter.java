package com.dreamspace.uucampus.adapter.FreeGoods;/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsLazyDataFragment;

import java.util.List;

public class FreeGoodsPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mCategoryList = null;

    public FreeGoodsPagerAdapter(FragmentManager fm, List<String> categoryList) {
        super(fm);
        mCategoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        return new FreeGoodsLazyDataFragment();
    }

    @Override
    public int getCount() {
        return null != mCategoryList ? mCategoryList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null != mCategoryList ? mCategoryList.get(position) : null;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;

    }
    public void setmCategoryList(List<String> mCategoryList) {
        this.mCategoryList = mCategoryList;
        this.notifyDataSetChanged();
    }
}
