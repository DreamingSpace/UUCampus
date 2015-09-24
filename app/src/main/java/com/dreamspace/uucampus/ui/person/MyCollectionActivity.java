package com.dreamspace.uucampus.ui.person;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zsh on 2015/9/16.
 */
public class MyCollectionActivity extends AbsActivity {
    private List<Fragment> fragments=new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private int currIndex = 0;  //当前页卡编号
    private int bmpW;           //图片宽度
    @Bind(R.id.scroll_bar)
    ImageView image;
    @Bind(R.id.text1)
    TextView textView1;
    @Bind(R.id.text2)
    TextView textView2;

    @Override
    protected int getContentView() {
        return R.layout.person_activity_my_useless;
    }

    @Override
    protected void prepareDatas() {
        textView1.setText("商品");
        textView2.setText("商家");
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
    }

    @Override
    protected void initViews() {
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
