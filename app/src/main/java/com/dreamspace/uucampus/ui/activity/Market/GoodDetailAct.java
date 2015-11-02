package com.dreamspace.uucampus.ui.activity.Market;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.Share;
import com.dreamspace.uucampus.ui.activity.Order.OrderConfirmAct;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ConnectSellerDialog;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/8.
 */
public class GoodDetailAct extends AbsActivity {
    @Bind(R.id.detail_comment_stl)
    SmartTabLayout tabLayout;
    @Bind(R.id.detail_comment_view_pager)
    ViewPager detailViewPager;
    @Bind(R.id.shop_ll)
    LinearLayout shopLl;
    @Bind(R.id.consult_ll)
    LinearLayout consultLl;
    @Bind(R.id.collect_ll)
    LinearLayout collect_ll;
    @Bind(R.id.shop_name_ll)
    LinearLayout shopNameLl;
    @Bind(R.id.buy_btn)
    Button buyBtn;
    @Bind(R.id.price_before_reduce_tv)
    TextView priceBeforeReduceTv;

    private Share share;
    public static final String TYPE = "type";
    public static final String DETAIL="detail";
    public static final String COMMENT="comment";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.good_detial_act_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.good_detail_action_share){
            share.getController().openShare(this,false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void prepareDatas() {
        initStl();
        share = new Share(this);
        share.ShareInQQ("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInWechat("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInQZone("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInWechatCircle("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInSina("good内容", R.drawable.banner1);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.detial));
        priceBeforeReduceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        initListeners();
    }

    private void initListeners(){
        shopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShopShowGoodsAct.class);
            }
        });

        consultLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSellerDialog dialog = dialog = new ConnectSellerDialog(GoodDetailAct.this,
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

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(OrderConfirmAct.class);
            }
        });
    }

    private void initStl(){
//        FragmentPagerItemAdapter pagerAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
//                                .add(R.string.detial, GoodDetailPagerFragment.class, getBundles(0))
//                                .add(R.string.comment, GoodDetailPagerFragment.class, getBundles(1))
//                                .create()
//        );
        tabLayout.setCustomTabView(R.layout.good_detail_stl_title_tab, R.id.detail_stl_title_tv);
        //detailViewPager.setAdapter(pagerAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = share.getController().getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
