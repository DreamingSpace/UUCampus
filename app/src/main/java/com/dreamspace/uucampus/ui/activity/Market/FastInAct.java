package com.dreamspace.uucampus.ui.activity.Market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.Labels;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.Market.ShowGoodsFragment;
import com.dreamspace.uucampus.ui.popupwindow.GoodsSortPopupWindow;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/9/22.
 */
public class FastInAct extends AbsActivity {
    @Bind(R.id.fast_in_act_smarttablayout)
    SmartTabLayout smartTabLayout;
    @Bind(R.id.fast_in_act_view_pager)
    ViewPager pager;
    @Bind(R.id.divider_below_fi_act_smarttab)
    View dividerBelowSTL;
    @Bind(R.id.shadow_view)
    View shadowView;
    @Bind(R.id.content_rl)
    RelativeLayout contentRl;

    private String order;//当前的排列方式
    private GoodsSortPopupWindow popupWindow;
    private boolean actDestory = false;
    private ArrayList<String> mLabels;
    private boolean haveGood = false;

    private FragmentStatePagerItemAdapter pagerAdpater;
    public static String LABEL = "label";
    public static String CATEGORY = "category";
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        category = bundle.getString(CATEGORY);
        order = getString(R.string.order_view_number);//默认排列方式
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fast_in_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            if (popupWindow != null) {
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else{
                    popupWindow.showAsDropDown(mToolBar);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem sort = menu.findItem(R.id.action_sort);
        if(!haveGood){
            sort.setVisible(false);//若没有数据则不现实排序
        }else{
            sort.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fast_in;
    }

    @Override
    protected void prepareDatas() {
        getLabels();
    }

    @Override
    protected void initViews() {
        popupWindow = new GoodsSortPopupWindow(this, shadowView);

        initListeners();
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
                order = getString(R.string.order_view_number);
                //必须调用getpage才能获取准确的fragment，getitem获取的不对
                ((ShowGoodsFragment) pagerAdpater.getPage(pager.getCurrentItem())).orderChange(getString(R.string.order_view_number));
            }
        });

        popupWindow.setHighestAppraiesOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(1);
                order = getString(R.string.order_score);
                ((ShowGoodsFragment) pagerAdpater.getPage(pager.getCurrentItem())).orderChange(getString(R.string.order_score));
            }
        });

        popupWindow.setRecentPublishOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(2);
                order = getString(R.string.order_last_update);
                ((ShowGoodsFragment) pagerAdpater.getPage(pager.getCurrentItem())).orderChange(getString(R.string.order_last_update));
            }
        });

        popupWindow.setCheapestOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(3);
                order = getString(R.string.order_price);
                ((ShowGoodsFragment) pagerAdpater.getPage(pager.getCurrentItem())).orderChange(getString(R.string.order_price));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    private void initSTL() {
        if (mLabels != null) {
            FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);
            for (String label : mLabels) {
                Bundle bundle = new Bundle();
                bundle.putString(LABEL, label);
                bundle.putString(CATEGORY, category);
                creator.add(label, ShowGoodsFragment.class, bundle);
            }
            pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), creator.create());
            smartTabLayout.setDistributeEvenly(false);
            pager.setAdapter(pagerAdpater);
            smartTabLayout.setViewPager(pager);
        }
    }

    private void getLabels() {
        toggleShowLoading(true, null);
        if (!NetUtils.isNetworkConnected(this)) {
            showNetWorkError();
            toggleNetworkError(true, getLabelsClickListener);
        }

        ApiManager.getService(this).getLabels(category, new Callback<Labels>() {
            @Override
            public void success(Labels labels, Response response) {
                if (labels != null && !actDestory) {
                    if (labels.getLabel().size() == 0) {
                        haveGood = false;
                        invalidateOptionsMenu();//不显示排序
                        toggleShowEmpty(true, getString(R.string.no_such_good), null);
                    } else {
                        haveGood = true;
                        invalidateOptionsMenu();
                        mLabels = labels.getLabel();
                        initSTL();
                        toggleRestore();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                toggleShowEmpty(true, null, getLabelsClickListener);
            }
        });
    }

    private View.OnClickListener getLabelsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getLabels();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actDestory = true;
    }

    public String getOrder() {
        return order;
    }
}
