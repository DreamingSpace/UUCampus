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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.Share;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.DensityUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.common.utils.TLog;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.AddGoodsCollectionRes;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.GoodsInfoRes;
import com.dreamspace.uucampus.ui.activity.Login.LoginActivity;
import com.dreamspace.uucampus.ui.activity.Order.OrderConfirmAct;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ConnectSellerDialog;
import com.dreamspace.uucampus.ui.fragment.Market.GoodDetailPagerFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/8.
 * 进入此activity需要传入相关商品的good_id
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
    @Bind(R.id.collect_iv)
    ImageView collectIv;
    @Bind(R.id.good_detail_image_iv)
    ImageView goodIv;
    @Bind(R.id.good_name_tv)
    TextView goodNameTv;
    @Bind(R.id.shop_image_civ)
    CircleImageView shopImageCiv;
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.people_like_tv)
    TextView peopleLikeTv;
    @Bind(R.id.sale_num_tv)
    TextView saleNumTv;
    @Bind(R.id.last_update_tv)
    TextView lastUpdateTv;
    @Bind(R.id.good_detail_price_tv)
    TextView priceTv;
    @Bind(R.id.price_before_reduce_tv)
    TextView priceBeforeReduceTv;
    @Bind(R.id.content_rl)
    RelativeLayout contentRl;

    private String goodId;
    private boolean actDestory = false;
    private GoodsInfoRes goodInfo;//当前商品的信息

    public static final String TYPE = "type";
    public static final String DETAIL="detail";
    public static final String COMMENT="comment";
    public static final String GOOD_ID = "good_id";
    public static final String GOOD_CURRENT_COLLCET_STATE = "CURRENT_COLLECT_STATE";

    @Override
    protected int getContentView() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = getIntent().getExtras();
        goodId = bundle.getString(GOOD_ID);

        getGoodInfo();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.detial));
        priceBeforeReduceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return contentRl;
    }

    private void initListeners(){
        shopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ShopShowGoodsAct.class);
            }
        });

        //创建咨询对话框并显示
        consultLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSellerDialog dialog = new ConnectSellerDialog(GoodDetailAct.this,
                        R.style.UpDialog, goodInfo.getName(), goodInfo.getPhone_num());
                Window window = dialog.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                layoutParams.y = DensityUtils.dip2px(GoodDetailAct.this, 80);//int)(80*getResources().getDisplayMetrics().density);
                window.setAttributes(layoutParams);
                dialog.show();
            }
        });

        shopNameLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ShopShowGoodsAct.SHOP_ID, goodInfo.getShop_id());
                bundle.putString(ShopShowGoodsAct.SHOP_NAME, goodInfo.getShop_name());
                readyGo(ShopShowGoodsAct.class, bundle);
            }
        });

        //同shopnameLl点击效果一样，都是将shopid和shopname传递到下个界面
        shopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ShopShowGoodsAct.SHOP_ID, goodInfo.getShop_id());
                bundle.putString(ShopShowGoodsAct.SHOP_NAME, goodInfo.getShop_name());
                readyGo(ShopShowGoodsAct.class, bundle);
            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PreferenceUtils.hasKey(GoodDetailAct.this, PreferenceUtils.Key.LOGIN) ||
                        !PreferenceUtils.getBoolean(GoodDetailAct.this,PreferenceUtils.Key.LOGIN)){
                    //未登录
                    readyGo(LoginActivity.class);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderConfirmAct.GOOD_NAME, goodInfo.getName());
                    bundle.putString(OrderConfirmAct.PRICE, goodInfo.getPrice() + "");
                    bundle.putString(OrderConfirmAct.DISCOUNT, goodInfo.getDiscount() + "");
                    bundle.putString(OrderConfirmAct.GOOD_ID, goodId);
                    readyGo(OrderConfirmAct.class, bundle);
                }
            }
        });

        collect_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodInfo != null){
                    if(!PreferenceUtils.hasKey(GoodDetailAct.this, PreferenceUtils.Key.LOGIN) ||
                            !PreferenceUtils.getBoolean(GoodDetailAct.this,PreferenceUtils.Key.LOGIN)){
                        //未登录
                        readyGo(LoginActivity.class);
                    }else{
                        if(goodInfo.getIs_collected() == 0){
                            addGoodCollection();
                        }else{
                            cancelGoodCollection();
                        }
                    }
                }
            }
        });
    }

    private void initStl(){
        Bundle detailBundle = new Bundle();
        detailBundle.putString(DETAIL,goodInfo.getDescription());
        detailBundle.putString(TYPE,DETAIL);
        Bundle commentBundle = new Bundle();
        commentBundle.putString(COMMENT, goodId);
        commentBundle.putString(TYPE, COMMENT);

        FragmentPagerItemAdapter pagerAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.detial, GoodDetailPagerFragment.class, detailBundle)
                .add(R.string.comment, GoodDetailPagerFragment.class, commentBundle)
                .create()
        );
        tabLayout.setCustomTabView(R.layout.good_detail_stl_title_tab, R.id.detail_stl_title_tv);
        detailViewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(detailViewPager);
    }

    //获取商品详细信息
    private void getGoodInfo(){
        toggleShowLoading(true, null);
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            toggleNetworkError(true, getGoodInfoClickListener);
            return;
        }

        ApiManager.getService(this).getGoodsInfo(goodId, new Callback<GoodsInfoRes>() {
            @Override
            public void success(GoodsInfoRes goodsInfoRes, Response response) {
                if (goodsInfoRes != null && !actDestory) {
                    if(goodsInfoRes.getIs_active() == 0){
                        //货物已下架，无法查看
                        toggleShowEmpty(true,getString(R.string.good_is_not_active),null);
                    }else{
                        toggleRestore();
                        goodInfo = goodsInfoRes;
                        setInfoIntoViews(goodsInfoRes);
                        initStl();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                toggleShowEmpty(true, null, getGoodInfoClickListener);
            }
        });
    }

    //添加商品收藏
    private void addGoodCollection(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).addGoodsCollection(goodId, new Callback<AddGoodsCollectionRes>() {
            @Override
            public void success(AddGoodsCollectionRes addGoodsCollectionRes, Response response) {
                if (addGoodsCollectionRes != null && !actDestory) {
                    goodInfo.setIs_collected(1);//更改本地数据，使其与服务器同步
                    collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_p));
                    showToast(getString(R.string.collect_success));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                //已收藏，不会发生已收藏但图标不变的情况(发生在未登录的情况下在此页面登录）
                if(errorRes.getCode() == 406){
                    showToast(getString(R.string.collect_success));
                    goodInfo.setIs_collected(1);//更改本地数据，使其与服务器同步
                    collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_p));
                }else{
                    showInnerError(error);
                }
            }
        });
    }

    //商品收藏取消
    private void cancelGoodCollection(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }

        ApiManager.getService(this).deleteGoodsCollection(goodId, new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if(commonStatusRes != null && !actDestory){
                    goodInfo.setIs_collected(0);
                    collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_n));
                    showToast(getString(R.string.collect_cancel));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    //将商品详细信息填入view
    private void setInfoIntoViews(GoodsInfoRes goodsInfoRes){
        CommonUtils.showImageWithGlide(this, goodIv, goodsInfoRes.getImage());
        CommonUtils.showImageWithGlideInCiv(this, shopImageCiv, goodsInfoRes.getShop_image());
        goodNameTv.setText(goodsInfoRes.getName());
        shopNameTv.setText(goodsInfoRes.getShop_name());
        priceBeforeReduceTv.setText(getString(R.string.RMB) + (float)goodsInfoRes.getOriginal_price() / 100);
        priceTv.setText(getString(R.string.RMB) +  (float)goodsInfoRes.getPrice() / 100);
        peopleLikeTv.setText(goodsInfoRes.getView_number() + getString(R.string.x_people_like));
        saleNumTv.setText(goodsInfoRes.getSales_number() + getString(R.string.x_people_bought));
        lastUpdateTv.setText(goodsInfoRes.getLast_update());
        if(goodsInfoRes.getIs_collected() == 1){
            collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_p));
        }else{
            collectIv.setImageDrawable(getResources().getDrawable(R.drawable.xiangqing_tab_bar_collect_n));
        }
    }


    @Override
    public void onBackPressed() {
        if(goodInfo != null){
            //为“我的收藏”界面返回当前此商品的收藏状态
            Intent data = new Intent();
            data.putExtra(GOOD_CURRENT_COLLCET_STATE,goodInfo.getIs_collected());
            setResult(RESULT_OK,data);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }

    private View.OnClickListener getGoodInfoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getGoodInfo();
        }
    };
}
