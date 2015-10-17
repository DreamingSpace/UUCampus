package com.dreamspace.uucampus.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.base.ChangeColorTabWithText;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by money on 2015/9/14.
 */
public class MainActivity extends AbsActivity implements View.OnClickListener {

    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private ChangeColorTabWithText oneChangeColorTabWithText;
    private ChangeColorTabWithText twoChangeColorTabWithText;
    private ChangeColorTabWithText threeChangeColorTabWithText;
    private List<ChangeColorTabWithText> mChangeColorTabWithTexts = new ArrayList<ChangeColorTabWithText>();
    private TextView centerTitleTv;
    //当前所在的fragment标号
    private int currentIndex = 0;
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        initView();
        initDates();
        initListener();
    }

    private void initListener() {
        oneChangeColorTabWithText.setOnClickListener(this);
        twoChangeColorTabWithText.setOnClickListener(this);
        threeChangeColorTabWithText.setOnClickListener(this);
    }

    private void initDates() {
        HomeFragment firstFragment = new HomeFragment();
        MarketFragment secondFragment = new MarketFragment();
        PersonCenterFragment thirdFragment = new PersonCenterFragment();
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
                if (position < mChangeColorTabWithTexts.size() - 1) {
                    ChangeColorTabWithText left = mChangeColorTabWithTexts.get(position);
                    ChangeColorTabWithText right = mChangeColorTabWithTexts.get(position + 1);
                    left.setIconAlpha(1 - positionOffset);
                    right.setIconAlpha(positionOffset);
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
        oneChangeColorTabWithText = (ChangeColorTabWithText) findViewById(R.id.id_tab1);
        twoChangeColorTabWithText = (ChangeColorTabWithText) findViewById(R.id.id_tab2);
        threeChangeColorTabWithText = (ChangeColorTabWithText) findViewById(R.id.id_tab3);
        mChangeColorTabWithTexts.add(oneChangeColorTabWithText);
        mChangeColorTabWithTexts.add(twoChangeColorTabWithText);
        mChangeColorTabWithTexts.add(threeChangeColorTabWithText);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(getResources().getString(R.string.app_name));
                centerTitleTv.setText("");
                break;

            case 1:
                searchItem.setVisible(true);
                actionBar.setDisplayShowTitleEnabled(false);
                centerTitleTv.setText(getResources().getString(R.string.seller));
                break;

            case 2:
                searchItem.setVisible(false);
                actionBar.setDisplayShowTitleEnabled(false);
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

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        resetTabColor();
        switch (v.getId()) {
            case R.id.id_tab1:
                mChangeColorTabWithTexts.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_tab2:
                mChangeColorTabWithTexts.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_tab3:
                mChangeColorTabWithTexts.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
        }
    }

    private void resetTabColor() {
        for (int i = 0; i < mChangeColorTabWithTexts.size(); i++) {
            mChangeColorTabWithTexts.get(i).setIconAlpha(0.0f);
        }
    }
}