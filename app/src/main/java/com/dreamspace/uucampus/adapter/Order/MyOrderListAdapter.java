package com.dreamspace.uucampus.adapter.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.api.OrderItem;
import com.dreamspace.uucampus.ui.activity.Order.OrderPayAct;

import java.util.List;


/**
 * Created by Lx on 2015/10/20.
 */
public class MyOrderListAdapter extends BasisAdapter<OrderItem,MyOrderListAdapter.ViewHolder>{
    private Context mContext;
    private OnPayClickListener onPayClickListener;
    private OnCommentClickListener onCommentClickListener;
    public MyOrderListAdapter(Context mContext, List<OrderItem> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
        this.mContext = mContext;
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, final OrderItem entity) {
        holder.shopName.setText(entity.getShop().getName());
        holder.goodName.setText(entity.getGood().getName());
        switch (entity.getStatus()){
            case -1://退款中
                holder.orderState.setText(mContext.getString(R.string.in_refund));
                holder.orderState.setTextColor(mContext.getResources().getColor(R.color.app_theme_color2));
                holder.btnRl.setVisibility(View.GONE);
                break;

            case 0://已取消
                holder.orderState.setText(mContext.getString(R.string.already_refund));
                holder.orderState.setTextColor(mContext.getResources().getColor(R.color.text_normal));
                holder.btnRl.setVisibility(View.GONE);
                break;

            case 1://未支付
                holder.orderState.setText(mContext.getString(R.string.not_pay_yet));
                holder.orderState.setTextColor(mContext.getResources().getColor(R.color.app_theme_color2));
                holder.btnRl.setVisibility(View.VISIBLE);
                holder.payTv.setVisibility(View.VISIBLE);
                holder.commentTv.setVisibility(View.INVISIBLE);
                holder.payTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用activity注册的回调
                        if(onPayClickListener != null){
                            onPayClickListener.onPayClick(entity.getGood().getName(),entity.get_id(),
                                    entity.getQuantity() * entity.getGood().getOriginal_price(),entity.getTotal_price());
                        }
                    }
                });
                break;

            case 2://未消费
                holder.orderState.setText(mContext.getString(R.string.not_consume_yet));
                holder.orderState.setTextColor(mContext.getResources().getColor(R.color.app_theme_color2));
                holder.btnRl.setVisibility(View.GONE);
                break;

            case 3://已消费,未评价
                holder.orderState.setText(mContext.getString(R.string.not_comment_yet));
                holder.orderState.setTextColor(mContext.getResources().getColor(R.color.app_theme_color2));
                holder.btnRl.setVisibility(View.VISIBLE);
                holder.payTv.setVisibility(View.GONE);
                holder.commentTv.setVisibility(View.VISIBLE);
                holder.commentTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onCommentClickListener != null){
                            onCommentClickListener.onCommentClick(entity.get_id(),entity.getGood().get_id());
                        }
                    }
                });
                break;

            case 4://已评价
                holder.orderState.setText(mContext.getString(R.string.already_comment));
                holder.orderState.setTextColor(mContext.getResources().getColor(R.color.text_normal));
                holder.btnRl.setVisibility(View.GONE);
                break;
        }

        holder.totalPrice.setText(mContext.getString(R.string.total_price) + (float)entity.getTotal_price() / 100);
        holder.num.setText(mContext.getString(R.string.num) + entity.getQuantity());
        CommonUtils.showImageWithGlide(mContext,holder.orderImage,entity.getGood().getImage());
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.shopName = (TextView) convertView.findViewById(R.id.shop_name_tv);
        holder.goodName = (TextView) convertView.findViewById(R.id.good_name_tv);
        holder.orderState = (TextView) convertView.findViewById(R.id.order_state_tv);
        holder.orderImage = (ImageView) convertView.findViewById(R.id.order_image_iv);
        holder.totalPrice = (TextView) convertView.findViewById(R.id.total_price_tv);
        holder.num = (TextView) convertView.findViewById(R.id.num_tv);
        holder.btnRl = (RelativeLayout) convertView.findViewById(R.id.order_item_btn_rl);
        holder.payTv = (TextView) convertView.findViewById(R.id.pay_tv);
        holder.commentTv = (TextView) convertView.findViewById(R.id.comment_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_my_order;
    }

    public void setOnPayClickListener(OnPayClickListener onPayClickListener) {
        this.onPayClickListener = onPayClickListener;
    }

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public interface  OnPayClickListener{
        void onPayClick(String order_name,String order_id,int total_price,int rest_to_pay);
    }

    public interface OnCommentClickListener{
        void onCommentClick(String order_id,String good_id);
    }

    public static class ViewHolder{
        public TextView shopName;
        public TextView orderState;
        public ImageView orderImage;
        public TextView goodName;
        public TextView totalPrice;
        public TextView num;
        public RelativeLayout btnRl;
        public TextView payTv;
        public TextView commentTv;
    }
}
