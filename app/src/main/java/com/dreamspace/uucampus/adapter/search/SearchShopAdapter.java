package com.dreamspace.uucampus.adapter.search;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.model.ShopItem;

import java.util.List;

/**
 * Created by money on 2015/11/11.
 */
public class SearchShopAdapter extends BasisAdapter<ShopItem,SearchShopAdapter.ViewHolder>{

    public SearchShopAdapter(Context mContext, List<ShopItem> mEntities, Class classType){
        super(mContext,mEntities,classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, ShopItem entity) {
        holder.searchShopName.setText(entity.getName());
        holder.searchShopMain.setText(entity.getMain());
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.searchShopName = (TextView) convertView.findViewById(R.id.search_shop_name_tv);
        holder.searchShopMain = (TextView) convertView.findViewById(R.id.search_main_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.search_shop_item;
    }

    public static class ViewHolder{
        TextView searchShopName;
        TextView searchShopMain;
    }
}
