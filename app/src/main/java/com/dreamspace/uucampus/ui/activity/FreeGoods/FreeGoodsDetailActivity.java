package com.dreamspace.uucampus.ui.activity.FreeGoods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.Share;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.GetIdleInfoRes;
import com.dreamspace.uucampus.model.api.LikeIdleRes;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsDetailBottomCommentFragment;
import com.dreamspace.uucampus.ui.fragment.FreeGoods.FreeGoodsDetailBottomInfoFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wufan on 2015/9/20.
 */
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

    public static final String TYPE = "type";
    public static final String DETAIL = "detail";
    public static final String COMMENT= "comment";
    public static final String EXTRA_IDLE_ID="idle_id";

    private String idle_id=null;
    private String content=null;
    private String is_collection=null;
    private boolean bLike=false;
    private int likeNum=0;
    private Share share;
    private boolean isActDestroy=false;

    @Override
    protected int getContentView() {
        return R.layout.activity_free_goods_detail;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = getIntent().getExtras();
        idle_id = bundle.getString(EXTRA_IDLE_ID);

        //初始化分享内容
        share = new Share(this);
        share.ShareInQQ("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInWechat("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInQZone("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInWechatCircle("good标题", "good内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInSina("good内容", R.drawable.banner1);

        loadingInitData();  //初始化界面数据
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void loadingInitData() {
        if(NetUtils.isNetworkConnected(this.getApplicationContext())){
            ApiManager.getService(this.getApplicationContext()).getIdleInfo(idle_id, new Callback<GetIdleInfoRes>() {
                @Override
                public void success(GetIdleInfoRes getIdleInfoRes, Response response) {
                    if(!isActDestroy) {
                        initViewData(getIdleInfoRes);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    showInnerError(error);
                }
            });
        }else{
            showNetWorkError();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.good_detial_act_menu, menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = share.getController().getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
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


    @OnClick(R.id.free_good_detail_like_no_click_iv)
    void like(){
        if(bLike){
            mLikeIv.setImageResource(R.drawable.xiangqing_btn_dianzan_p);
            bLike=false;
            likeIdle(bLike);  //取消点赞
        }else{
            mLikeIv.setImageResource(R.drawable.xiangqing_btn_dianzan);
            bLike=true;
            likeIdle(bLike);  //点赞
        }
    }

    private void likeIdle(boolean bLike) {
        final ProgressDialog pd =ProgressDialog.show(this, "", "正在点赞", true, false);
        if(NetUtils.isNetworkConnected(this.getApplicationContext())){
            if(bLike) {
                ApiManager.getService(this.getApplicationContext()).likeIdle(idle_id, new Callback<LikeIdleRes>() {
                    @Override
                    public void success(LikeIdleRes likeIdleRes, Response response) {
                        showToast("成功点赞");
                        likeNum+=1;
                        updateLikeNum(likeNum);
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }else{
                ApiManager.getService(this.getApplicationContext()).unLikeIdle(idle_id, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        likeNum-=1;
                        updateLikeNum(likeNum);
                        showToast("取消点赞");
                        pd.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showInnerError(error);
                        pd.dismiss();
                    }
                });
            }
        }else{
            showNetWorkError();
            pd.dismiss();
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
        mPriceTv.setText(String.valueOf(getIdleInfoRes.getPrice())+getResources().getString(R.string.yuan));
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

//        TLog.i("闲置详情初始化：","image:"+getIdleInfoRes.getUser_image()+"  like_clicked:"+getIdleInfoRes.getLike_clicked()
//                +"  is_collected:"+getIdleInfoRes.getIs_collected());
        initStl();  //必须在idle_id和content，is_collection都拿到后再传给fragment
    }

    private void updateLikeNum(int likeNum) {  //点赞实时更新点赞数，同时写入后台
        mLikeTv.setText(String.valueOf(likeNum+getResources().getString(R.string.like)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActDestroy=true;
    }
}
