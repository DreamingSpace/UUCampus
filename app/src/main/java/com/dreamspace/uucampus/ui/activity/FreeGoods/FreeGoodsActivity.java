package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.ShareData;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsLazyDataFragment;
import com.dreamspace.uucampus.ui.popupwindow.FreeGoodsSortPopupWindow;
import com.dreamspace.uucampus.widget.smartlayout.SmartTabLayout;
import com.melnykov.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/19.
 */
public class FreeGoodsActivity extends AbsActivity {

    @Bind(R.id.free_goods_publish_btn)
    FloatingActionButton mPublishBtn;
    @Bind(R.id.free_goods_shadow_view)
    View shadowView;
    @Bind(R.id.free_goods_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.free_goods_smart_tab)
    SmartTabLayout mSmartTabLayout;

    FreeGoodsSortPopupWindow popupWindow;

    private FragmentStatePagerItemAdapter pagerAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods;
    }

    @Override
    protected void prepareDatas() {
//        FreeGoodsFragment fragment = new FreeGoodsFragment();
//        getSupportFragmentManager().beginTransaction().
//                add(R.id.free_goods_tab_container, fragment).
//                commit();
    }

    @Override
    protected void initViews() {
        initFragment();   //初始化viewpager与smartLayout
        popupWindow = new FreeGoodsSortPopupWindow(this, shadowView);
        initListeners();
        mPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(FreeGoodsPublishFirstActivity.class);
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners() {
        popupWindow.setMostPopularOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(0);
                //必须调用getpage才能获取准确的fragment，getitem获取的不对
                ((FreeGoodsLazyDataFragment) pagerAdpater.getPage(mViewPager.getCurrentItem())).orderChange(getString(R.string.order_view_number));
            }
        });

        popupWindow.setRecentPublishOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(2);
                ((FreeGoodsLazyDataFragment)pagerAdpater.getPage(mViewPager.getCurrentItem())).orderChange(getString(R.string.order_last_update));
            }
        });

        popupWindow.setCheapestOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(3);
                ((FreeGoodsLazyDataFragment)pagerAdpater.getPage(mViewPager.getCurrentItem())).orderChange(getString(R.string.order_price));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_free_goods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.free_goods_action_sort) {
            if (popupWindow != null) {
                popupWindow.showAsDropDown(mToolBar);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(popupWindow != null && popupWindow.isShowing())
        {
            popupWindow.dismiss();
        }else{
            super.onBackPressed();
        }
    }

    void initFragment() {
        final List<String> items = Arrays.asList(ShareData.freeGoodsCategorys);
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add(items.get(0), FreeGoodsLazyDataFragment.class)
                        .add(items.get(1), FreeGoodsLazyDataFragment.class)
                        .add(items.get(2), FreeGoodsLazyDataFragment.class)
                        .add(items.get(3), FreeGoodsLazyDataFragment.class)
                        .create());
        mViewPager.setAdapter(pagerAdpater);
        mSmartTabLayout.setViewPager(mViewPager);
    }
}