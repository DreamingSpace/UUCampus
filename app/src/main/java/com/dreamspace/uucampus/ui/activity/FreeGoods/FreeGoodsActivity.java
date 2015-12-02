package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.CategoryItem;
import com.dreamspace.uucampus.model.api.AllCategoryRes;
import com.dreamspace.uucampus.ui.activity.Login.LoginActivity;
import com.dreamspace.uucampus.ui.activity.Search.SearchResultActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsLazyDataFragment;
import com.dreamspace.uucampus.ui.popupwindow.FreeGoodsSortPopupWindow;
import com.dreamspace.uucampus.widget.smartlayout.SmartTabLayout;
import com.melnykov.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    @Bind(R.id.content_rl)
    RelativeLayout contentRl;

    FreeGoodsSortPopupWindow popupWindow;
    private String order="last_update";
    private boolean bIdleAct=true;
    private boolean haveGood = false;

    private FragmentPagerItemAdapter pagerAdpater;
    public static final String CATEGORY = "category";
    public static final String EXTRA_ORDER="order";
    public static final String EXTRA_IDLE_ACT="idle_activity";

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
    }

    @Override
    protected void initViews() {
        initTabs();

        popupWindow = new FreeGoodsSortPopupWindow(this, shadowView);
        initListeners();
        mPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PreferenceUtils.hasKey(FreeGoodsActivity.this,PreferenceUtils.Key.LOGIN) ||
                        !PreferenceUtils.getBoolean(FreeGoodsActivity.this,PreferenceUtils.Key.LOGIN)){
                    //未登录，需先登录
                    readyGo(LoginActivity.class);
                }else{
                    readyGo(FreeGoodsPublishFirstActivity.class);
                }
            }
        });
    }


    @Override
    protected View getLoadingTargetView() {
        return contentRl;
    }

    private void initListeners() {
        popupWindow.setMostPopularOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(0);
                //必须调用getpage才能获取准确的fragment，getitem获取的不对
                order="view_number";
                ((FreeGoodsLazyDataFragment) pagerAdpater.getPage(mViewPager.getCurrentItem())).orderChange(order);
            }
        });

        popupWindow.setRecentPublishOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(1);
                order="last_update";
                ((FreeGoodsLazyDataFragment)pagerAdpater.getPage(mViewPager.getCurrentItem())).orderChange(order);
            }
        });

        popupWindow.setCheapestOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(2);
                order = "price";
                ((FreeGoodsLazyDataFragment) pagerAdpater.getPage(mViewPager.getCurrentItem())).orderChange(order);
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
        if(id==R.id.free_goods_action_search){
            readyGo(SearchResultActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem sort = menu.findItem(R.id.free_goods_action_sort);
        if(haveGood){
            sort.setVisible(true);
        }else{
            //没有商品则不现实排序图标
            sort.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
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

    private void initTabs() {
        toggleShowLoading(true, null);
        final List<String> items = new ArrayList<String>();
        if(NetUtils.isNetworkConnected(getApplicationContext())){
            ApiManager.getService(getApplicationContext()).getAllIdleCategory(new Callback<AllCategoryRes>() {
                @Override
                public void success(AllCategoryRes allCategoryRes, Response response) {
                    TLog.i("idle tabs:", response.getReason());
                    if(allCategoryRes.getCategory().size() == 0){
                        haveGood = false;
                        invalidateOptionsMenu();
                    }else{
                        haveGood = true;
                        invalidateOptionsMenu();
                        for(CategoryItem categoryItem :allCategoryRes.getCategory()){
                            items.add(categoryItem.getName());
                        }
                        initFragment(items);   //初始化viewpager与smartLayout
                        toggleRestore();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                    toggleShowEmpty(true,getString(R.string.no_such_good),null);
                }
            });
        }else {
            showNetWorkError();
            toggleNetworkError(true,getIdleCategoryClickListener);
        }
    }

    void initFragment(List<String> items) {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);
        for(String titleTab:items){
            Bundle bundle = new Bundle();
            bundle.putString(CATEGORY, titleTab);
            bundle.putString(EXTRA_ORDER,order);
            bundle.putBoolean(EXTRA_IDLE_ACT,bIdleAct);
            creator.add(titleTab, FreeGoodsLazyDataFragment.class, bundle);
        }
        pagerAdpater = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());
//        items = Arrays.asList(ShareData.freeGoodsCategorys);
//        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),
//                FragmentPagerItems.with(this)
//                        .add(items.get(0), FreeGoodsLazyDataFragment.class)
//                        .add(items.get(1), FreeGoodsLazyDataFragment.class)
//                        .add(items.get(2), FreeGoodsLazyDataFragment.class)
//                        .add(items.get(3), FreeGoodsLazyDataFragment.class)
//                        .create());
        mViewPager.setAdapter(pagerAdpater);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    private View.OnClickListener getIdleCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            initTabs();
        }
    };

    public void setFloatActionBtnVisible(boolean visible){
        if(mPublishBtn != null){
            if(visible){
                mPublishBtn.setVisibility(View.VISIBLE);
            }else{
                mPublishBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    public String getOrder(){
        return order;
    }
}