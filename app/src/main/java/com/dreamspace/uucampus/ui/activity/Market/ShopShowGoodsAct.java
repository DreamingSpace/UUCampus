package com.dreamspace.uucampus.ui.activity.Market;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.Market.ShowGoodsFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/10.
 */
public class ShopShowGoodsAct extends AbsActivity {
    @Bind(R.id.shop_show_goods_act_smarttablayout)
    SmartTabLayout smartTabLayout;
    @Bind(R.id.shop_sg_act_view_pager)
    ViewPager viewPager;
    @Bind(R.id.shop_collect_ll)
    LinearLayout collectLl;
    @Bind(R.id.shop_consult_ll)
    LinearLayout consultLl;
    @Bind(R.id.shop_consult_shadow_rl)
    RelativeLayout consultShadowRl;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shop_show_goods_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.shop_action_shop_detail){
            readyGo(ShopDetailAct.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_shop_show_goods;
    }

    @Override
    protected void prepareDatas() {
        initStl();
    }

    @Override
    protected void initViews() {
        initListeners();
    }

    private void initListeners(){
        consultLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultShadowRl.setVisibility(View.VISIBLE);
            }
        });

        collectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        consultShadowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultShadowRl.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initStl(){
        getSupportActionBar().setTitle("东大旅游吧");
        FragmentStatePagerItemAdapter pagerAdapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                                        .add("精选",ShowGoodsFragment.class)
                                        .add("特惠",ShowGoodsFragment.class)
                                        .add("一日游",ShowGoodsFragment.class)
                                        .add("两日游",ShowGoodsFragment.class)
                                        .add("江浙游",ShowGoodsFragment.class)
                                        .create()
        );
        viewPager.setAdapter(pagerAdapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if(consultShadowRl.getVisibility() == View.VISIBLE){
            consultShadowRl.setVisibility(View.INVISIBLE);
        }else{
            super.onBackPressed();
        }
    }
}