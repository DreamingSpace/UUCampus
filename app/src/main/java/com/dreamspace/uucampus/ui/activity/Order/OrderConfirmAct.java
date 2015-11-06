package com.dreamspace.uucampus.ui.activity.Order;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.common.utils.PreferenceUtils;
import com.dreamspace.uucampus.model.ErrorRes;
import com.dreamspace.uucampus.model.api.Card;
import com.dreamspace.uucampus.ui.base.AbsActivity;


import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/20.
 */
public class OrderConfirmAct extends AbsActivity{
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

    public static final String GOOD_NAME = "good_name";
    public static final String PRICE = "price";
    public static final String DISCOUNT = "discount";
    private String goodName;
    private float price;
    private float discount;
    private boolean actDestory = false;
    private boolean useCard = false;//用来判断用户是否使用优惠卡
    private int quantity = 1;//用户当前要购买的商品数量

    @Override
    protected int getContentView() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void prepareDatas() {
        Bundle bundle = getIntent().getExtras();
        goodName = bundle.getString(GOOD_NAME);
        price = Float.parseFloat(bundle.getString(PRICE));
        discount = Float.parseFloat(bundle.getString(DISCOUNT));
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.order_confirm));
        priceBeforeReduceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        phoneTv.setText(PreferenceUtils.getString(this,PreferenceUtils.Key.PHONE));
        campusTv.setText("东大九龙湖校区");//之后直接从preference里调用
        goodNumTv.setText(quantity + "");
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
                    priceBeforeReduceTv.setText(price * quantity + "");
                    if(useCard){
                        totalPriceTv.setText((price - discount) * quantity + "");
                    }else{
                        totalPriceTv.setText(price * quantity + "");
                    }
                }
            }
        });

        numReduceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1){
                    goodNumTv.setText(--quantity+"");
                    priceBeforeReduceTv.setText(price * quantity + "");
                    if(useCard){
                        totalPriceTv.setText((price - discount) * quantity + "");
                    }else{
                        totalPriceTv.setText(price * quantity + "");
                    }
                }
            }
        });

        couponUseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponUseIv.setVisibility(View.INVISIBLE);
            }
        });

        //提交订单
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //初始化用户有优惠卡时候的视图
    private void initHasCardViews(){
        couponUseIv.setImageDrawable(getResources().getDrawable(R.drawable.orderdetail_btn_chose_h));
        useCard = true;
        priceBeforeReduceTv.setText(price * quantity + "");
        totalPriceTv.setText((price - discount) * quantity + "");
        getCouponBtn.setVisibility(View.INVISIBLE);
        couponUseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(useCard){
                    couponUseIv.setImageDrawable(getResources().getDrawable(R.drawable.orderdetail_btn_chose_n));
                    useCard = false;
                    priceBeforeReduceTv.setVisibility(View.INVISIBLE);
                    totalPriceTv.setText(price * quantity + "");
                }else{
                    couponUseIv.setImageDrawable(getResources().getDrawable(R.drawable.orderdetail_btn_chose_h));
                    useCard = true;
                    priceBeforeReduceTv.setVisibility(View.VISIBLE);
                    totalPriceTv.setText((price - discount) * quantity + "");
                }
            }
        });
    }

    private void initNoCardViews(){
        useCard = false;
        couponUseIv.setVisibility(View.INVISIBLE);
        priceBeforeReduceTv.setText(price * quantity + "");
        totalPriceTv.setText(price * quantity + "");
        priceBeforeReduceTv.setVisibility(View.INVISIBLE);
        getCouponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 获取优惠卡
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
                if(card != null && !actDestory){
                    initHasCardViews();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(((ErrorRes)error.getBodyAs(ErrorRes.class)).getCode() == 404){
                    //没有优惠卡
                    initNoCardViews();
                }else{
                    showInnerError(error);
                    initNoCardViews();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }
}
