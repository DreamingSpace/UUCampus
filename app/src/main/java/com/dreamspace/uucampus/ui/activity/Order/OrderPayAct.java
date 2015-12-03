package com.dreamspace.uucampus.ui.activity.Order;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.PayOrderReq;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.google.gson.JsonElement;
import com.pingplusplus.android.PaymentActivity;

import org.json.JSONObject;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/11/7.
 * 订单支付界面，进入此界面需要传入order_name,order_id,order_total_price,rest_to_pay_price
 */
public class OrderPayAct extends AbsActivity{
    @Bind(R.id.order_name_tv)
    TextView orderNameTv;
    @Bind(R.id.order_price_tv)
    TextView orderPriceTv;
    @Bind(R.id.rest_pay_tv)
    TextView restPayTv;
    @Bind(R.id.already_reduce_tv)
    TextView reduceTv;
    @Bind(R.id.alipay_rl)
    RelativeLayout alipayRl;
    @Bind(R.id.weichat_pay_rl)
    RelativeLayout weichatpayRl;
    @Bind(R.id.pay_btn)
    Button payBtn;
    @Bind(R.id.alipay_iv)
    ImageView alipayIv;
    @Bind(R.id.weichat_pay_iv)
    ImageView weichatpayIv;

    private int payWay;

    private String orderName;
    private String orderId;
    private float orderPrice;
    private float restToPay;
    private boolean actDestory = false;
    private ProgressDialog progressDialog;

    private static final int ALIPAY = 1;
    private static final int WEICHATPAY = 2;
    public static final String ORDER_NAME = "ORDER_NAME";
    public static final String ORDER_TOTAL_PRICE = "ORDER_TOTAL_PRICE";
    public static final String REST_TO_PAY = "REST_TO_PAY";
    public static final String ORDER_ID = "ORDER_ID";
    private static final int REQUEST_CODE_PAYMENT = 1;
    @Override
    protected int getContentView() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = getIntent().getExtras();
        orderName = bundle.getString(ORDER_NAME);
        orderId = bundle.getString(ORDER_ID);
        orderPrice = bundle.getFloat(ORDER_TOTAL_PRICE);
        restToPay = bundle.getFloat(REST_TO_PAY);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.order_confirm ));
        orderNameTv.setText(orderName);
        orderPriceTv.setText(getString(R.string.RMB) + orderPrice / 100);
        restPayTv.setText(getString(R.string.RMB) + restToPay / 100);
        reduceTv.setText(getString(R.string.already_reduce) + getString(R.string.RMB) + (orderPrice - restToPay) / 100);
        initListeners();
    }

    private void initListeners(){
        alipayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay != ALIPAY) {
                    payWay = ALIPAY;
                    alipayIv.setImageDrawable(getResources().getDrawable(R.drawable.dingdan_btn_get));
                    weichatpayIv.setImageDrawable(getResources().getDrawable(R.drawable.dingdan_btn_select));
                } else {
                    payWay = -1;
                    alipayIv.setImageDrawable(getResources().getDrawable(R.drawable.dingdan_btn_select));
                }
            }
        });

        weichatpayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay != WEICHATPAY) {
                    payWay = WEICHATPAY;
                    weichatpayIv.setImageDrawable(getResources().getDrawable(R.drawable.dingdan_btn_get));
                    alipayIv.setImageDrawable(getResources().getDrawable(R.drawable.dingdan_btn_select));
                } else {
                    payWay = -1;
                    weichatpayIv.setImageDrawable(getResources().getDrawable(R.drawable.dingdan_btn_select));
                }
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payWay != ALIPAY && payWay != WEICHATPAY) {
                    showToast(getString(R.string.plz_select_pay_way));
                } else {
                    initProgressDialog();
                    progressDialog.show();
                    payOrder();
                }
            }
        });
    }

    private void payOrder(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            progressDialog.dismiss();
            return;
        }

        PayOrderReq payOrderReq = new PayOrderReq();
        if(payWay == ALIPAY){
            payOrderReq.setChannel(getString(R.string.alipay));
        }else if(payWay == WEICHATPAY){
            payOrderReq.setChannel(getString(R.string.wxpay));
        }
        ApiManager.getService(this).payOrder(orderId, payOrderReq, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement data, Response response) {
                if (data != null && !actDestory) {
                    String json = data.toString();
                    Intent intent = new Intent();
                    String packageName = getPackageName();
                    ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                    intent.setComponent(componentName);
                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data.toString());
                    progressDialog.dismiss();
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }

        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             */
                if(result.equals("success")){
                    setResult(RESULT_OK);
                    //进入订单详情页面
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderDetailAct.ORDER_ID,orderId);
                    readyGoThenKill(OrderDetailAct.class,bundle);
                }else if(result.equals("fail")){
                    showToast(getString(R.string.pay_fail));
                }
//                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
//                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                System.out.println(result);
//                System.out.println(errorMsg);
//                System.out.println(extraMsg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setContent(getString(R.string.plz_wait));
    }
}
