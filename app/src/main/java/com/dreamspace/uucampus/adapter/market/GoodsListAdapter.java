package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.BasisAdapter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Lx on 2015/9/23.
 */
public class GoodsListAdapter extends BasisAdapter<String,GoodsListAdapter.ViewHolder> {

    public GoodsListAdapter(Context mContext, List mEntities, Class classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, String entity) {

    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.good_image_iv);
        holder.goodName = (TextView) convertView.findViewById(R.id.good_name_tv);
        holder.shopName = (TextView) convertView.findViewById(R.id.shop_name_tv);
        holder.browseNum = (TextView) convertView.findViewById(R.id.good_browse_tv);
        holder.likeNum = (TextView) convertView.findViewById(R.id.good_like_tv);
        holder.perferential = (TextView) convertView.findViewById(R.id.good_preferential_tv);
        holder.price = (TextView) convertView.findViewById(R.id.good_price_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.goods_list_item;
    }


    public static class ViewHolder{
        public ImageView image;
        public TextView goodName;
        public TextView shopName;
        public TextView browseNum;
        public TextView likeNum;
        public TextView perferential;
        public TextView price;
    }
}
