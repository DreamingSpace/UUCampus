package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.FreeGoods.FreeGoodsPagerAdapter;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.smartlayout.SmartTabLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/19.
 */
public class FreeGoodsFragment extends BaseLazyFragment {

    @Bind(R.id.free_goods_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.free_goods_smart_tab)
    SmartTabLayout mSmartTabLayout;

    private FreeGoodsPagerAdapter mAdapter;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        String[] item = new String[]{
                getResources().getString(R.string.electronics),
                getResources().getString(R.string.book_magazine),
                getResources().getString(R.string.transport),
                getResources().getString(R.string.daily_used),
                getResources().getString(R.string.items_other)
        };

        final List<String> items = Arrays.asList(item);
        mAdapter=new FreeGoodsPagerAdapter(getChildFragmentManager(),items);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(items.size());
        mSmartTabLayout.setViewPager(mViewPager);
        mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                FreeGoodsLazyDataFragment fragment = (FreeGoodsLazyDataFragment) mViewPager.getAdapter().instantiateItem(mViewPager,position);
                fragment.onPageSelected(position,items.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_free_goods;
    }
}
