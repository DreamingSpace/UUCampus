package com.dreamspace.uucampus.ui.activity.Order;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.api.ApiManager;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.common.utils.NetUtils;
import com.dreamspace.uucampus.model.api.CommonStatusRes;
import com.dreamspace.uucampus.model.api.OrderDetail;
import com.dreamspace.uucampus.ui.activity.Market.GoodDetailAct;
import com.dreamspace.uucampus.ui.activity.Market.ShopShowGoodsAct;
import com.dreamspace.uucampus.ui.base.AbsActivity;
import com.dreamspace.uucampus.ui.dialog.MsgDialog;
import com.dreamspace.uucampus.ui.dialog.ProgressDialog;
import com.dreamspace.uucampus.widget.RatingBar;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Lx on 2015/10/22.
 * 订单详情界面，进入需要传入order_id
 */
public class OrderDetailAct extends AbsActivity{
    @Bind(R.id.good_ll)
    LinearLayout goodLl;
    @Bind(R.id.good_rl)
    RelativeLayout goodRl;
    @Bind(R.id.shop_name_rl)
    RelativeLayout shopRl;
    @Bind(R.id.comment_ll)
    LinearLayout commentLl;
    @Bind(R.id.good_bottom_divider)
    View goodLlBottomDivider;
    @Bind(R.id.good_name_tv)
    TextView goodNameTv;
    @Bind(R.id.good_image_iv)
    ImageView goodImageIv;
    @Bind(R.id.price_before_reduce_tv)
    TextView priceBeforeReduceTv;
    @Bind(R.id.total_price_tv)
    TextView totalPriceTv;
    @Bind(R.id.shop_image_civ)
    CircleImageView shopImageCiv;
    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;
    @Bind(R.id.order_id_tv)
    TextView orderIdTv;
    @Bind(R.id.paid_time_tv)
    TextView orderTimeTv;
    @Bind(R.id.phone_num_tv)
    TextView buyerPhoneTv;
    @Bind(R.id.campus_tv)
    TextView locationTv;
    @Bind(R.id.discount_tv)
    TextView discountTv;
    @Bind(R.id.remark_tv)
    TextView remarkTv;
    @Bind(R.id.content_sv)
    ScrollView contentSv;

    private Button paidBtn;
    private Button applyRefundBtn;
    private Button commentBtn;
    private View refundView;
    private TextView qrCodeTv;
    private TextView inRefundTv;
    private TextView alreadyRefundTv;
    private ImageView qrCodeIv;
    private String order_id;
    private MsgDialog msgDialog;
    private ProgressDialog progressDialog;

    private boolean actDestory = false;
    private boolean orderStateChange = false;
    private OrderDetail mOrderDetail;

    public static final String ORDER_ID = "ORDER_ID";
    private static final int GO_PAY = 1;
    private static final int GO_COMMENT = 2;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void prepareDatas() {
        Bundle data = getIntent().getExtras();
        order_id = data.getString(ORDER_ID);
        getOrderDetail();
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle(getString(R.string.order_detail));
        initListeners();
    }

    @Override
    protected View getLoadingTargetView() {
        return contentSv;
    }

    private void initListeners(){

    }

    //初始化未付款订单状态视图
    private void initUnpaidViews(){
        View unpaidView = getLayoutInflater().inflate(R.layout.order_detail_view_unpaid_state,null);
        paidBtn = (Button) unpaidView.findViewById(R.id.pay_btn);

        goodLl.addView(unpaidView, getInflatePosition());
        commentLl.setVisibility(View.GONE);
    }

    private void initUnpaidListeners(){
        paidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入支付界面
                intPayAct(mOrderDetail.getGood().getName(), mOrderDetail.get_id(),
                        mOrderDetail.getQuantity() * mOrderDetail.getGood().getOriginal_price(), mOrderDetail.getTotal_price());
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
        File qrcodeFile = QRCode.from(mOrderDetail.getCode()).withSize(qrCodeWidth,qrCodeWidth).withCharset("UTF-8").file();
        Glide.with(this)
                .load(qrcodeFile)
                .centerCrop()
                .override(qrCodeWidth, qrCodeWidth)
                .into(qrCodeIv);
        qrCodeTv.setText(getString(R.string.order_qr_code_password) + mOrderDetail.getCode());
        goodLl.addView(unconsumeView, getInflatePosition());
        commentLl.setVisibility(View.GONE);
    }

