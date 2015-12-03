package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.Share;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.api.GetIdleInfoRes;
import com.dreamspace.uucampus.model.api.LikeIdleRes;
import com.dreamspace.uucampus.ui.activity.Login.LoginActivity;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsDetailBottomCommentFragment;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsDetailBottomInfoFragment;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsLazyListFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//进入此activity需要传入idle_id
public class FreeGoodsDetailActivity extends AbsActivity {
    @Bind(R.id.free_good_detail_comment_stl)
    SmartTabLayout tabLayout;
    @Bind(R.id.free_good_detail_comment_view_pager)
    ViewPager detailViewPager;
    @Bind(R.id.free_goods_detail_name_tv)
    TextView mGoodsNameTv;
    @Bind(R.id.free_goods_detail_user_name_tv)
    TextView mUserNameTv;
    @Bind(R.id.free_good_detail_image_iv)
    ImageView mImageIv;
    @Bind(R.id.free_goods_detail_create_time_tv)
    TextView mDateTv;
    @Bind(R.id.free_goods_detail_like_tv)
    TextView mLikeTv;
    @Bind(R.id.free_good_detail_price_tv)
    TextView mPriceTv;
    @Bind(R.id.free_goods_detail_people_view_tv)
    TextView mViewTv;
    @Bind(R.id.free_good_detail_like_no_click_iv)
    ImageView mLikeIv;
    @Bind(R.id.free_goods_detail_shop_name_image_cv)
    CircleImageView mUserImage;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    public static final String TYPE = "type";
    public static final String DETAIL = "detail";
    public static final String COMMENT= "comment";
    public static final String EXTRA_IDLE_ID="idle_id";
    public static final String IDLE_CURRENT_COLLECT_STATE = "IDLE_CURRENT_COLLECT_STATE";

    private String idle_id=null;
    private String content=null;
    private String is_collection=null;
    private String goodsName=null;
    private String phoneNum=null;
    private boolean bLike=false;
    private int likeNum=0;
    private boolean isActDestroy=false;

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_detail;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = getIntent().getExtras();
        idle_id = bundle.getString(EXTRA_IDLE_ID);

