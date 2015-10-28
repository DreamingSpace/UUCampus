package com.dreamspace.uucampus.ui.activity.Order;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.hedgehog.ratingbar.RatingBar;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

import butterknife.Bind;

/**
 * Created by Lx on 2015/10/22.
 */
public class OrderDetailAct extends AbsActivity{
    @Bind(R.id.good_ll)
    LinearLayout goodLl;
    @Bind(R.id.shop_name_rl)
    RelativeLayout shopRl;
    @Bind(R.id.comment_ll)
    LinearLayout commentLl;
    @Bind(R.id.good_bottom_divider)
    View goodLlBottomDivider;

    private Button paidBtn;
    private Button applyRefundBtn;
    private Button commentBtn;
    private View refundView;
    private TextView qrCodeTv;
    private TextView inRefundTv;
    private TextView alreadyRefundTv;
    private ImageView qrCodeIv;

    public static final String ORDER_STATE = "order_state";

    //订单状态
    public static final int UNPAID = 0;
    public static final int UNCONSUME = 1;
    public static final int UNCOMMENT = 2;
    public static final int IN_REFUND = 3;
    public static final int ALREADY_REFUND = 4;
    public static final int ALREADY_COMMENT = 5;

    private int orderState;
    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void prepareDatas() {
        Bundle data = getIntent().getExtras();
        orderState = data.getInt(ORDER_STATE);
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.order_detail));
        switch(orderState){
            case UNPAID:
                initUnpaidViews();
                initUnpaidListeners();
                break;

            case UNCONSUME:
                initUnconsumeViews();
                initUnconsumeListeners();
                break;

            case UNCOMMENT:
                initUncommentViews();
                initUncommentListeners();
                break;

            case IN_REFUND:
                initRefundViews();
                initRefundListeners();
                break;

            case ALREADY_REFUND:
                initRefundViews();
                initRefundListeners();
                break;

            case ALREADY_COMMENT:
                initAlreadyCommentViews();
                break;
        }

        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void initListeners(){

    }

    //初始化未付款订单状态视图
    private void initUnpaidViews(){
        View unpaidView = getLayoutInflater().inflate(R.layout.order_detail_view_unpaid_state,null);
        paidBtn = (Button) unpaidView.findViewById(R.id.pay_btn);

        goodLl.addView(unpaidView,getInflatePosition());
        commentLl.setVisibility(View.GONE);
    }

    private void initUnpaidListeners(){
        paidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //初始化未消费订单状态视图
    private void initUnconsumeViews(){
        View unconsumeView = getLayoutInflater().inflate(R.layout.order_detail_view_unconsume_state,null);
        qrCodeTv = (TextView) unconsumeView.findViewById(R.id.order_password_tv);
        qrCodeIv = (ImageView) unconsumeView.findViewById(R.id.qrcode_iv);
        applyRefundBtn = (Button) unconsumeView.findViewById(R.id.apply_refund_btn);

        //获取二维码宽度，按照此宽度生成二维码文件
        int qrCodeWidth = getResources().getDimensionPixelSize(R.dimen.qrcode_width);
        //生成二维码文件，指定编码方式为utf-8
        File qrcodeFile = QRCode.from("李鑫").withSize(qrCodeWidth,qrCodeWidth).withCharset("UTF-8").file();
        Glide.with(this)
                .load(qrcodeFile)
                .centerCrop()
                .override(qrCodeWidth, qrCodeWidth)
                .into(qrCodeIv);
        goodLl.addView(unconsumeView, getInflatePosition());
        commentLl.setVisibility(View.GONE);
    }

    private void initUnconsumeListeners(){
        applyRefundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //初始化未评价订单状态视图
    private void initUncommentViews(){
        View uncommentView = getLayoutInflater().inflate(R.layout.order_detail_view_uncomment_state,null);
        commentBtn = (Button) uncommentView.findViewById(R.id.comment_btn);

        goodLl.addView(uncommentView, getInflatePosition());
        commentLl.setVisibility(View.GONE);
    }

    private void initUncommentListeners(){
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(CommentAct.class);
            }
        });
    }


    //初始化退款订单状态视图（退款中，已退款)
    private void initRefundViews(){
        refundView = getLayoutInflater().inflate(R.layout.order_detail_view_refund_state,null);
        inRefundTv = (TextView) refundView.findViewById(R.id.in_refund_tv);
        alreadyRefundTv = (TextView) refundView.findViewById(R.id.already_refund_tv);
        if(orderState == IN_REFUND){
            inRefundTv.setVisibility(View.VISIBLE);
            alreadyRefundTv.setVisibility(View.INVISIBLE);
        }else{
            inRefundTv.setVisibility(View.INVISIBLE);
            alreadyRefundTv.setVisibility(View.VISIBLE);
        }

        goodLl.addView(refundView,getInflatePosition());
        commentLl.setVisibility(View.GONE);
    }

    private void initRefundListeners(){
        refundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(RefundAct.class);
            }
        });
    }

    //初始化已评价订单状态视图（无控件需要添加监听器）
    private void initAlreadyCommentViews(){
        commentLl.setVisibility(View.VISIBLE);

        RatingBar ratingBar = (RatingBar) commentLl.findViewById(R.id.comment_rating_bar);
        ratingBar.setmClickable(false);
    }

    //返回要添加的view的位置
    private int getInflatePosition(){
        return goodLl.indexOfChild(goodLlBottomDivider);
    }
}
