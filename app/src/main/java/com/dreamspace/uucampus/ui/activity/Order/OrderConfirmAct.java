package com.dreamspace.uucampus.ui.activity.Order;

import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;


import butterknife.Bind;

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
    @Bind(R.id.coupon_not_used_iv)
    ImageView couponNotUseIv;
    @Bind(R.id.coupon_used_iv)
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

    @Override
    protected int getContentView() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void prepareDatas() {

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.order_confirm));
        priceBeforeReduceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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
                int currentValue = Integer.parseInt(goodNumTv.getText().toString());
                if(currentValue < 999){
                    goodNumTv.setText(++currentValue+"");
                }
            }
        });

        numReduceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(goodNumTv.getText().toString());
                if(currentValue > 1){
                    goodNumTv.setText(--currentValue+"");
                }
            }
        });

        couponNotUseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponNotUseIv.setVisibility(View.INVISIBLE);
                couponUseIv.setVisibility(View.VISIBLE);
            }
        });

        couponUseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponNotUseIv.setVisibility(View.VISIBLE);
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
}