        loadingInitData();  //初始化界面数据
    }

    @Override
    protected void initViews() {
        initListeners();
    }

    private void initListeners(){
        mLikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PreferenceUtils.hasKey(FreeGoodsDetailActivity.this, PreferenceUtils.Key.LOGIN) ||
                        !PreferenceUtils.getBoolean(FreeGoodsDetailActivity.this, PreferenceUtils.Key.LOGIN)) {
                    readyGo(LoginActivity.class);
                } else {
                        likeIdle(bLike);
                }
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    private void loadingInitData() {
        toggleShowLoading(true, null);
        if(NetUtils.isNetworkConnected(this.getApplicationContext())){
            ApiManager.getService(this.getApplicationContext()).getIdleInfo(idle_id, new Callback<GetIdleInfoRes>() {
                @Override
                public void success(GetIdleInfoRes getIdleInfoRes, Response response) {
                    if(!isActDestroy) {
                        toggleRestore();
                        initViewData(getIdleInfoRes);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                    toggleShowEmpty(true,null,clickGetDetailListener);
                }
            });
        }else{
            showNetWorkError();
            toggleNetworkError(true,clickGetDetailListener);
        }
    }

    private void initStl(){
        getSupportActionBar().setTitle(getResources().getString(R.string.detial));
        FragmentPagerItemAdapter pagerAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.detial, FreeGoodsDetailBottomInfoFragment.class, getBundles(0))
                .add(R.string.comment, FreeGoodsDetailBottomCommentFragment.class, getBundles(1))
                .create()
        );
        tabLayout.setCustomTabView(R.layout.good_detail_stl_title_tab, R.id.detail_stl_title_tv);
        detailViewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(detailViewPager);
    }

    private void likeIdle(final boolean like) {
        if(NetUtils.isNetworkConnected(this.getApplicationContext())){
            if(like) {     //取消点赞
                ApiManager.getService(this.getApplicationContext()).unLikeIdle(idle_id, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        showToast("取消点赞");
                        updateUI(like);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                    }
                });
            }else{            //添加点赞
                ApiManager.getService(this.getApplicationContext()).likeIdle(idle_id, new Callback<LikeIdleRes>() {
                    @Override
                    public void success(LikeIdleRes likeIdleRes, Response response) {
                        showToast("成功点赞");
                        //更新UI
                        updateUI(like);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                    }
                });
            }
        }else{
            showNetWorkError();
        }
    }



    private Bundle getBundles(int index){
        Bundle bundle = new Bundle();
        if(index < 2){
            if(index == 0){
                bundle.putString(TYPE,DETAIL);
                bundle.putString(EXTRA_IDLE_ID,idle_id);
                bundle.putString(FreeGoodsDetailBottomInfoFragment.EXTRA_CONTENT,content);
                bundle.putString(FreeGoodsDetailBottomInfoFragment.EXTRA_IS_COLLECTION,is_collection);
                bundle.putString(FreeGoodsDetailBottomInfoFragment.EXTRA_GOODS_NAME,goodsName);
                bundle.putString(FreeGoodsDetailBottomInfoFragment.EXTRA_PHONE_NUM,phoneNum);
            }else{
                bundle.putString(TYPE,COMMENT);
                bundle.putString(EXTRA_IDLE_ID,idle_id);
            }
        }
        return bundle;
    }

    private void initViewData(GetIdleInfoRes getIdleInfoRes) {
        CommonUtils.showImageWithGlide(this, mImageIv, getIdleInfoRes.getImage());
        mGoodsNameTv.setText(getIdleInfoRes.getName());
        mUserNameTv.setText(getIdleInfoRes.getUser_name());
        CommonUtils.showImageWithGlide(this, mUserImage, getIdleInfoRes.getUser_image());
        mPriceTv.setText(String.valueOf(getIdleInfoRes.getPrice()/ Float.valueOf(100))+getResources().getString(R.string.yuan));
        mViewTv.setText(String.valueOf(getIdleInfoRes.getView_number())+getResources().getString(R.string.interest)); //感兴趣的人
        mLikeTv.setText(String.valueOf(getIdleInfoRes.getLike_number())+getResources().getString(R.string.like));
        mDateTv.setText(getIdleInfoRes.getLast_update());

        if(Integer.parseInt(getIdleInfoRes.getLike_clicked())==1){ //已点赞状态
            mLikeIv.setImageResource(R.drawable.xiangqing_btn_dianzan);
            bLike=true;
        }else{
            mLikeIv.setImageResource(R.drawable.xiangqing_btn_dianzan_p);  //未点赞状态
            bLike=false;
        }

        likeNum=getIdleInfoRes.getLike_number();

        //保存信息传到子fragment中显示与请求
        content =getIdleInfoRes.getDescription();
        is_collection=getIdleInfoRes.getIs_collected();
        goodsName=getIdleInfoRes.getName();
        phoneNum=getIdleInfoRes.getPhone_num();
        initStl();  //必须在idle_id和content，is_collection都拿到后再传给fragment
    }

    private void updateLikeNum(int likeNum) {  //点赞实时更新点赞数，同时写入后台

    }


    private void updateUI(boolean like) {   //后端传送成功后更新前端ui
        if(like){  //取消点赞
            bLike = false;
            likeNum-=1;
            mLikeIv.setImageResource(R.drawable.xiangqing_btn_dianzan_p);
        }else {   //添加点赞
            bLike = true;
            mLikeIv.setImageResource(R.drawable.xiangqing_btn_dianzan);
            likeNum+=1;
        }
       mLikeTv.setText(String.valueOf(likeNum + getResources().getString(R.string.like)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActDestroy=true;
    }

    @Override
    public void onBackPressed() {
        if(is_collection != null ){
            //为“我的收藏”界面返回当前闲置的收藏状态
            Intent data = new Intent();
            data.putExtra(IDLE_CURRENT_COLLECT_STATE, Integer.parseInt(is_collection));
            setResult(RESULT_OK,data);
        }
        setResult(FreeGoodsLazyListFragment.RESULT_CODE);
        super.onBackPressed();
    }

    private View.OnClickListener clickGetDetailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadingInitData();
        }
    };
}
