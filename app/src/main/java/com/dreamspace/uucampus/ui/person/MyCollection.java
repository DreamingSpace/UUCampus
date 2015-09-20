package com.dreamspace.uucampus.ui.person;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2015/9/16.
 */
public class MyCollection extends AppCompatActivity {
    private List<Fragment> fragments=new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private TextView textView1, textView2;
    private int currIndex = 0;  //当前页卡编号
    private int bmpW;           //图片宽度
    private ImageView image;
    private Toolbar toolbar;// it's a toolbar
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_useless);
        image = (ImageView)findViewById(R.id.scroll_bar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
        initViewPager();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initData() {
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView1.setText("商品");
        textView2.setText("商家");
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
    }
    private void initViewPager() {
        fragments.add(new MyCollectionGoodsFragment());
        fragments.add(new MyCollectionMarketsFragment());
        FragmentPagerAdapter mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        mViewPager = (ViewPager) findViewById(R.id.vPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnClickListener(int i) {
            index = i;
        }
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenW = dm.widthPixels; // 获取分辨率宽度
            bmpW = screenW / 2;
            Animation animation = new TranslateAnimation(currIndex * bmpW, position * bmpW, 0, 0);
            currIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(200);
            image.startAnimation(animation);
            if (position == 0) {
                textView1.setTextColor(0xFF0096a6);
                textView2.setTextColor(0xFF000000);
            } else {
                textView1.setTextColor(0xFF000000);
                textView2.setTextColor(0xFF0096a6);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }
    }

}
