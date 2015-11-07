package com.dreamspace.uucampus.ui.activity.Market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.AddShopCollectionRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.ShopAllGroupRes;
import com.dreamspace.uucampus.model.api.ShopInfoRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ConnectSellerDialog;
import com.dreamspace.uucampus.ui.fragment.Market.ShopShowGoodsFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/10.
 * 店铺页面，展示的都是相应店铺的商品,进入此页面是需要传入对应shop的id和name
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
    @Bind(R.id.content_rl)
    RelativeLayout contentRl;
    @Bind(R.id.collect_iv)
    ImageView collectIv;

    private boolean actDestory = false;
    private String shopId;//当前店铺的shopid
    private String shopName;//当前店铺的name
    private ConnectSellerDialog consultDialog;

    private ShopAllGroupRes mShopGroup;//当前shop的group
    private ShopInfoRes mShopInfo;//当前shop的信息
    public static final String SHOP_ID = "SHOP_ID";
    public static final String SHOP_NAME = "SHOP_NAME";
    public static final String GROUP = "GROUP";
    private static final int SHOP_DETAIL = 1;
    public static final String SHOP_CURRENT_COLLECT_STATE = "CURRENT_COLLECTION_STATE";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shop_show_goods_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.shop_action_shop_detail){
            if(mShopInfo != null){
                Bundle bundle = new Bundle();
                bundle.putParcelable(ShopDetailAct.SHOP_INFO, mShopInfo);
                bundle.putString(SHOP_ID, shopId);
                readyGoForResult(ShopDetailAct.class, SHOP_DETAIL, bundle);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_shop_show_goods;
    }

    @Override
    protected void prepareDatas() {
        shopId = getIntent().getExtras().getString(SHOP_ID);//获取传递过来的shopid
        shopName = getIntent().getExtras().getString(SHOP_NAME);//name
        getShopGroup();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(shopName);//设置shopname为actionbar title
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return contentRl;
    }

    private void initListeners(){
        consultLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSellerDialog dialog = new ConnectSellerDialog(ShopShowGoodsAct.this,
                        R.style.UpDialog, mShopInfo.getPhone_num(), mShopInfo.getPhone_num());
                Window window = dialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                layoutParams.y = (int) (80 * getResources().getDisplayMetrics().density);
                window.setAttributes(layoutParams);
                dialog.show();
            }
        });

        collectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mShopInfo != null){
                    if(mShopInfo.getIs_collected() == 1){
                        cancelShopCollection();
                    }else{
                        addShopCollection();
                    }
                }
            }
        });
    }

    private void loadViews(){
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);
        for(String group:mShopGroup.getGroup()){
            //将此店铺的id和各个fragment对应的group传入fragment
            Bundle bundle = new Bundle();
            bundle.putString(GROUP,group);
            bundle.putString(SHOP_ID,shopId);
            creator.add(group,ShopShowGoodsFragment.class,bundle);
        }
        FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),creator.create());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        if(mShopInfo.getIs_collected() == 1){
            collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_p));
        }else{
            collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_n));
        }
    }

    //获取商铺的group
    private void getShopGroup(){
        toggleShowLoading(true, null);
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            toggleNetworkError(true, getGroupClickListener);
            return;
        }

        ApiManager.getService(this).getShopCategory(shopId, new Callback<ShopAllGroupRes>() {
            @Override
            public void success(ShopAllGroupRes shopAllGroupRes, Response response) {
                if (shopAllGroupRes != null && !actDestory) {
                    mShopGroup = shopAllGroupRes;
                    getShopInfo();//获取到group后紧接着获取商铺信息
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                toggleShowEmpty(true, null, getGroupClickListener);
            }
        });
    }

    //获取店铺的信息
    private void getShopInfo(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            toggleNetworkError(true,getGroupClickListener);
            return;
        }

        ApiManager.getService(this).getShopInfo(shopId, new Callback<ShopInfoRes>() {
            @Override
            public void success(ShopInfoRes shopInfoRes, Response response) {
                if (shopInfoRes != null && !actDestory) {
                    mShopInfo = shopInfoRes;
                    loadViews();//将获取的结果来展示到界面
                    toggleRestore();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                toggleShowEmpty(true, null, getGroupClickListener);
            }
        });
    }

    //店铺收藏添加
    private void addShopCollection(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).addCollection(shopId, new Callback<AddShopCollectionRes>() {
            @Override
            public void success(AddShopCollectionRes addShopCollectionRes, Response response) {
                if (addShopCollectionRes != null && !actDestory) {
                    showToast(getString(R.string.collect_success));
                    collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_p));
                    mShopInfo.setIs_collected(1);//使本地数据与服务器同步
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    //取消商铺收藏
    private void cancelShopCollection(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).deleteShopCollection(shopId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if(commonStatusRes != null && !actDestory){
                    showToast(getString(R.string.collect_cancel));
                    collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_n));
                    mShopInfo.setIs_collected(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SHOP_DETAIL && resultCode == RESULT_OK){
            int collected = data.getIntExtra(ShopDetailAct.CURRENT_COLLECTION_STATE,-1);
            if(collected == 1){
                collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_p));
                mShopInfo.setIs_collected(1);//使本地数据与服务器同步
            }else if(collected == 0){
                collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_n));
                mShopInfo.setIs_collected(0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //为“我的收藏”界面返回当前商铺的收藏状态
        Intent data = new Intent();
        data.putExtra(SHOP_CURRENT_COLLECT_STATE,mShopInfo.getIs_collected());
        setResult(RESULT_OK,data);
        super.onBackPressed();
    }

    private View.OnClickListener getGroupClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getShopGroup();
        }
    };
}
