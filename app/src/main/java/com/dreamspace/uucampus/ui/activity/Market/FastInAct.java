package com.dreamspace.uucampus.ui.activity.Market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.Market.ShowGoodsFragment;
import com.dreamspace.uucampus.ui.popupwindow.GoodsSortPopupWindow;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.Bind;

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

    GoodsSortPopupWindow popupWindow;

    private FragmentStatePagerItemAdapter pagerAdpater;
    public static String TYPE_NAME = "type";
    public static String CLASSIFICATION_TYPE="classification_name";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString(CLASSIFICATION_TYPE);
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
        if(id == R.id.action_sort){
            if(popupWindow != null){
                popupWindow.showAsDropDown(mToolBar);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fast_in;
    }

    @Override
    protected void prepareDatas() {
        //根据type加载初始化相应的界面
        if(type.equals(getResources().getString(R.string.travel))){
            initTravelViews();
        }else if(type.equals(getResources().getString(R.string.driver_school))){
            initDriverSchoolViews();
        }else if(type.equals(getResources().getString(R.string.class_uniform))){
            initUniformsViews();
        }else if(type.equals(getResources().getString(R.string.study_abroad))){
            initLanguageViews();
        }else if(type.equals(getResources().getString(R.string.personal_shop))){
            initPersonalShopViews();
        }
    }

    @Override
    protected void initViews() {
        popupWindow = new GoodsSortPopupWindow(this,shadowView);
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        popupWindow.setMostPopularOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(0);
            }
        });

        popupWindow.setHighestAppraiesOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(1);
            }
        });

        popupWindow.setRecentPublishOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(2);
            }
        });

        popupWindow.setCheapestOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.popupItemSetSelect(3);
            }
        });
    }

    private void initTravelViews(){
        getSupportActionBar().setTitle(getResources().getString(R.string.travel));
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                        .add(R.string.travel_item_near, ShowGoodsFragment.class, getTravelBundle(0))
                        .add(R.string.travel_item_ancient_town,ShowGoodsFragment.class,getTravelBundle(1))
                        .add(R.string.travel_item_barbecue, ShowGoodsFragment.class, getTravelBundle(2))
                        .add(R.string.travel_item_carnie,ShowGoodsFragment.class,getTravelBundle(3))
                        .add(R.string.travel_item_long_distance,ShowGoodsFragment.class,getTravelBundle(4))
                        .add(R.string.travel_item_beach,ShowGoodsFragment.class,getTravelBundle(5))
                        .add(R.string.items_other,ShowGoodsFragment.class,getTravelBundle(6))
                        .create()
        );
//        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        smartTabLayout.setDistributeEvenly(false);
        pager.setAdapter(pagerAdpater);
        smartTabLayout.setViewPager(pager);
    }

    private void initLanguageViews(){
        getSupportActionBar().setTitle(getResources().getString(R.string.study_abroad));
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.language_item_TOEFL, ShowGoodsFragment.class, getLanguageBundle(0))
                .add(R.string.language_item_IETLS, ShowGoodsFragment.class, getLanguageBundle(1))
                .add(R.string.language_item_GRE, ShowGoodsFragment.class, getLanguageBundle(2))
                .add(R.string.language_item_minlang, ShowGoodsFragment.class, getLanguageBundle(3))
                .add(R.string.language_item_foradvice, ShowGoodsFragment.class, getLanguageBundle(4))
                .add(R.string.items_other, ShowGoodsFragment.class, getLanguageBundle(5))
                .create()
        );
//        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        smartTabLayout.setDistributeEvenly(false);
        pager.setAdapter(pagerAdpater);
        smartTabLayout.setViewPager(pager);
    }

    private void initDriverSchoolViews(){
        getSupportActionBar().setTitle(getResources().getString(R.string.driver_school));
        getSupportActionBar().setTitle(getResources().getString(R.string.driver_school));
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.driver_school_item_normal, ShowGoodsFragment.class, getDriverSchoolBundle(0))
                .add(R.string.driver_school_item_vip, ShowGoodsFragment.class, getDriverSchoolBundle(1))
                .create()
        );
        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        smartTabLayout.setDistributeEvenly(true);//条目小于等于5个，均分宽度
        pager.setAdapter(pagerAdpater);
        smartTabLayout.setViewPager(pager);
    }

    private void initUniformsViews(){
        getSupportActionBar().setTitle(getResources().getString(R.string.class_uniform));
        pagerAdpater = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.uniforms_class, ShowGoodsFragment.class, getUniformsBundle(0))
                .add(R.string.uniforms_club, ShowGoodsFragment.class, getUniformsBundle(1))
                .add(R.string.uniforms_stage, ShowGoodsFragment.class, getUniformsBundle(2))
                .add(R.string.uniforms_lease, ShowGoodsFragment.class, getUniformsBundle(3))
                .add(R.string.items_other, ShowGoodsFragment.class, getUniformsBundle(4))
                .create()
        );
//        smartTabLayout.setCustomTabView(R.layout.market_smart_tab_title_tab,R.id.title_tv);
        smartTabLayout.setDistributeEvenly(false);//条目小于等于5个，均分宽度
        pager.setAdapter(pagerAdpater);
        smartTabLayout.setViewPager(pager);
    }

    private void initPersonalShopViews(){
        getSupportActionBar().setTitle(getResources().getString(R.string.personal_shop));
        smartTabLayout.setVisibility(View.GONE);
        dividerBelowSTL.setVisibility(View.GONE);

        final ShowGoodsFragment showGoodsFragment = new ShowGoodsFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_NAME, getResources().getResourceName(R.string.personal_shop));
        showGoodsFragment.setArguments(args);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return showGoodsFragment;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        pager.setAdapter(fragmentPagerAdapter);
    }

    private Bundle getTravelBundle(int position){
        Bundle bundle = new Bundle();
        if(position < 7){
            switch (position){
                case 0:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel_item_near));
                    break;

                case 1:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel_item_ancient_town));
                    break;

                case 2:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel_item_barbecue));
                    break;

                case 3:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel_item_carnie));
                    break;

                case 4:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel_item_long_distance));
                    break;

                case 5:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.travel_item_beach));
                    break;

                case 6:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.items_other));
                    break;
            }
        }
        return bundle;
    }

    private Bundle getLanguageBundle(int position){
        Bundle bundle = new Bundle();
        if(position < 6){
            switch (position){
                case 0:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.language_item_TOEFL));
                    break;

                case 1:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.language_item_IETLS));
                    break;

                case 2:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.language_item_GRE));
                    break;

                case 3:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.language_item_minlang));
                    break;

                case 4:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.language_item_foradvice));
                    break;

                case 5:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.items_other));
                    break;
            }
        }
        return bundle;
    }

    private Bundle getDriverSchoolBundle(int position){
        Bundle bundle = new Bundle();
        if(position < 2){
            switch (position){
                case 0:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.driver_school_item_normal));
                    break;

                case 1:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.driver_school_item_vip));
                    break;
            }
        }
        return bundle;
    }

    private Bundle getUniformsBundle(int position){
        Bundle bundle = new Bundle();
        if(position < 5){
            switch (position){
                case 0:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.uniforms_class));
                    break;

                case 1:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.uniforms_club));
                    break;

                case 2:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.uniforms_stage));
                    break;

                case 3:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.uniforms_lease));
                    break;

                case 4:
                    bundle.putString(TYPE_NAME,getResources().getString(R.string.items_other));
                    break;
            }
        }
        return bundle;
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
}
