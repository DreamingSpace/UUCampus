package com.dreamspace.uucampus.ui.fragment.FreeGoods;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.widget.smartlayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

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

        final List<String> items = Arrays.asList(ShareData.freeGoodsCategorys);
        FragmentStatePagerItemAdapter mAdapter = new FragmentStatePagerItemAdapter(getActivity().getSupportFragmentManager(),
                FragmentPagerItems.with(getActivity()).add(items.get(0), FreeGoodsLazyDataFragment.class)
                        .add(items.get(1), FreeGoodsLazyDataFragment.class)
                        .add(items.get(2), FreeGoodsLazyDataFragment.class)
                        .add(items.get(3), FreeGoodsLazyDataFragment.class)
                        .create());
        mViewPager.setAdapter(mAdapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_free_goods;
    }
}
