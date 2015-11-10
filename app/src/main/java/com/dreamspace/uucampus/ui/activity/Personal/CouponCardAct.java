package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.Share;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.Card;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/22.
 */
public class CouponCardAct extends AbsActivity{
    @Bind(R.id.share_btn)
    Button shareBtn;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.have_state_tv)
    TextView haveStateTv;
    @Bind(R.id.title1_tv)
    TextView title1Tv;
    @Bind(R.id.title2_tv)
    TextView title2Tv;
    @Bind(R.id.explain_tv)
    TextView explainTv;
    @Bind(R.id.content_ll)
    LinearLayout contentLl;

    private Share share;
    private boolean actDestory = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_coupon_card;
    }

    @Override
    protected void prepareDatas() {
        getCard();

        share = new Share(this);
        share.ShareInQQ("标题", "内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInWechat("标题", "内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInQZone("标题", "内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInWechatCircle("标题", "内容", "http://www.baidu.com", R.drawable.banner1);
        share.ShareInSina("内容", R.drawable.banner1);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.my_coupon_card));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return contentLl;
    }

    private void initListeners(){
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.getController().openShare(CouponCardAct.this, false);
            }
        });
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

    private void getCard(){
        toggleShowLoading(true, null);
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            toggleNetworkError(true, getCardClickListener);
            return;
        }

        ApiManager.getService(this).checkCard(new Callback<Card>() {
            @Override
            public void success(Card card, Response response) {
                if(card != null && !actDestory){
                    toggleRestore();
                    setCardInfoIntoViews(card);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ErrorRes errorRes = (ErrorRes) error.getBodyAs(ErrorRes.class);
                if(errorRes.getCode() == 404){
                    //没有优惠卡
                    toggleRestore();
                    setNoCardViews();
                }else{
                    toggleShowEmpty(true, null, getCardClickListener);
                }
            }
        });
    }

    private void setCardInfoIntoViews(Card card){
        timeTv.setVisibility(View.VISIBLE);
        haveStateTv.setText(getString(R.string.own));
        title1Tv.setText(card.getTitle1());
        title2Tv.setText(card.getTitle2());
        explainTv.setText(card.getExplain());
        String[] strings = card.getStart_date().split(" ");
        String dateStart = strings[0];
        String[] strings1 = card.getEnd_date().split(" ");
        String dateEnd = strings1[0];
        timeTv.setText(dateStart + "~" + dateEnd);
        shareBtn.setBackground(getResources().getDrawable(R.drawable.share_btn_unenable));
        shareBtn.setEnabled(false);//不可点击
        shareBtn.setTextColor(getResources().getColor(R.color.white));
    }

    private void setNoCardViews(){
        timeTv.setVisibility(View.INVISIBLE);
        haveStateTv.setText(getString(R.string.not_have));
    }

    private View.OnClickListener getCardClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCard();
        }
    };

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }
}
