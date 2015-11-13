package com.dreamspace.uucampus.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.CategoryItem;
import com.dreamspace.uucampus.model.api.AllCategoryRes;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.fragment.Market.ShowShopsFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by money on 2015/9/14.
 */
public class MarketFragment extends BaseLazyFragment {
    public static String TYPE_NAME = "type";
    @Bind(R.id.market_view_pager)
    ViewPager pager;
    @Bind(R.id.market_smarttablayout)
    SmartTabLayout smartTabLayout;

    private boolean fragmentDestory = false;
    private FragmentStatePagerItemAdapter pagerAdpater;
    private ArrayList<CategoryItem> categories;

    public static final String CATEGORY = "category";

    @Override
    protected void onFirstUserVisible() {
        getCategory();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_third;
    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(getActivity(),R.id.content_rl);
    }

    private void loadViews(){
        FragmentPagerItems.Creator pagerItems = FragmentPagerItems.with(mContext);
        for(CategoryItem categoryItem:categories){
            Bundle bundle = new Bundle();
            bundle.putParcelable(CATEGORY,categoryItem);
            pagerItems.add(categoryItem.getName(),ShowShopsFragment.class,bundle);
        }
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),pagerItems.create());
        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        pager.setAdapter(pagerAdpater);
        smartTabLayout.setViewPager(pager);
    }

    private void getCategory(){
        toggleShowLoading(true,null);
        if(!NetUtils.isNetworkConnected(mContext)){
            showNetWorkError();
            toggleNetworkError(true,getCategoryClickListener);
            return;
        }

        ApiManager.getService(mContext).getAllShopCategory(new Callback<AllCategoryRes>() {
            @Override
            public void success(AllCategoryRes allCategoryRes, Response response) {
                if(allCategoryRes != null && !fragmentDestory){
                    categories = allCategoryRes.getCategory();
                    loadViews();
                    toggleRestore();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                toggleShowError(true,null,getCategoryClickListener);
            }
        });
    }

    private View.OnClickListener getCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCategory();
        }
    };

    @Override
    public void onDestroy() {
        fragmentDestory = true;
        super.onDestroy();
    }
}