    private void initUnconsumeListeners(){
        applyRefundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCancelOrderMsgDialog();
                msgDialog.show();
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
                //进入评论界面
                inCommentAct(mOrderDetail.get_id(), mOrderDetail.getGood().get_id());
            }
        });
    }


    //初始化退款订单状态视图（退款中，已退款)
    private void initRefundViews(){
        refundView = getLayoutInflater().inflate(R.layout.order_detail_view_refund_state,null);
        inRefundTv = (TextView) refundView.findViewById(R.id.in_refund_tv);
        alreadyRefundTv = (TextView) refundView.findViewById(R.id.already_refund_tv);
        if(mOrderDetail.getStatus() == -1){
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
    }

    //初始化已评价订单状态视图（无控件需要添加监听器）
    private void initAlreadyCommentViews(){
        commentLl.setVisibility(View.VISIBLE);

        RatingBar ratingBar = (RatingBar) commentLl.findViewById(R.id.comment_rating_bar);
        ratingBar.setmClickable(false);
        CircleImageView civ = (CircleImageView) commentLl.findViewById(R.id.user_avatar_civ);
        TextView userNameTv = (TextView) commentLl.findViewById(R.id.user_name_tv);
        TextView timeTv = (TextView) commentLl.findViewById(R.id.publis_time_tv);
        TextView contentTv = (TextView) commentLl.findViewById(R.id.comment_content_tv);
        TextView userfulTv = (TextView) commentLl.findViewById(R.id.userful_tv);
        CommonUtils.showImageWithGlideInCiv(this,civ,mOrderDetail.getComment().getUser().getImage());
        userNameTv.setText(mOrderDetail.getComment().getUser().getName());
        timeTv.setText(mOrderDetail.getComment().getDate());
        contentTv.setText(mOrderDetail.getComment().getContent());
        userfulTv.setText(getString(R.string.useful) + mOrderDetail.getComment().getUseful_number() + getString(R.string.useful2));
        ratingBar.setStar(mOrderDetail.getComment().getScore());
    }

    //返回要添加的view的位置
    private int getInflatePosition(){
        return goodLl.indexOfChild(goodLlBottomDivider);
    }

    //获取订单详情
    private void getOrderDetail(){
        toggleShowLoading(true,null);
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            toggleNetworkError(true,getOrderDetailClickListener);
            return;
        }

        ApiManager.getService(this).getOrderDetail(order_id, new Callback<OrderDetail>() {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                if(orderDetail != null && !actDestory){
                    toggleRestore();
                    mOrderDetail = orderDetail;
                    setOrderInfoIntoViews();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
                toggleShowEmpty(true,null,getOrderDetailClickListener);
            }
        });
    }

    //取消订单(退款)
    private void cancelOrder(){
        if(!NetUtils.isNetworkConnected(this)){
            showNetWorkError();
            return;
        }
        ApiManager.getService(this).cancelOrder(mOrderDetail.get_id(), new Callback<CommonStatusRes>() {
            @Override
            public void success(CommonStatusRes commonStatusRes, Response response) {
                if (commonStatusRes != null && !actDestory) {
                    progressDialog.dismiss();
                    showToast(getString(R.string.cancel_order_success));
                    resetViews();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                showInnerError(error);
            }
        });
    }

    private void setOrderInfoIntoViews(){
        switch (mOrderDetail.getStatus()){
            case -1://退款中
                initRefundViews();
                initRefundListeners();
                break;

            case 0://订单取消(已退款)
                initRefundViews();
                initRefundListeners();
                break;

            case 1://未付款
                initUnpaidViews();
                initUnpaidListeners();
                break;

            case 2://未消费
                initUnconsumeViews();
                initUnconsumeListeners();
                break;

            case 3://未评价
                initUncommentViews();
                initUncommentListeners();
                break;

            case 4://已评价
                initAlreadyCommentViews();
                break;
        }

        //把订单的数据填写到view中
        CommonUtils.showImageWithGlide(this, goodImageIv, mOrderDetail.getGood().getImage());
        if(mOrderDetail.getQuantity() > 1){
            goodNameTv.setText(mOrderDetail.getGood().getName() + "*" + mOrderDetail.getQuantity());
        }else{
            goodNameTv.setText(mOrderDetail.getGood().getName());
        }
        float pricetotalBR = (float)(mOrderDetail.getGood().getOriginal_price() * mOrderDetail.getQuantity()) / 100;
        priceBeforeReduceTv.setText(getString(R.string.RMB) + pricetotalBR);
        totalPriceTv.setText(getString(R.string.RMB) + (float) mOrderDetail.getTotal_price() / 100);
        CommonUtils.showImageWithGlideInCiv(this, shopImageCiv, mOrderDetail.getShop().getShop_image());
        shopNameTv.setText(mOrderDetail.getShop().getName());
        orderIdTv.setText(mOrderDetail.get_id());
        orderTimeTv.setText(mOrderDetail.getTime());
        buyerPhoneTv.setText(mOrderDetail.getBuyer().getPhone_num());
        locationTv.setText(mOrderDetail.getBuyer().getLocation());
        float priceDiscout = (float)(mOrderDetail.getGood().getOriginal_price() * mOrderDetail.getQuantity() - mOrderDetail.getTotal_price()) / 100;
        discountTv.setText(getString(R.string.RMB) + priceDiscout);
        remarkTv.setText(mOrderDetail.getRemark());

        goodRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(GoodDetailAct.GOOD_ID, mOrderDetail.getGood().get_id());
                readyGo(GoodDetailAct.class, bundle);
            }
        });

        shopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ShopShowGoodsAct.SHOP_ID, mOrderDetail.getShop().getShop_id());
                bundle.putString(ShopShowGoodsAct.SHOP_NAME, mOrderDetail.getShop().getName());
                readyGo(ShopShowGoodsAct.class, bundle);
            }
        });
    }

    private void intPayAct(String order_name,String order_id,float total_price,float rest_to_pay){
        Bundle bundle = new Bundle();
        bundle.putString(OrderPayAct.ORDER_NAME, order_name);
        bundle.putString(OrderPayAct.ORDER_ID, order_id);
        bundle.putFloat(OrderPayAct.ORDER_TOTAL_PRICE, total_price);
        bundle.putFloat(OrderPayAct.REST_TO_PAY, rest_to_pay);
        readyGoForResult(OrderPayAct.class, GO_PAY, bundle);
    }

    private void inCommentAct(String order_id,String good_id){
        Bundle bundle = new Bundle();
        bundle.putString(CommentAct.ORDER_ID, order_id);
        bundle.putString(CommentAct.GOOD_ID, good_id);
        readyGoForResult(CommentAct.class, GO_COMMENT, bundle);
    }

    //初始化退款确认框
    private void initCancelOrderMsgDialog(){
        if(msgDialog != null){
            return;
        }
        msgDialog = new MsgDialog(this);
        msgDialog.setContent(getString(R.string.confirm_cancel_order));
        msgDialog.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();
            }
        });

        msgDialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDialog();
                msgDialog.dismiss();
                progressDialog.show();
                cancelOrder();
            }
        });
    }

    private void initProgressDialog(){
        if(progressDialog != null){
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setContent(getString(R.string.in_cancel_order));
    }

    private View.OnClickListener getOrderDetailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getOrderDetail();
        }
    };

    @Override
    protected void onDestroy() {
        actDestory = true;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GO_PAY && resultCode == RESULT_OK){
            //将原来添加进来的视图删除，再重新获取数据
            resetViews();
        }else if(requestCode == GO_COMMENT && resultCode == RESULT_OK){
            resetViews();
        }
    }

    private void resetViews(){
        orderStateChange = true;//订单状态发生改变
        goodLl.removeViewAt(getInflatePosition() - 1);
        getOrderDetail();
    }

    @Override
    public void onBackPressed() {
        if(orderStateChange){
            setResult(RESULT_OK);
        }else{
            setResult(RESULT_CANCELED);
        }
        super.onBackPressed();
    }
}
