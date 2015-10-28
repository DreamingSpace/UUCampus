package com.dreamspace.uucampus.adapter.market;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamspace.uucampus.R;
import com.dreamspace.uucampus.adapter.base.BasisAdapter;
import com.dreamspace.uucampus.common.utils.CommonUtils;
import com.dreamspace.uucampus.model.ShopItem;

import java.util.List;

/**
 * Created by Lx on 2015/9/22.
 */
public class ShopListAdapter extends BasisAdapter<ShopItem,ShopListAdapter.ViewHolder> {

    public ShopListAdapter(Context mContext, List<ShopItem> mEntities, Class classType) {
        super(mContext, mEntities, classType);
    }

    @Override
    protected void setDataIntoView(ViewHolder holder, ShopItem entity) {
        CommonUtils.showImageWithGlide(getmContext(),holder.image,entity.getImage());
        holder.shopName.setText(entity.getName());
        holder.majorSale.setText(entity.getMain());
        holder.address.setText(entity.getAddress());
    }

    @Override
    protected void initViewHolder(View convertView, ViewHolder holder) {
        holder.image = (ImageView) convertView.findViewById(R.id.shop_image_iv);
        holder.shopName = (TextView) convertView.findViewById(R.id.shop_name_tv);
        holder.majorSale = (TextView) convertView.findViewById(R.id.major_sale_tv);
        holder.address = (TextView) convertView.findViewById(R.id.address_tv);
//        holder.browseNum = (TextView) convertView.findViewById(R.id.shop_browse_num_tv);
//        holder.collectNum = (TextView) convertView.findViewById(R.id.shop_collect_num_tv);
//        holder.perferential = (TextView) convertView.findViewById(R.id.shop_preferential_tv);
    }

    @Override
    public int getItemLayout() {
        return R.layout.shop_list_item;
    }


    public static class ViewHolder{
        public ImageView image;
        public TextView shopName;
        public TextView majorSale;
        public TextView address;
//        public TextView browseNum;
//        public TextView collectNum;
//        public TextView perferential;
    }
}
