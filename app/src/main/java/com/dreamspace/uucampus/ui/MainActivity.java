package com.dreamspace.uucampus.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.ui.activity.Search.SearchResultActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by money on 2015/9/14.
 */
public class MainActivity extends AbsActivity implements View.OnClickListener {
    @Bind(R.id.home_page_rl)
    RelativeLayout homeRl;
    @Bind(R.id.shop_rl)
    RelativeLayout shopRl;
    @Bind(R.id.personal_rl)
    RelativeLayout personalRl;
    @Bind(R.id.home_select_ll)
    LinearLayout homeSelectLl;
    @Bind(R.id.home_unselect_ll)
    LinearLayout homeUnselectLl;
    @Bind(R.id.shop_select_ll)
    LinearLayout shopSelectLl;
    @Bind(R.id.shop_unselect_ll)
    LinearLayout shopUnselectLl;
    @Bind(R.id.personal_select_ll)
    LinearLayout personalSelectLl;
    @Bind(R.id.personal_unselect_ll)
    LinearLayout personalUnselectLl;

    private List<LinearLayout> mBottomTabs = new ArrayList<LinearLayout>();
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private TextView centerTitleTv;
    private LinearLayout locationLl;//校区地址
    private TextView locationTv;
    private long lastBackPreeTime = 0;
    //当前所在的fragment标号
    private int currentIndex = 0;
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void prepareDatas() {
        UmengUpdateAgent.setDeltaUpdate(false);//关闭增量更新，增量更新会出现错误
        UmengUpdateAgent.update(this);
    }

    @Override
    protected void initViews() {
        initView();
        initDates();
        initListener();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListener() {
        homeRl.setOnClickListener(this);
        shopRl.setOnClickListener(this);
        personalRl.setOnClickListener(this);
    }

    private void initDates() {
        HomeFragment firstFragment = new HomeFragment();
        MarketFragment secondFragment = new MarketFragment();
        final PersonCenterFragment thirdFragment = new PersonCenterFragment();
        mFragments.add(firstFragment);
        mFragments.add(secondFragment);
        mFragments.add(thirdFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < 2) {
                    if (position == 0) {
                        homePageIconSetAlpha(1 - positionOffset);
                        shopPageIconSetAlpha(positionOffset);
                    } else if (position == 1) {
                        shopPageIconSetAlpha(1 - positionOffset);
                        personalPageIconSetAlpha(positionOffset);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        currentIndex = 0;
                        break;

                    case 1:
                        currentIndex = 1;
                        break;

                    case 2:
                        currentIndex = 2;
                        thirdFragment.updateView();
                        break;
                }
                //重新加载menu item
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        centerTitleTv = (TextView) mToolBar.findViewById(R.id.custom_title_tv);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        locationLl = (LinearLayout) mToolBar.findViewById(R.id.location_ll);
        locationTv = (TextView) mToolBar.findViewById(R.id.location_tv);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initBottomBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        switch (currentIndex){
            case 0:
                searchItem.setVisible(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setTitle(getResources().getString(R.string.app_name));
                centerTitleTv.setText("");
                locationLl.setVisibility(View.VISIBLE);
                locationTv.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.LOCATION,getString(R.string.seu)));
                break;

            case 1:
                searchItem.setVisible(true);
                actionBar.setDisplayShowTitleEnabled(false);
                locationLl.setVisibility(View.GONE);
                centerTitleTv.setText(getResources().getString(R.string.seller));
                break;

            case 2:
                searchItem.setVisible(false);
                actionBar.setDisplayShowTitleEnabled(false);
                locationLl.setVisibility(View.GONE);
                centerTitleTv.setText(getResources().getString(R.string.personal_center));
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_search){
            readyGo(SearchResultActivity.class);
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_rl:
                homePageIconSetAlpha(1);
                shopPageIconSetAlpha(0);
                personalPageIconSetAlpha(0);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.shop_rl:
                homePageIconSetAlpha(0);
                shopPageIconSetAlpha(1);
                personalPageIconSetAlpha(0);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.personal_rl:
                homePageIconSetAlpha(0);
                shopPageIconSetAlpha(0);
                personalPageIconSetAlpha(1);
                mViewPager.setCurrentItem(2, false);
                break;
        }
    }

    private void initBottomBar(){
        homeUnselectLl.setAlpha(0);
        shopSelectLl.setAlpha(0);
        personalSelectLl.setAlpha(0);
    }

    private void homePageIconSetAlpha(float alpha){
        homeSelectLl.setAlpha(alpha);
        homeUnselectLl.setAlpha(1 - alpha);
    }

    private void shopPageIconSetAlpha(float alpha){
        shopSelectLl.setAlpha(alpha);
        shopUnselectLl.setAlpha(1 - alpha);
    }

    private void personalPageIconSetAlpha(float alpha){
        personalSelectLl.setAlpha(alpha);
        personalUnselectLl.setAlpha(1 - alpha);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastBackPreeTime > 2000){
            showToast(getString(R.string.press_again_to_exit));
            lastBackPreeTime = System.currentTimeMillis();
        }else{
            super.onBackPressed();
        }
    }
}