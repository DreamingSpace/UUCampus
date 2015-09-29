package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.BasisAdapter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Lx on 2015/9/24.
 */
public class OnSaleListAdapter extends BasisAdapter<String,OnSaleListAdapter.ViewHolder> {
    public OnSaleListAdapter(Context mContext, List<String> mEntities, Class<ViewHolder> classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.sm_sale_good_image_iv);
        holder.goodName = (TextView) convertView.findViewById(R.id.sm_sale_good_name_tv);
        holder.shopName = (TextView) convertView.findViewById(R.id.sm_sale_good_shop_name_tv);
        holder.browseNum = (TextView) convertView.findViewById(R.id.sm_sale_good_browse_tv);
        holder.likeNum = (TextView) convertView.findViewById(R.id.sm_sale_good_like_tv);
        holder.preferential = (TextView) convertView.findViewById(R.id.sm_sale_good_preferential_tv);
        holder.price = (TextView) convertView.findViewById(R.id.sm_sale_good_price_tv);
        holder.pullOff = (Button) convertView.findViewById(R.id.good_pull_off_btn);
        holder.edit = (Button) convertView.findViewById(R.id.sale_good_edit_btn);
    }

    @Override
    public int getItemLayout() {
        return R.layout.shop_management_sale_goods_list_item;
    }

    public static class ViewHolder{
        ImageView image;
        TextView goodName;
        TextView shopName;
        TextView likeNum;
        TextView browseNum;
        TextView preferential;
        TextView price;
        Button pullOff;
        Button edit;
    }
}
