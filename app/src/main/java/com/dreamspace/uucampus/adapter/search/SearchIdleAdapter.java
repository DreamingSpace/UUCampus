package com.dreamspace.uucampus.adapter.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.IdleItem;

import java.util.List;

/**
 * Created by money on 2015/11/11.
 */
public class SearchIdleAdapter extends BasisAdapter<IdleItem, SearchIdleAdapter.ViewHolder> {

    public SearchIdleAdapter(Context mContext, List<IdleItem> mEntities, Class classType){
        super(mContext,mEntities,classType);
    }

    @Override
    public int getItemLayout() {
        return R.layout.search_idle_item;
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, IdleItem entity) {
        holder.searchIdleNameTv.setText(entity.getName());
        holder.searchIdleShopNameTv.setText(entity.getUser_name());
        holder.searchIdlePriceTv.setText("￥"+String.valueOf(entity.getPrice()/100.0));
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.searchIdleNameTv = (TextView)convertView.findViewById(R.id.search_idle_name_tv);
        holder.searchIdleShopNameTv = (TextView)convertView.findViewById(R.id.search_idle_shop_name_tv);
        holder.searchIdlePriceTv = (TextView)convertView.findViewById(R.id.search_idle_price_tv);
    }

    public static class ViewHolder{
        TextView searchIdleNameTv;
        TextView searchIdleShopNameTv;
        TextView searchIdlePriceTv;
    }
}
