package com.dreamspace.uucampus.adapter.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.GoodsItem;

import java.util.List;

/**
 * Created by money on 2015/11/11.
 */
public class SearchGoodsAdapter extends BasisAdapter<GoodsItem, SearchGoodsAdapter.ViewHolder> {

    public SearchGoodsAdapter(Context mContext, List<GoodsItem> mEntities, Class classType){
        super(mContext,mEntities,classType);
    }


    @Override
    protected void setDataIntoView(ViewHolder holder, GoodsItem entity) {
        holder.searchGoodNameTv.setText(entity.getName());
        holder.searchGoodShopNameTv.setText(entity.getShop_name());
        holder.searchGoodPriceTv.setText(String.valueOf(entity.getPrice()));
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.searchGoodNameTv = (TextView)convertView.findViewById(R.id.search_good_name_tv);
        holder.searchGoodShopNameTv = (TextView)convertView.findViewById(R.id.search_good_shop_name_tv);
        holder.searchGoodPriceTv = (TextView)convertView.findViewById(R.id.search_good_price_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.search_goods_item;
    }

    public static class ViewHolder {
        TextView searchGoodNameTv;
        TextView searchGoodShopNameTv;
        TextView searchGoodPriceTv;
    }
}
