package com.dreamspace.uucampus.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.BaseLazyFragment;
import com.dreamspace.uucampus.ui.fragment.Market.ShowShopsFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;


import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */
public class MarketFragment extends BaseLazyFragment {
    public static String TYPE_NAME = "type";
    @Bind(R.id.market_view_pager)
    ViewPager pager;
    @Bind(R.id.market_smarttablayout)
    SmartTabLayout smartTabLayout;
    private FragmentStatePagerItemAdapter pagerAdpater;

    @Override
    protected void onFirstUserVisible() {
//        System.out.println("on firstuservisible");
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(mContext)
                        .add(R.string.travel, ShowShopsFragment.class, getBundle(0))
                        .add(R.string.study_abroad,ShowShopsFragment.class,getBundle(1))
                        .add(R.string.driver_school,ShowShopsFragment.class,getBundle(2))
                        .add(R.string.class_uniform,ShowShopsFragment.class, getBundle(3))
                        .add(R.string.personal_shop,ShowShopsFragment.class, getBundle(4))
                        .create());
        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        pager.setAdapter(pagerAdpater);
        smartTabLayout.setViewPager(pager);
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
        return null;
    }

    /*position 0=旅游 1=留学 2=驾校 3=班服 4=个人小店*/
    private Bundle getBundle(int position){
        Bundle bundle = new Bundle();
        if(position < 5){
            switch (position){
                case 0:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel));
                    break;

                case 1:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.study_abroad));
                    break;

                case 2:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.driver_school));
                    break;

                case 3:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.class_uniform));
                    break;

                case 4:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.personal_shop));
                    break;
            }
        }
        return bundle;
    }
}