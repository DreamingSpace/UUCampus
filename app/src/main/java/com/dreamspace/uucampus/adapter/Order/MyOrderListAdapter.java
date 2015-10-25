package com.dreamspace.uucampus.adapter.Order;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;

import java.util.List;


/**
 * Created by Lx on 2015/10/20.
 */
public class MyOrderListAdapter extends BasisAdapter<String,MyOrderListAdapter.ViewHolder>{
    public MyOrderListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.shopName = (TextView) convertView.findViewById(R.id.shop_name_tv);
        holder.goodName = (TextView) convertView.findViewById(R.id.good_name_tv);
        holder.orderState = (TextView) convertView.findViewById(R.id.order_state_tv);
        holder.orderImage = (ImageView) convertView.findViewById(R.id.order_image_iv);
        holder.totalPrice = (TextView) convertView.findViewById(R.id.total_price_tv);
        holder.num = (TextView) convertView.findViewById(R.id.num_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item_my_order;
    }

    public static class ViewHolder{
        public TextView shopName;
        public TextView orderState;
        public ImageView orderImage;
        public TextView goodName;
        public TextView totalPrice;
        public TextView num;
    }
}
