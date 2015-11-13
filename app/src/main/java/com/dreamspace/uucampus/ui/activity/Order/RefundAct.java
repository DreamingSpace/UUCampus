package com.dreamspace.uucampus.ui.activity.Order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/22.
 */
public class RefundAct extends AbsActivity{
    @Bind(R.id.refund_money_tv)
    TextView refundMoneyTv;
    @Bind(R.id.submit_apply_icon_circle_iv)
    ImageView submitApplyCIv;
    @Bind(R.id.submit_apply_icon_line_iv)
    ImageView submitApplyLIv;
    @Bind(R.id.submit_apply_tv)
    TextView submitApplyTv;
    @Bind(R.id.yoyo_icon_circle_iv)
    ImageView yoyoCIv;
    @Bind(R.id.yoyo_icon_line_iv)
    ImageView yoyoLIv;
    @Bind(R.id.yoyo_tv)
    TextView yoyoTv;
    @Bind(R.id.pay_platform_icon_circle_iv)
    ImageView payPlatformCIv;
    @Bind(R.id.pay_platform_icon_line_iv)
    ImageView payPlatformLIv;
    @Bind(R.id.pay_platform_tv)
    TextView payPlatformTv;
    @Bind(R.id.refund_success_icon_circle_iv)
    ImageView refundSuccessCIv;
    @Bind(R.id.refund_success_tv)
    TextView refundSuccessTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_refund_detail;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.refund_detail));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){

    }
}
