package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ConnectSellerDialog;
import com.dreamspace.uucampus.ui.fragment.Market.GoodDetailPagerFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;

/**
 * Created by wufan on 2015/9/20.
 */
public class FreeGoodsDetailActivity extends AbsActivity {
    @Bind(R.id.detail_comment_stl)
    SmartTabLayout tabLayout;
    @Bind(R.id.detail_comment_view_pager)
    ViewPager detailViewPager;
    @Bind(R.id.consult_ll)
    LinearLayout consultLl;
    @Bind(R.id.collect_ll)
    LinearLayout collect_ll;
    @Bind(R.id.shop_name_ll)
    LinearLayout shopNameLl;

    public static final String TYPE = "type";
    public static final String DETAIL = "detail";
    public static final String COMMENT= "comment";

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_detail;
    }

    @Override
    protected void prepareDatas() {
        initStl();
    }

    @Override
    protected void initViews() {
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.good_detial_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initListeners(){
        consultLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSellerDialog dialog = dialog = new ConnectSellerDialog(FreeGoodsDetailActivity.this,
                        R.style.UpDialog, "good name", "phone number");
                Window window = dialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                layoutParams.y = (int)(80*getResources().getDisplayMetrics().density);
                window.setAttributes(layoutParams);
                dialog.show();
            }
        });

        collect_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        shopNameLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShopShowGoodsAct.class);
            }
        });
    }

    private void initStl(){
        getSupportActionBar().setTitle(getResources().getString(R.string.detial));
        FragmentPagerItemAdapter pagerAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.detial, GoodDetailPagerFragment.class, getBundles(0))
                .add(R.string.comment, GoodDetailPagerFragment.class, getBundles(1))
                .create()
        );
        tabLayout.setCustomTabView(R.layout.good_detail_stl_title_tab, R.id.detail_stl_title_tv);
        detailViewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(detailViewPager);
    }

    private Bundle getBundles(int index){
        Bundle bundle = new Bundle();
        if(index < 2){
            if(index == 0){
                bundle.putString(TYPE,DETAIL);
            }else{
                bundle.putString(TYPE,COMMENT);
            }
        }
        return bundle;
    }
}
