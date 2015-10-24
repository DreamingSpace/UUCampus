package com.dreamspace.uucampus.ui.activity.Personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.common.Share;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/22.
 */
public class CouponCardAct extends AbsActivity{
    @Bind(R.id.share_btn)
    Button shareBtn;
    @Bind(R.id.time_tv)
    TextView timeTv;

    private Share share;
    @Override
    protected int getContentView() {
        return R.layout.activity_my_coupon_card;
    }

    @Override
    protected void prepareDatas() {
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
}
