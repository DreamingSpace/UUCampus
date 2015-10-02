package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsDetailBottomCommentFragment;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsDetailBottomInfoFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/20.
 */
public class FreeGoodsDetailActivity extends AbsActivity {

    @Bind(R.id.goods_detail_bottom_info_tv)
    TextView mInfoTv;
    @Bind(R.id.goods_detail_bottom_comment_tv)
    TextView mCommentTv;
    @Bind(R.id.goods_detail_bottom_line_iv)
    ImageView mLineTv;
    @Bind(R.id.goods_detail_bottom_view_pager)
    ViewPager mViewPager;

    private ArrayList<Fragment> fragmentList;
    private int currIndex = 0;   //当前底部页卡编号
    private int bottomLineWidth;  //底部横线图片宽度
    private int bottomOffset = 0; //底部图片移动的偏移量
    private int bottomPosition;  //底部图片的位置

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_detail;
    }

    @Override
    protected void prepareDatas() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        bottomLineWidth = mLineTv.getLayoutParams().width;
        bottomOffset = (int) ((screenW / 2 - bottomLineWidth) / 2);
        bottomPosition = (int) (screenW / 2 - bottomLineWidth / 3);
    }

    @Override
    protected void initViews() {
        mInfoTv.setOnClickListener(new textListener(0));
        mCommentTv.setOnClickListener(new textListener(1));
        initViewPager();
    }

    private class textListener implements View.OnClickListener {
        private int index = 0;

        public textListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    private void initViewPager() {
        fragmentList = new ArrayList<Fragment>();

        //详情页面需要分别往商品详细与评论fragment中 传入 详情text与从后台获取评论需要的参数
        Fragment infoFragment = FreeGoodsDetailBottomInfoFragment.newInstance();  //商品详细直接传入
        Fragment commentFragment = FreeGoodsDetailBottomCommentFragment.newInstance();    //评论可以跳转后再次从后台获取数据

        fragmentList.add(infoFragment);
        fragmentList.add(commentFragment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {  //为pager设置fragment的适配器
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {     //监听动态切换fragment
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        if (currIndex == 1) {
                            animation = new TranslateAnimation(bottomPosition, 0, 0, 0);
                            mCommentTv.setTextColor(getResources().getColor(R.color.text_pressed));
                        }
                        mInfoTv.setTextColor(getResources().getColor(R.color.text_normal));
                        break;
                    case 1:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(bottomOffset, bottomPosition, 0, 0);
                            mInfoTv.setTextColor(getResources().getColor(R.color.text_pressed));
                        }
                        mCommentTv.setTextColor(getResources().getColor(R.color.text_normal));
                        break;
                    default:
                        break;
                }
                currIndex = position;
                animation.setFillAfter(true);
                animation.setDuration(300);
                mLineTv.startAnimation(animation);
            }
        });
    }

}
