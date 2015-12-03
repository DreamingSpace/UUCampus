package com.dreamspace.uucampus.ui.activity.Order;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.Card;
import com.dreamspace.uucampus.model.api.CreateOrderReq;
import com.dreamspace.uucampus.model.api.CreateOrderRes;
import com.dreamspace.uucampus.ui.activity.Personal.CouponCardAct;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;


import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/20.
 */
public class OrderConfirmAct extends AbsActivity{
    @Bind(R.id.good_name_tv)
    TextView goodNameTv;
    @Bind(R.id.good_single_price_tv)
    TextView singlePriceTv;
    @Bind(R.id.good_num_add_ll)
    LinearLayout numAddLl;
    @Bind(R.id.good_num_reduce_ll)
    LinearLayout numReduceLl;
    @Bind(R.id.good_num_tv)
    TextView goodNumTv;
    @Bind(R.id.coupon_use_iv)
    ImageView couponUseIv;
    @Bind(R.id.get_coupon_btn)
    Button getCouponBtn;
    @Bind(R.id.total_price_tv)
    TextView totalPriceTv;
    @Bind(R.id.total_price_before_reduce_tv)
    TextView priceBeforeReduceTv;
    @Bind(R.id.bundling_phone_tv)
    TextView phoneTv;
    @Bind(R.id.campus_tv)
    TextView campusTv;
    @Bind(R.id.remark_et)
    EditText remarkEt;
    @Bind(R.id.submit_order_btn)
    Button submitBtn;
//    @Bind(R.id.coupon_use_rl)
//    RelativeLayout couponUseRl;

    public static final String GOOD_NAME = "good_name";
    public static final String PRICE = "price";
    public static final String DISCOUNT = "discount";
    public static final String GOOD_ID = "good_id";

    private String goodName;
    private String goodId;
    private float price;
    private float discount;
    private boolean actDestory = false;
    private boolean useCard = false;//用来判断用户是否使用优惠卡
    private int quantity = 1;//用户当前要购买的商品数量
    private ProgressDialog progressDialog;

    private static final int GO_PAY_ORDER = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = getIntent().getExtras();
        goodName = bundle.getString(GOOD_NAME);
        goodId = bundle.getString(GOOD_ID);
        price = Float.parseFloat(bundle.getString(PRICE));
        discount = Float.parseFloat(bundle.getString(DISCOUNT));

        checkCard();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.order_confirm));
        priceBeforeReduceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        phoneTv.setText(PreferenceUtils.getString(this, PreferenceUtils.Key.PHONE));
        campusTv.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.LOCATION));//之后直接从preference里调用
        goodNumTv.setText(quantity + "");
        goodNameTv.setText(goodName);
        singlePriceTv.setText(getString(R.string.RMB) + price / 100);
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){
        numAddLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity < 999){
                    goodNumTv.setText(++quantity+"");
                    priceBeforeReduceTv.setText(getString(R.string.RMB) + price / 100 * quantity);
                    if(useCard){
                        totalPriceTv.setText(getString(R.string.RMB) + (price - discount) / 100 * quantity);
                    }else{
                        totalPriceTv.setText(getString(R.string.RMB) + price / 100 * quantity);
                    }
                }
            }
        });

        numReduceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1){
                    goodNumTv.setText(--quantity+"");
                    priceBeforeReduceTv.setText(getString(R.string.RMB) + price * quantity / 100);
                    if(useCard){
                        totalPriceTv.setText(getString(R.string.RMB) + (price - discount) / 100 * quantity);
                    }else{
                        totalPriceTv.setText(getString(R.string.RMB) + price * quantity / 100);
                    }
                }
            }
        });

        //提交订单
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDialog();
                createOrder();
            }
        });
    }

    //初始化用户有优惠卡时候的视图
    private void initHasCardViews(){
        useCard = true;
        priceBeforeReduceTv.setText(getString(R.string.RMB) + price / 100 * quantity);
        totalPriceTv.setText(getString(R.string.RMB) + (price - discount) / 100 * quantity);
        getCouponBtn.setVisibility(View.INVISIBLE);
    }

    private void initNoCardViews(){
        useCard = false;
        couponUseIv.setVisibility(View.INVISIBLE);
        getCouponBtn.setVisibility(View.VISIBLE);
        priceBeforeReduceTv.setText(getString(R.string.RMB) + price / 100 * quantity);
        totalPriceTv.setText(getString(R.string.RMB) + price /100 * quantity);
        priceBeforeReduceTv.setVisibility(View.INVISIBLE);
        getCouponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(CouponCardAct.class);
            }
        });
    }

    private void checkCard(){
        if(!NetUtils.isNetworkConnected(this)){
            showToast(getString(R.string.check_network_to_get_card_info));
            initNoCardViews();
            return;
        }

        ApiManager.getService(this).checkCard(new Callback<Card>() {
            @Override
            public void success(Card card, Response response) {
                if (card != null && !actDestory) {
                    initHasCardViews();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (((ErrorRes) error.getBodyAs(ErrorRes.class)).getCode() == 404 && !actDestory) {
                    //没有优惠卡
                    initNoCardViews();
                } else if(!actDestory){
                    showInnerError(error);
                    initNoCardViews();
                }
            }
        });
    }

    //创建订单
    private void createOrder(){
        progressDialog.show();
        if(!NetUtils.isNetworkConnected(this)){
            progressDialog.dismiss();
            showNetWorkError();
            return;
        }

        CreateOrderReq orderReq = new CreateOrderReq();
        orderReq.setGood_id(goodId);
        orderReq.setQuantity(quantity);
        orderReq.setRemark(remarkEt.getText().toString());

        ApiManager.getService(this).createOrder(orderReq, new Callback<CreateOrderRes>() {
            @Override
            public void success(CreateOrderRes createOrderRes, Response response) {
                if (createOrderRes != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(OrderPayAct.ORDER_NAME, goodName);
                    bundle.putString(OrderPayAct.ORDER_ID, createOrderRes.getOrder_id());
                    bundle.putFloat(OrderPayAct.REST_TO_PAY, useCard ? quantity * (price - discount) : quantity * price);
                    bundle.putFloat(OrderPayAct.ORDER_TOTAL_PRICE, quantity * price);
                    progressDialog.dismiss();
                    readyGoForResult(OrderPayAct.class, GO_PAY_ORDER, bundle);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                showInnerError(error);
            }
        });
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setContent(getString(R.string.creating_order));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GO_PAY_ORDER && resultCode == RESULT_OK){
            //支付成功
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }
}
